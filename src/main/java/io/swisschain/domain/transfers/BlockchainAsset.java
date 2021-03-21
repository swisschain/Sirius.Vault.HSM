package io.swisschain.domain.transfers;

public class BlockchainAsset {
  private final BlockchainAssetId id;
  private final int accuracy;

  public BlockchainAsset(BlockchainAssetId id, int accuracy) {
    this.id = id;
    this.accuracy = accuracy;
  }

  public BlockchainAssetId getId() {
    return id;
  }

  public int getAccuracy() {
    return accuracy;
  }

  @Override
  public String toString() {
    return "BlockchainAsset{" + "id=" + id + ", accuracy=" + accuracy + '}';
  }
}
