// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hsm.proto

package com.ibm.crypto.grep11.grpc;

public interface GenerateKeyResponseOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:grep11.GenerateKeyResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bytes Key = 4;</code>
   *
   * @return The key.
   */
  com.google.protobuf.ByteString getKey();

  /**
   * <code>bytes CheckSum = 5;</code>
   *
   * @return The checkSum.
   */
  com.google.protobuf.ByteString getCheckSum();
}