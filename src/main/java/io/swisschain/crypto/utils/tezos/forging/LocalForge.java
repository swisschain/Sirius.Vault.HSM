package io.swisschain.crypto.utils.tezos.forging;

import io.swisschain.crypto.transactions.exceptions.InvalidInputsException;
import io.swisschain.crypto.utils.tezos.Lengths;
import io.swisschain.crypto.utils.tezos.Prefix;
import io.swisschain.crypto.utils.tezos.forging.operations.OperationTag;
import io.swisschain.crypto.utils.tezos.forging.operations.RevealContent;
import io.swisschain.crypto.utils.tezos.forging.operations.TransactionContent;
import io.swisschain.crypto.utils.tezos.forging.operations.OperationContent;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LocalForge {
  public List<OperationContent> UnforgeOperations(byte[] bytes)
      throws IOException, NoSuchAlgorithmException, InvalidInputsException {
    var reader = new ForgedReader(bytes);
    reader.ReadBase58(Lengths.B.Decoded, Prefix.B); // read branch

    var result = new ArrayList<OperationContent>();
    while (!reader.EndOfStream()) {
      result.add(UnforgeOperation(reader));
    }

    return result;
  }

  private static OperationContent UnforgeOperation(ForgedReader reader)
      throws IOException, InvalidInputsException, NoSuchAlgorithmException {
    var operationTag = reader.ReadUBigInt().intValue();
    switch (operationTag) {
      case OperationTag.Transaction:
        {
          return UnforgeTransaction(reader);
        }
      case OperationTag.Reveal:
        {
          return UnforgeReveal(reader);
        }
      default:
        {
          throw new InvalidInputsException("Unsupported operation tag to unforge " + operationTag);
        }
    }
  }

  static TransactionContent UnforgeTransaction(ForgedReader reader)
      throws InvalidInputsException, IOException, NoSuchAlgorithmException {

    return new TransactionContent() {
      {
        Source = reader.ReadTzAddress();
        Fee = reader.ReadUBigInt().longValue();
        Counter = reader.ReadUBigInt().intValue();
        GasLimit = reader.ReadUBigInt().intValue();
        StorageLimit = reader.ReadUBigInt().intValue();
        Amount = reader.ReadUBigInt().longValue();
        Destination = reader.ReadAddress();
        Parameters = UnforgeParameters(reader);
      }
    };
  }

  static String UnforgeEntrypoint(ForgedReader reader) throws IOException, InvalidInputsException {
    var ep = reader.ReadInt32(1);

    switch (ep) {
      case 0:
        return "default";
      case 1:
        return "root";
      case 2:
        return "do";
      case 3:
        return "set_delegate";
      case 4:
        return "remove_delegate";
      case -1:
        return reader.ReadString(1);
      default:
        throw new InvalidInputsException("Unrecognized endpoint type " + ep);
    }
  }

  static TransactionContent.Parameters UnforgeParameters(ForgedReader reader)
      throws IOException, InvalidInputsException {
    if (reader.ReadBool()) {
      return new TransactionContent().new Parameters() {
        {
          Entrypoint = UnforgeEntrypoint(reader);
          reader.ReadEnumerableSingle((r) -> 42); // TODO Unforge Micheline
        }
      };
    }
    return null;
  }

  static RevealContent UnforgeReveal(ForgedReader reader)
      throws InvalidInputsException, IOException, NoSuchAlgorithmException {

    return new RevealContent() {
      {
        Source = reader.ReadTzAddress();
        Fee = reader.ReadUBigInt().longValue();
        Counter = reader.ReadUBigInt().intValue();
        GasLimit = reader.ReadUBigInt().intValue();
        StorageLimit = reader.ReadUBigInt().intValue();
        PublicKey = reader.ReadPublicKey();
      }
    };
  }
}
