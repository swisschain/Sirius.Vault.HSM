package io.swisschain.mappers;

import io.swisschain.primitives.DoubleSpendingProtectionType;
import io.swisschain.sirius.vaultApi.generated.transferSigningRequests.TransferSigningRequestsOuterClass;
import org.jetbrains.annotations.NotNull;

public class DoubleSpendingProtectionTypeMapper {
  public static DoubleSpendingProtectionType map(
      @NotNull TransferSigningRequestsOuterClass.DoubleSpendingProtectionType value) {
    switch (value) {
      case COINS:
        return DoubleSpendingProtectionType.Coins;
      case NONCE:
        return DoubleSpendingProtectionType.Nonce;
      default:
        throw new IllegalArgumentException(
            String.format("Unknown double spending protection type. %s", value.name()));
    }
  }
}
