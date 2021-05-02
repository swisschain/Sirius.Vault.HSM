package io.swisschain.services;

import com.google.protobuf.Timestamp;
import io.swisschain.domain.common.*;
import io.swisschain.domain.primitives.DoubleSpendingProtectionType;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.sirius.vaultApi.generated.common.Common;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class Mapper {
  public static Blockchain map(Common.Blockchain blockchain) {
    return new Blockchain(
        blockchain.getId(), blockchain.getProtocolId(), map(blockchain.getNetworkType()));
  }

  public static SigningAddress map(Common.SigningAddress signingAddress) {
    return new SigningAddress(signingAddress.getAddress(), signingAddress.getGroup());
  }

  public static List<Coin> map(List<Common.CoinToSpend> coinToSpends) {
    var coins = new ArrayList<Coin>();

    for (var coinToSpend : coinToSpends) {
      var coinId =
          new CoinId(coinToSpend.getId().getTransactionId(), coinToSpend.getId().getNumber());

      var redeem = coinToSpend.getRedeem() != null ? coinToSpend.getRedeem().getValue() : null;

      var coin =
          new Coin(
              coinId,
              new Asset(
                  coinToSpend.getAsset().getId(),
                  coinToSpend.getAsset().getSymbol(),
                  coinToSpend.getAsset().hasAddress()
                      ? coinToSpend.getAsset().getAddress().getValue()
                      : null),
              new BigDecimal(coinToSpend.getValue().getValue()),
              coinToSpend.getAddress(),
              redeem);

      coins.add(coin);
    }

    return coins;
  }

  public static Instant map(Timestamp timestamp) {
    return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
  }

  public static DoubleSpendingProtectionType map(
      @NotNull
          io.swisschain.sirius.vaultApi.generated.common.Common.DoubleSpendingProtectionType
              value) {
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

  public static NetworkType map(
      @NotNull io.swisschain.sirius.vaultApi.generated.common.Common.NetworkType networkType) {
    switch (networkType) {
      case PRIVATE:
        return NetworkType.Private;
      case TEST:
        return NetworkType.Test;
      case PUBLIC:
        return NetworkType.Public;
      default:
        throw new IllegalArgumentException(
            String.format("Unknown network type. %s", networkType.name()));
    }
  }
}
