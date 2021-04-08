package io.swisschain.crypto.utils.tezos;

import org.bouncycastle.crypto.digests.Blake2bDigest;

public class Blacke2bHelper {
    public static byte[] getDigest(byte[] msg, int size){
        var result = new byte[size/8];
        var digest = new Blake2bDigest(size);

        digest.update(msg, 0, msg.length);
        digest.doFinal(result, 0);

        return result;
    }
}
