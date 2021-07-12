package io.swisschain.crypto.utils.polkadot;

import com.google.common.primitives.UnsignedBytes;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {

    public static byte[] u8aConcat(List<byte[]> _list) {
        List<byte[]> list = _list.stream().map(e -> u8aToU8a(e)).collect(Collectors.toList());

        int length = list.stream().mapToInt(e -> e.length).sum();
        byte[] result = new byte[length];
        int offset = 0;

        for (byte[] bytes : list) {
            System.arraycopy(bytes, 0, result, offset, bytes.length);
            offset += bytes.length;
        }
        return result;
    }

    public static byte[] u8aToU8a(Object value) {
        if (value == null) {
            return new byte[0];
        }

        if (value instanceof String) {
            String strValue = (String) value;
            return isHex(strValue)
                    ? hexToU8a(strValue)
                    : stringToU8a(strValue);
        }

        return (byte[]) value;
    }

    public static byte[] hexToU8a(String value, int bitLength) {
        if (value == null) {
            return new byte[0];
        }

        assert isHex(value) : "Expected hex value to convert, found " + value;

        value = hexStripPrefix(value);
        int valLength = value.length() / 2;
        int bufLength = (int) Math.ceil((
                bitLength == -1
                        ? valLength
                        : bitLength / 8f));

        byte[] result = new byte[bufLength];
        int offSet = Math.max(0, bufLength - valLength);

        for (int index = 0; index < bufLength; index++) {
            String byteStr = value.substring(index * 2, index * 2 + 2);
            result[index + offSet] = UnsignedBytes.parseUnsignedByte(byteStr, 16);
        }
        return result;
    }

    public static byte[] hexToU8a(String value) {
        return hexToU8a(value, -1);
    }

    static final String HEX_REGEX = "^0x[a-fA-F0-9]+$";
    public static boolean isHex(Object value) {
        return isHex(value, -1, false);
    }

    public static boolean isHex(Object value, int bitLength, boolean ignoreLength) {
        if (value == null) {
            return false;
        }
        //CharSequence value = _value.toString();
        boolean isValidHex = value.equals("0x") || (value instanceof String && Pattern.matches(HEX_REGEX, (CharSequence) value));

        if (isValidHex && bitLength != -1) {
            String strValue = (String) value;
            return strValue.length() == (2 + (int) Math.ceil(bitLength / 4));
        }

        return isValidHex && (ignoreLength || (((String) value).length() % 2 == 0));
    }

    static String UNPREFIX_HEX_REGEX = "^[a-fA-F0-9]+$";

    public static String hexStripPrefix(String value) {
        if (value == null) {
            return "";
        }

        if (hexHasPrefix(value)) {
            return value.substring(2);
        }

        if (Pattern.matches(UNPREFIX_HEX_REGEX, value)) {
            return value;
        }

        throw new RuntimeException("Invalid hex " + value + " passed to hexStripPrefix");
    }

    public static boolean hexHasPrefix(String value) {
        if (value != null
                && isHex(value, -1, true)
                && value.substring(0, 2).equals("0x")) {
            return true;
        }
        return false;
    }

    public static byte[] stringToU8a(String value) {
        if (StringUtils.isEmpty(value)) {
            return new byte[0];
        }

        return value.getBytes();
    }

    static final String ALPHABET = "0123456789abcdef";
    public static String u8aToHex(byte[] value, int bitLength, boolean isPrefixed) {
        String prefix = isPrefixed ? "0x" : "";

        if (ArrayUtils.isEmpty(value)) {
            return prefix;
        }

        int byteLength = (int) Math.ceil(bitLength / 8f);

        if (byteLength > 0 && value.length > byteLength) {
            int halfLength = (int) Math.ceil(byteLength / 2f);

            String left = u8aToHex(ArrayUtils.subarray(value, 0, halfLength), -1, isPrefixed);
            String right = u8aToHex(ArrayUtils.subarray(value, value.length - halfLength, value.length), -1, false);

            return left + "..." + right;
        }
        // based on comments in https://stackoverflow.com/questions/40031688/javascript-arraybuffer-to-hex and
        // implementation in http://jsben.ch/Vjx2V - optimisation here suggests that a forEach loop is faster
        // than reduce as well (clocking at in 90% of the reduce speed with tweaking in the playpen above)
        //return value.reduce((result, value) => {
        //    return result + ALPHABET[value >> 4] + ALPHABET[value & 15];
        //}, prefix);
        StringBuilder stringBuilder = new StringBuilder(prefix);

        for (byte b : value) {
            int ub = UnsignedBytes.toInt(b);
            stringBuilder.append(ALPHABET.charAt(ub >> 4)).append(ALPHABET.charAt(ub & 15));
        }
        return stringBuilder.toString();
    }

    public static String u8aToHex(byte[] value) {
        return u8aToHex(value, -1, true);
    }
}
