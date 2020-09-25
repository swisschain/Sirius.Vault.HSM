package io.swisschain.crypto.hsm;

import com.ibm.crypto.grep11.grpc.CryptoGrpc;
import io.grpc.ManagedChannel;

public class StubHolder {
  public final CryptoGrpc.CryptoBlockingStub stub;
  private final ManagedChannel channel;

  public StubHolder(CryptoGrpc.CryptoBlockingStub stub, ManagedChannel channel) {
    this.stub = stub;
    this.channel = channel;
  }

  public void closeChannel() {
    channel.shutdown();
  }
}
