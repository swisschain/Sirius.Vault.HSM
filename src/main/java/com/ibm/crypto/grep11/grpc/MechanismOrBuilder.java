// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hsm.proto

package com.ibm.crypto.grep11.grpc;

public interface MechanismOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:grep11.Mechanism)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * [(gogoproto.casttype) = "github.com/ibm-developer/ibm-cloud-hyperprotectcrypto/golang/ep11.Mechanism"];
   * </pre>
   *
   * <code>uint64 Mechanism = 1;</code>
   *
   * @return The mechanism.
   */
  long getMechanism();

  /**
   *
   *
   * <pre>
   * optional
   * </pre>
   *
   * <code>bytes Parameter = 2;</code>
   *
   * @return The parameter.
   */
  com.google.protobuf.ByteString getParameter();
}