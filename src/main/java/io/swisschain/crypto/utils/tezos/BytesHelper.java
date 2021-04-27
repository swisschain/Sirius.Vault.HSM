package io.swisschain.crypto.utils.tezos;
public class BytesHelper {

    public static byte[] concat(byte[] a, byte[] b){
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static byte[] take(byte[] src, int start, int length) {
        var res = new byte[length];
        System.arraycopy(src, start, res, 0, length);
        return res;
    }
}
