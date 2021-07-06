package io.swisschain.crypto.utils.polkadot;

import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.crypto.digests.Blake2bDigest;

public class UtilsCrypto {

    public static byte[] blake2AsU8a(byte[] data, int bitLength, byte[] key) {

        int byteLength = (int) Math.ceil(bitLength / 8F);

        Blake2bDigest blake2bkeyed = new Blake2bDigest(key, byteLength, null, null);
        blake2bkeyed.reset();
        blake2bkeyed.update(data, 0, data.length);
        byte[] keyedHash = new byte[64];
        int digestLength = blake2bkeyed.doFinal(keyedHash, 0);

        return ArrayUtils.subarray(keyedHash, 0, digestLength);

    }

    public static byte[] blake2AsU8a(byte[] data, int bitLength) {
        return blake2AsU8a(data, bitLength, null);
    }
}
