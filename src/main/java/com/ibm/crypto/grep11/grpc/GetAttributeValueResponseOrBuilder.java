// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hsm.proto

package com.ibm.crypto.grep11.grpc;

public interface GetAttributeValueResponseOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:grep11.GetAttributeValueResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * [(gogoproto.castkey) = "github.com/ibm-developer/ibm-cloud-hyperprotectcrypto/golang/ep11.Attribute"];
   * </pre>
   *
   * <code>map&lt;uint64, bytes&gt; Attributes = 2;</code>
   */
  int getAttributesCount();
  /**
   *
   *
   * <pre>
   * [(gogoproto.castkey) = "github.com/ibm-developer/ibm-cloud-hyperprotectcrypto/golang/ep11.Attribute"];
   * </pre>
   *
   * <code>map&lt;uint64, bytes&gt; Attributes = 2;</code>
   */
  boolean containsAttributes(long key);
  /** Use {@link #getAttributesMap()} instead. */
  @java.lang.Deprecated
  java.util.Map<java.lang.Long, com.google.protobuf.ByteString> getAttributes();
  /**
   *
   *
   * <pre>
   * [(gogoproto.castkey) = "github.com/ibm-developer/ibm-cloud-hyperprotectcrypto/golang/ep11.Attribute"];
   * </pre>
   *
   * <code>map&lt;uint64, bytes&gt; Attributes = 2;</code>
   */
  java.util.Map<java.lang.Long, com.google.protobuf.ByteString> getAttributesMap();
  /**
   *
   *
   * <pre>
   * [(gogoproto.castkey) = "github.com/ibm-developer/ibm-cloud-hyperprotectcrypto/golang/ep11.Attribute"];
   * </pre>
   *
   * <code>map&lt;uint64, bytes&gt; Attributes = 2;</code>
   */
  com.google.protobuf.ByteString getAttributesOrDefault(
      long key, com.google.protobuf.ByteString defaultValue);
  /**
   *
   *
   * <pre>
   * [(gogoproto.castkey) = "github.com/ibm-developer/ibm-cloud-hyperprotectcrypto/golang/ep11.Attribute"];
   * </pre>
   *
   * <code>map&lt;uint64, bytes&gt; Attributes = 2;</code>
   */
  com.google.protobuf.ByteString getAttributesOrThrow(long key);
}