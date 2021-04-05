import io.swisschain.config.clients.HsmApiConfig;
import io.swisschain.crypto.address.generation.generators.HsmTezosAddressGenerator;
import io.swisschain.crypto.exceptions.InvalidPublicKeyException;
import io.swisschain.crypto.transaction.signing.signers.HsmTezosTransactionSigner;
import io.swisschain.crypto.utils.tezos.sdk.Base58Helper;
import io.swisschain.primitives.NetworkType;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class TezosTests {

    private HsmApiConfig buildConfig(){
        return new HsmApiConfig( ) {
            {
                iamEndpoint = System.getenv("iamEndpoint");
                iamToken = System.getenv("iamToken");
                bluemixInstance = System.getenv("bluemixInstance");
                hsmHost = System.getenv("hsmHost");
                hsmPort = Integer.parseInt(System.getenv("hsmPort"));
            }
        };
    }

//    @Test
    public void should_generate_tezos_address() throws InvalidPublicKeyException, IOException, NoSuchAlgorithmException {
        var subject = new HsmTezosAddressGenerator(buildConfig());
        var result =  subject.generate(NetworkType.Private);

        LogManager.getLogger().atInfo().log("Private key " + result.getPrivateKey());
        LogManager.getLogger().atInfo().log("Public key " + result.getPublicKey());
        LogManager.getLogger().atInfo().log("Address " + result.getAddress());
    }

//    @Test()
    public void  can_sign() throws  NoSuchAlgorithmException, IOException {
        var subject = new HsmTezosTransactionSigner(buildConfig());
        var source = "31JJyiHqC7WLgpakTzFKvFesEbVBWFsahH2vRCMQmedU5TWaefBpprnLWjEbfkDFso7PW4JY2CKB9Gdo8gSgHQ5GGsjz3mJUutqf6ixYfQ7ZhdfK18jzqKA8ypxdXU5d1qZmiRygrmhwu5jPXcZnBNQ2PRS6bbqgxUjvVErhuBo18b9qAyqj8jZzJofyAjfPQh9QfqX5rXhH";
        var privateKeyHex = "0000000000000000000000000000000000000000000000000000000000000000ffff000100009f01a6fe48d3e2cdb84600000000004001260000000000000001123409798e7c3cdfe32738113e1f15f49c313fe55308af917fadff46dbe839ae168f284962e971ed384369366f92cca3e32f13a277705125652f8204335fde6d6d7337c81c48390414c90914c8ab4e33fda1f20f3ff6213bc0780ef71c43dca996f8f6bcb17fb98cd79b4a4dcfc9d13ae37800d004e352e162211a3313c77983baf469b6bf583cf675c7a7af9aaaa04a71736c1d033a15dccecdf98aafc634ed7f0447f2280e7e843c5353a395f8f340a44b1f22192978148302127919546a4cf15bfb70e09c4f18790c43a544ac4f452c4de485423a58de6a5d4b14a19f4fa44962fe01635d7c32adaec2f8ff19db2b46554aff13b363d9a97f42292823e468ec795ed7da379686dc14b777d32b8e833c1e1727a5ee642fb4b9825deba2a16f2b2fa3983b82eeb653f5ce25497860fb886f2dd5a35572caaf964ece789d2f2ac2bad982259e7bac7e277ef5314a7a888851ba13905d68892b5e314aa50d01a5e2ea3c268df006e9a6014740b36146f198c3fb86a4982ff0df3de880535dc94e5e477e13c69e699987275df362fc5a75efc906aa2aad6f2f8d0cddcb966b2968bd69eb7261c59e62c8f3c2274756d812ee4d3ba23a041a7c8e355bf53ef56a50ebd355cfda683d05a7a9a075657add084195353dffd63ede2eb8a2d63371159d945441e9e1a1547133fa105d40e885987269c1f01cab8cde877b411a518ab4f6306a3bc6cc7e0480a2e964188702c00215cd610aae2262c5bf437f3f2fe7445255afedea0e9227552beecc0e90e02d54a4c31b9fb55d30b5f231672315124a0c18a6525d1091f106e2f056d50a67f6008833830348268724bea2e9c0b8244c2a04473a3f3b90399dc6743fa0379d8414d06ad5225bd358bccf85e1ae021a5b27";

        var signed = subject.sign(Base58Helper.decode(source), null, privateKeyHex, "", NetworkType.Private, null);
        LogManager.getLogger().atInfo().log("Signed tx " + Base58Helper.encode(signed.getSignedTransaction()));
    }
}
