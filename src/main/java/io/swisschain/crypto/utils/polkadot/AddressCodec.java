package io.swisschain.crypto.utils.polkadot;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.Base58;

public class AddressCodec {
    public static String encodeAddress(byte[] key, byte prefix){

        assert Defaults.allowedDecodedLengths.contains(key.length)
                : "Expected a valid key to convert, with length " + Defaults.allowedDecodedLengths + " : " + key.length;

        boolean isPublicKey = key.length == 32;

        byte[] input = Utils.u8aConcat(Lists.newArrayList(new byte[]{prefix}, key));
        byte[] hash = sshash(input);

        byte[] bytes = Utils.u8aConcat(Lists.newArrayList(input, ArrayUtils.subarray(hash, 0, isPublicKey ? 2 : 1)));

        String result = Base58.encode(bytes);
        return result;
    }

    final static byte[] SS58_PREFIX = Utils.stringToU8a("SS58PRE");
    public static byte[] sshash(byte[] key) {
        return UtilsCrypto.blake2AsU8a(Utils.u8aConcat(Lists.newArrayList(SS58_PREFIX, key)), 512);
    }
}
