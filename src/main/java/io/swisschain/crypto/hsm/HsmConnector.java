package io.swisschain.crypto.hsm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.ibm.crypto.grep11.grpc.CryptoGrpc;
import com.ibm.crypto.grep11.grpc.GenerateKeyPairRequest;
import com.ibm.crypto.grep11.grpc.Mechanism;
import com.ibm.crypto.grep11.grpc.SignSingleRequest;
import io.grpc.Metadata;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.grpc.stub.MetadataUtils;
import io.swisschain.config.clients.HsmApiConfig;
import io.swisschain.crypto.hsm.ep11.Attribute;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HsmConnector {
  private static final Logger logger = LogManager.getLogger();

  private final HsmApiConfig hsmConfig;

  public HsmConnector(HsmApiConfig hsmConfig) {
    this.hsmConfig = hsmConfig;
  }

  protected HsmKeyPair generateECDSAKeyPair() throws IOException {
    return generateKeyPair(
        new ASN1ObjectIdentifier("1.3.132.0.10"),
        io.swisschain.crypto.hsm.ep11.Mechanism.CKM_ECDSA_KEY_PAIR_GEN);
  }

  protected HsmKeyPair generateED25519KeyPair() throws IOException {
    return generateKeyPair(
        new ASN1ObjectIdentifier("1.3.101.112"),
        io.swisschain.crypto.hsm.ep11.Mechanism.CKM_EC_KEY_PAIR_GEN);
  }

  private HsmKeyPair generateKeyPair(
      ASN1ObjectIdentifier asn1ObjectIdentifier, io.swisschain.crypto.hsm.ep11.Mechanism mechanism)
      throws IOException {
    StubHolder stubHolder = initStub();
    try {
      final Map<Long, ByteString> publicKeyTemplate = new HashMap<>();
      publicKeyTemplate.put(
          Attribute.CKA_EC_PARAMS.value, ByteString.copyFrom(asn1ObjectIdentifier.getEncoded()));
      publicKeyTemplate.put(Attribute.CKA_VERIFY.value, ByteString.copyFrom(writeToBytes(true)));
      publicKeyTemplate.put(
          Attribute.CKA_EXTRACTABLE.value, ByteString.copyFrom(writeToBytes(false)));

      final Map<Long, ByteString> privateKeyTemplate = new HashMap<>();
      privateKeyTemplate.put(Attribute.CKA_SIGN.value, ByteString.copyFrom(writeToBytes(true)));
      privateKeyTemplate.put(
          Attribute.CKA_EXTRACTABLE.value, ByteString.copyFrom(writeToBytes(false)));

      final var request =
          GenerateKeyPairRequest.newBuilder()
              .setMech(Mechanism.newBuilder().setMechanism(mechanism.value).build())
              .putAllPubKeyTemplate(publicKeyTemplate)
              .putAllPrivKeyTemplate(privateKeyTemplate)
              .setPrivKeyId(UUID.randomUUID().toString())
              .setPubKeyId(UUID.randomUUID().toString())
              .build();

      final var response = stubHolder.stub.generateKeyPair(request);

      final var bIn =
          new ASN1InputStream(new ByteArrayInputStream(response.getPubKey().toByteArray()));
      final var obj = bIn.readObject();
      final var subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(obj);

      final var publicKey = subjectPublicKeyInfo.getPublicKeyData().getBytes();
      return new HsmKeyPair(response.getPrivKey().toByteArray(), publicKey);
    } finally {
      closeStub(stubHolder);
    }
  }

  protected byte[] signECDSA(byte[] data, byte[] privateKey) throws IOException {
    StubHolder stubHolder = initStub();
    try {
      return signECDSA(stubHolder.stub, data, privateKey);
    } finally {
      closeStub(stubHolder);
    }
  }

  protected byte[] signECDSA(CryptoGrpc.CryptoBlockingStub stub, byte[] data, byte[] privateKey) {
    return sign(stub, data, privateKey, io.swisschain.crypto.hsm.ep11.Mechanism.CKM_ECDSA);
  }

  protected byte[] signEd25519(byte[] data, byte[] privateKey) throws IOException {
    StubHolder stubHolder = initStub();
    try {
      return signEd25519(stubHolder.stub, data, privateKey);
    } finally {
      closeStub(stubHolder);
    }
  }

  protected byte[] signEd25519(CryptoGrpc.CryptoBlockingStub stub, byte[] data, byte[] privateKey) {
    return sign(
        stub, data, privateKey, io.swisschain.crypto.hsm.ep11.Mechanism.CKM_IBM_ED25519_SHA512);
  }

  protected byte[] sign(
      CryptoGrpc.CryptoBlockingStub stub,
      byte[] data,
      byte[] privateKey,
      io.swisschain.crypto.hsm.ep11.Mechanism mechanism) {
    logger.debug("Data to sign: {}", Hex.toHexString(data));
    final var signSingleRequest =
        SignSingleRequest.newBuilder()
            .setMech(Mechanism.newBuilder().setMechanism(mechanism.value).build())
            .setPrivKey(ByteString.copyFrom(privateKey))
            .setData(ByteString.copyFrom(data))
            .build();

    final var response = stub.signSingle(signSingleRequest);
    logger.debug("Signed data: {}", Hex.toHexString(response.getSignature().toByteArray()));
    return response.getSignature().toByteArray();
  }

  protected StubHolder initStub() throws IOException {
    final var client = new OkHttpClient();
    final var request =
        new Request.Builder()
            .url(hsmConfig.iamEndpoint)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .method(
                "POST",
                RequestBody.create(
                    "grant_type=urn:ibm:params:oauth:grant-type:apikey&apikey="
                        + hsmConfig.iamToken,
                    MediaType.parse("application/x-www-form-urlencoded")))
            .build();

    final var result = client.newCall(request).execute();

    final var mapper = new ObjectMapper();

    if (result.body() == null) {
      logger.error(String.format("Unable to get aut token: null body. %s", result.toString()));
      return null;
    }

    final var token =
        mapper.readValue(Objects.requireNonNull(result.body()).string(), TokenResponse.class);

    final var metadata = new Metadata();
    metadata.put(
        Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER),
        "Bearer " + token.access_token);
    metadata.put(
        Metadata.Key.of("Bluemix-Instance", Metadata.ASCII_STRING_MARSHALLER),
        hsmConfig.bluemixInstance);

    final var channel =
        NettyChannelBuilder.forAddress(hsmConfig.hsmHost, hsmConfig.hsmPort)
            .sslContext(
                GrpcSslContexts.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build())
            .build();

    //    final var channel =
    //        ManagedChannelBuilder.forAddress(hsmConfig.hsmHost, hsmConfig.hsmPort)
    //            .useTransportSecurity()
    //            .build();
    return new StubHolder(
        MetadataUtils.attachHeaders(CryptoGrpc.newBlockingStub(channel), metadata), channel);
  }

  protected byte[] writeToBytes(Object value) throws IOException {
    if (value instanceof byte[]) {
      return (byte[]) value;
    }
    final var stream = new ByteArrayOutputStream();
    final var dos = new DataOutputStream(stream);
    switch (value.getClass().getSimpleName()) {
      case "Boolean":
        dos.writeBoolean((Boolean) value);
        break;
      case "String":
        dos.writeChars((String) value);
        break;
      case "Integer":
        dos.writeInt((Integer) value);
        break;
      case "Long":
        dos.writeLong((Long) value);
        break;
      default:
        logger.error("Unsupported type: ${value.javaClass}");
    }
    final var result = stream.toByteArray();
    stream.close();
    return result;
  }

  protected void closeStub(StubHolder stubHolder) {
    stubHolder.closeChannel();
  }
}
