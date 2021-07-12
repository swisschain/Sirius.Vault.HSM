package io.swisschain.crypto.transactions.signers;

import com.google.common.collect.Lists;
import io.swisschain.config.clients.IbmApiConfig;
import io.swisschain.crypto.hsm.HsmConnector;
import io.swisschain.crypto.transactions.CoinsTransactionSigner;
import io.swisschain.crypto.transactions.TransactionSigningResult;
import io.swisschain.crypto.utils.polkadot.Utils;
import io.swisschain.crypto.utils.polkadot.contracts.BuiltTx;
import io.swisschain.crypto.utils.polkadot.contracts.SignedTx;
import io.swisschain.domain.common.Coin;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class HsmPolkadotTransactionSigner  extends HsmConnector implements CoinsTransactionSigner {
    private JsonSerializer _serializer = new JsonSerializer();
    public HsmPolkadotTransactionSigner(IbmApiConfig ibmApiConfig) {
        super(ibmApiConfig);
    }

    @Override
    public TransactionSigningResult sign(byte[] unsignedTransaction, List<Coin> coins, String privateKey, String publicKey, NetworkType networkType) throws Exception {
        var str = new String(unsignedTransaction, StandardCharsets.UTF_8);

        var builtTx = _serializer.deserialize(str, BuiltTx.class);
        var signingPayload = Utils.hexToU8a(builtTx.PreparedSigningPayload);
        var plainSignature = signEd25519(signingPayload, Hex.decode(privateKey));

        var polkadotSignature = Utils.u8aConcat(Lists.newArrayList(new byte[]{0}, plainSignature));

        var signedTx = new SignedTx();
        signedTx.Signature = Utils.u8aToHex(polkadotSignature);
        signedTx.UnsignedTxJson = builtTx.UnsignedTxJson;
        return new TransactionSigningResult("", _serializer.serialize(signedTx).getBytes(StandardCharsets.UTF_8));
    }
}
