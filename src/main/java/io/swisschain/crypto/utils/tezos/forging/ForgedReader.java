package io.swisschain.crypto.utils.tezos.forging;

import io.swisschain.crypto.transaction.signing.exceptions.InvalidInputsException;
import io.swisschain.crypto.utils.tezos.Lengths;
import io.swisschain.crypto.utils.tezos.Base58Helper;
import io.swisschain.crypto.utils.tezos.Prefix;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.function.Function;

public class ForgedReader {
    private final DataInputStream _reader;

    public ForgedReader(byte[] data){
        _reader = new DataInputStream(new ByteArrayInputStream(data));
    }

    public byte[] ReadBytes(int count) throws IOException {
        return _reader.readNBytes(count);
    }

    public String ReadBase58(int length, byte[] prefix) throws NoSuchAlgorithmException, IOException {
        if (prefix == null)
        {
            throw new IllegalArgumentException ("Prefix is null");
        }

        else if (prefix.length == 0)
        {
            throw new IllegalArgumentException("Prefix length must be greater than 0");
        }

        var b58bytes = ReadBytes(length);
        return Base58Helper.convert(b58bytes, prefix);
    }

    public int ReadInt32(int len) throws IOException {
        if (len < 1 || 4 < len)
            throw new IllegalArgumentException("len must be between 1 and 4");

        var bytes = ReadBytes(len);

        var res = 0;
        for (int i = 0, shift = ((len - 1) * 8); i < len; i++, shift -= 8)
        {
            res |= bytes[i] << shift;
        }

        return res;
    }

    public int ReadInt32() throws IOException {
        return ReadInt32(4);
    }

    public String ReadString(int len) throws IOException {
        var stringLength = ReadInt32(len);
        return new String(ReadBytes(stringLength), StandardCharsets.UTF_8);
    }

    public BigInteger ReadUBigInt() throws IOException {
        var value = BigInteger.ZERO;

        var bytes = new ArrayList<Byte>();

        byte b;
        while (((b = ReadByte()) & 0x80) != 0)
        {
            bytes.add(b);
        }
        bytes.add(b);

        for (var i = bytes.size() - 1; i >= 0; i--)
        {
            value = value.shiftLeft(7);
            var el =(byte) bytes.toArray()[i];
            value = value.or(BigInteger.valueOf(el & 0x7F));
        }

        return value;
    }

    public String ReadTzAddress() throws IOException, NoSuchAlgorithmException, InvalidInputsException {
        var tzType = ReadByte();

        byte[] prefix;
        switch (tzType)
        {
            case 0: prefix = Prefix.tz1; break;
            case 1: prefix = Prefix.tz2; break;
            case 2: prefix = Prefix.tz3; break;
            default:
                throw new InvalidInputsException("Invalid source prefix {tzType}");
        }

        return ReadBase58(20, prefix);
    }

    public String ReadPublicKey() throws IOException, NoSuchAlgorithmException, InvalidInputsException {
        var id = ReadByte();

        byte[] prefix;
        switch (id)
        {
            case 0: prefix = Prefix.edpk; break;
            case 1: prefix = Prefix.sppk; break;
            case 2: prefix = Prefix.p2pk; break;
            default: throw new InvalidInputsException("Invalid public key prefix id");
        }
        return ReadBase58(32, prefix);
    }

    public String ReadKtAddress() throws IOException, NoSuchAlgorithmException {
        var address = ReadBase58(Lengths.KT1.Decoded, Prefix.KT1);
        ReadByte(); // Consume padded 0
        return address;
    }

    public String ReadAddress() throws IOException, InvalidInputsException, NoSuchAlgorithmException {
        var type = ReadByte();

        switch (type)
        {
            case 0: return ReadTzAddress();
            case 1: return ReadKtAddress();
            default: throw new InvalidInputsException("Invalid address prefix type");
        }
    }

    public boolean ReadBool() throws IOException {
        return ReadByte() == (byte) -1;
    }

    public byte ReadByte() throws IOException {
        return ReadBytes(1)[0];
    }

    public boolean EndOfStream() throws IOException {
        return _reader.available() == 0;
    }

    public <T> T ReadEnumerableSingle(Function<ForgedReader, T> readData) throws IOException, InvalidInputsException {
        if(!EndOfStream()){
            var arrLen = ReadInt32();
            var arrData = ReadBytes(arrLen);
            var internalReader = new ForgedReader(arrData);
            var result = readData.apply(internalReader);

//            if(!internalReader.EndOfStream()){
//                throw new InvalidInputsException("Expected end of stream but not reached");
//            }
            return result;
        }

        return null;
    }
}
