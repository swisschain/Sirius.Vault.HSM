import io.swisschain.config.clients.HsmConfig;
import io.swisschain.config.clients.IamConfig;
import io.swisschain.config.clients.IbmApiConfig;
import io.swisschain.crypto.address.generation.generators.HsmPolkadotAddressGenerator;
import io.swisschain.crypto.exceptions.InvalidPublicKeyException;
import io.swisschain.crypto.transactions.signers.HsmPolkadotTransactionSigner;
import io.swisschain.crypto.utils.polkadot.AddressCodec;
import io.swisschain.crypto.utils.polkadot.PolkadotSS58Format;
import io.swisschain.crypto.utils.polkadot.Utils;
import io.swisschain.crypto.utils.polkadot.contracts.BuiltTx;
import io.swisschain.crypto.utils.polkadot.contracts.SignedTx;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;
import org.bitcoinj.core.Base58;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class PolkadotTests {
    private IbmApiConfig buildConfig() {
        return new IbmApiConfig() {
            {
                hsm =
                        new HsmConfig() {
                            {
                                bluemixInstance = System.getenv("bluemixInstance");
                                host = System.getenv("hsmHost");
                                port = Integer.parseInt(System.getenv("hsmPort"));
                            }
                        };
                iam =
                        new IamConfig() {
                            {
                                host = System.getenv("iamEndpoint");
                                apiKey = System.getenv("iamToken");
                            }
                        };
            }
        };
    }
    @Test
    public void CanExtractAddressFromPublicKey(){

        String base58EncodedPublicKey = "8pWvqrAyoZHVQoHjEut7GnKZ7DuajQw2hawMQy7L45Nu";

    String address =
        AddressCodec.encodeAddress(
            Base58.decode(base58EncodedPublicKey), PolkadotSS58Format.westend);
        Assert.assertEquals("5Eh34UfunFifLWNBE5k8Avqt1jGNLEzrFVf7EHyBxLk5twHW", address);
    }

//    @Test
    public void  CanGenerateForTest() throws NoSuchAlgorithmException, IOException, InvalidPublicKeyException {
        var generator = new HsmPolkadotAddressGenerator(buildConfig());
        var addr = generator.generate(NetworkType.Test).getAddress();

        System.out.println("Address generated " + addr);

        Assert.assertTrue(addr.startsWith("5"));

    }
//    @Test
    public void  CanGenerateForPublic() throws NoSuchAlgorithmException, IOException, InvalidPublicKeyException {
        var generator = new HsmPolkadotAddressGenerator(buildConfig());
        var addr = generator.generate(NetworkType.Public).getAddress();

        System.out.println("Address generated " + addr);

        Assert.assertTrue(addr.startsWith("1"));

    }

//    @Test
    public void CanSign() throws Exception {
        var serializer = new JsonSerializer();
        var signer = new HsmPolkadotTransactionSigner(buildConfig());
        var address = "5GuoS6u6AiCbEMX3Qprz42YpunghWkFrguB5Z3S71z6RyY6X";
        var privateKey = "0000000000000000000000000000000000000000000000000000000000000000c55aaf5fe4c7ded09da95176d5c53ffb000000000000012600000000000000011234edc173279e864c12061105d598de6003ed2f05cda83c76cd0baa97689cf9e37f6b620a05363c7c2ab58b753f8ede71f579332fb7fca816414421561c18c3d2799589ff5fcc16d6ef955432aff0bd3b6940040f2a74b445605b1f41db4a2fd0861ce533646be978c3837397ad733d1fa0b7cc77c3520bf4d30d9c86478bfb489399a83b0bf12b575b7c31a3e762e8be5228f5df05d15a4850dc799ca1ed218a4363fa7186263552bb4f00ba7d26b82478b2d42aa24abfa587e3ffdfdea207b1e763c71c8ffdd88c68fefe4132001bebcda81f6e969ee0cf30d191e83c051decddd47382fa0ec0af9c8435a07d7f6745e5817642bb0974b50be8a689febcdf39cf17aba930e40857627742cb3db0e455e8681d7f4c4c1f53720772acedefb690120f73fe045713ec5802a4692a1016e29fbe5a41b6bae0651a76d42558e42f365fd4268a33d9471ac1140205a425fb31e7dd36a515b16711a476d77edf66c946acbe935e402031d911f0c3c5002e4a85aae26ffd1e3d7c94af913059b47a44c6ef42ad9707cfa625552892f4634af2a5268d95afcc9c916716e62d2e7a67b2fa73708dfefbeedb77d5d46e1f9a76055f5d7fc4610544faf8771cf1ae117fd5cbbe240ea54cdb9056c1a6cf470fd18724016c7adea39f8a33768867c05e9db48875fab97e821a1b3f230447473cd8f1999e4681d3057aabe2dc5e841e16adbd4b3a014c85d3342d130feb7eab9f48874f0fdcd873e2c690e88bac5329a85e7b6f64a7dc24b090de6b25a84c0f49c956";

        var signingPayloadPrepared = "0x040000751601d87938ee8ead70b0fe138daae854dbaf5823b97ccb4aaf52e89ef9c917a10f750100a10f7823000005000000e143f23803ac50e8f6f8e62695d1ce9e4e1d68aa36c1cd2cfd15340213f3423e66ea8df40096cdbce74c206f2be2d3f6206bbf780fd3b67702e55d7b2e15a291";

        var builtTx = new BuiltTx();
        builtTx.UnsignedTxJson = "unused";
        builtTx.SigningPayload = "unused";
        builtTx.PreparedSigningPayload = signingPayloadPrepared;
        var builtTxJson = serializer.serialize(builtTx);

        var signedTxRaw = signer.sign(builtTxJson.getBytes(StandardCharsets.UTF_8), null, privateKey, "", NetworkType.Private);
        var signedTxString = new String(signedTxRaw.getSignedTransaction(), StandardCharsets.UTF_8);
        var signedTxModel = serializer.deserialize(signedTxString, SignedTx.class);

        System.out.println("signature " + signedTxModel.Signature);
    }
}
