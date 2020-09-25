// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hsm.proto

package com.ibm.crypto.grep11.grpc;

/** Protobuf type {@code grep11.Grep11Error} */
public final class Grep11Error extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:grep11.Grep11Error)
    Grep11ErrorOrBuilder {
  public static final int CODE_FIELD_NUMBER = 1;
  public static final int DETAIL_FIELD_NUMBER = 2;
  public static final int RETRY_FIELD_NUMBER = 3;
  private static final long serialVersionUID = 0L;
  // @@protoc_insertion_point(class_scope:grep11.Grep11Error)
  private static final com.ibm.crypto.grep11.grpc.Grep11Error DEFAULT_INSTANCE;
  private static final com.google.protobuf.Parser<Grep11Error> PARSER =
      new com.google.protobuf.AbstractParser<Grep11Error>() {
        @java.lang.Override
        public Grep11Error parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new Grep11Error(input, extensionRegistry);
        }
      };

  static {
    DEFAULT_INSTANCE = new com.ibm.crypto.grep11.grpc.Grep11Error();
  }

  private long code_;
  private volatile java.lang.Object detail_;
  private boolean retry_;
  private byte memoizedIsInitialized = -1;

  // Use Grep11Error.newBuilder() to construct.
  private Grep11Error(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Grep11Error() {
    detail_ = "";
  }
  private Grep11Error(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8:
            {
              code_ = input.readUInt64();
              break;
            }
          case 18:
            {
              java.lang.String s = input.readStringRequireUtf8();

              detail_ = s;
              break;
            }
          case 24:
            {
              retry_ = input.readBool();
              break;
            }
          default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.ibm.crypto.grep11.grpc.Hsm.internal_static_grep11_Grep11Error_descriptor;
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(com.ibm.crypto.grep11.grpc.Grep11Error prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  public static com.ibm.crypto.grep11.grpc.Grep11Error getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  public static com.google.protobuf.Parser<Grep11Error> parser() {
    return PARSER;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new Grep11Error();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.ibm.crypto.grep11.grpc.Hsm.internal_static_grep11_Grep11Error_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.ibm.crypto.grep11.grpc.Grep11Error.class,
            com.ibm.crypto.grep11.grpc.Grep11Error.Builder.class);
  }

  /**
   *
   *
   * <pre>
   * [(gogoproto.casttype) = "github.com/ibm-developer/ibm-cloud-hyperprotectcrypto/golang/ep11.Return"];
   * </pre>
   *
   * <code>uint64 Code = 1;</code>
   *
   * @return The code.
   */
  @java.lang.Override
  public long getCode() {
    return code_;
  }

  /**
   * <code>string Detail = 2;</code>
   *
   * @return The detail.
   */
  @java.lang.Override
  public java.lang.String getDetail() {
    java.lang.Object ref = detail_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      detail_ = s;
      return s;
    }
  }

  /**
   * <code>string Detail = 2;</code>
   *
   * @return The bytes for detail.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getDetailBytes() {
    java.lang.Object ref = detail_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      detail_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  /**
   * <code>bool Retry = 3;</code>
   *
   * @return The retry.
   */
  @java.lang.Override
  public boolean getRetry() {
    return retry_;
  }

  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
    if (code_ != 0L) {
      output.writeUInt64(1, code_);
    }
    if (!getDetailBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, detail_);
    }
    if (retry_ != false) {
      output.writeBool(3, retry_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (code_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeUInt64Size(1, code_);
    }
    if (!getDetailBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, detail_);
    }
    if (retry_ != false) {
      size += com.google.protobuf.CodedOutputStream.computeBoolSize(3, retry_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof com.ibm.crypto.grep11.grpc.Grep11Error)) {
      return super.equals(obj);
    }
    com.ibm.crypto.grep11.grpc.Grep11Error other = (com.ibm.crypto.grep11.grpc.Grep11Error) obj;

    if (getCode() != other.getCode()) return false;
    if (!getDetail().equals(other.getDetail())) return false;
    if (getRetry() != other.getRetry()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + CODE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCode());
    hash = (37 * hash) + DETAIL_FIELD_NUMBER;
    hash = (53 * hash) + getDetail().hashCode();
    hash = (37 * hash) + RETRY_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getRetry());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  @java.lang.Override
  public Builder newBuilderForType() {
    return newBuilder();
  }

  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Grep11Error> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.ibm.crypto.grep11.grpc.Grep11Error getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

  /** Protobuf type {@code grep11.Grep11Error} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:grep11.Grep11Error)
      com.ibm.crypto.grep11.grpc.Grep11ErrorOrBuilder {
    private long code_;
    private java.lang.Object detail_ = "";
    private boolean retry_;

    // Construct using com.ibm.crypto.grep11.grpc.Grep11Error.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.ibm.crypto.grep11.grpc.Hsm.internal_static_grep11_Grep11Error_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.ibm.crypto.grep11.grpc.Hsm.internal_static_grep11_Grep11Error_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.ibm.crypto.grep11.grpc.Grep11Error.class,
              com.ibm.crypto.grep11.grpc.Grep11Error.Builder.class);
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {}
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      code_ = 0L;

      detail_ = "";

      retry_ = false;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.ibm.crypto.grep11.grpc.Hsm.internal_static_grep11_Grep11Error_descriptor;
    }

    @java.lang.Override
    public com.ibm.crypto.grep11.grpc.Grep11Error getDefaultInstanceForType() {
      return com.ibm.crypto.grep11.grpc.Grep11Error.getDefaultInstance();
    }

    @java.lang.Override
    public com.ibm.crypto.grep11.grpc.Grep11Error build() {
      com.ibm.crypto.grep11.grpc.Grep11Error result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.ibm.crypto.grep11.grpc.Grep11Error buildPartial() {
      com.ibm.crypto.grep11.grpc.Grep11Error result =
          new com.ibm.crypto.grep11.grpc.Grep11Error(this);
      result.code_ = code_;
      result.detail_ = detail_;
      result.retry_ = retry_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }

    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
      return super.setField(field, value);
    }

    @java.lang.Override
    public Builder clearField(com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }

    @java.lang.Override
    public Builder clearOneof(com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }

    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field, int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }

    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.ibm.crypto.grep11.grpc.Grep11Error) {
        return mergeFrom((com.ibm.crypto.grep11.grpc.Grep11Error) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.ibm.crypto.grep11.grpc.Grep11Error other) {
      if (other == com.ibm.crypto.grep11.grpc.Grep11Error.getDefaultInstance()) return this;
      if (other.getCode() != 0L) {
        setCode(other.getCode());
      }
      if (!other.getDetail().isEmpty()) {
        detail_ = other.detail_;
        onChanged();
      }
      if (other.getRetry() != false) {
        setRetry(other.getRetry());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.ibm.crypto.grep11.grpc.Grep11Error parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.ibm.crypto.grep11.grpc.Grep11Error) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * [(gogoproto.casttype) = "github.com/ibm-developer/ibm-cloud-hyperprotectcrypto/golang/ep11.Return"];
     * </pre>
     *
     * <code>uint64 Code = 1;</code>
     *
     * @return The code.
     */
    @java.lang.Override
    public long getCode() {
      return code_;
    }

    /**
     *
     *
     * <pre>
     * [(gogoproto.casttype) = "github.com/ibm-developer/ibm-cloud-hyperprotectcrypto/golang/ep11.Return"];
     * </pre>
     *
     * <code>uint64 Code = 1;</code>
     *
     * @param value The code to set.
     * @return This builder for chaining.
     */
    public Builder setCode(long value) {

      code_ = value;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * [(gogoproto.casttype) = "github.com/ibm-developer/ibm-cloud-hyperprotectcrypto/golang/ep11.Return"];
     * </pre>
     *
     * <code>uint64 Code = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCode() {

      code_ = 0L;
      onChanged();
      return this;
    }

    /**
     * <code>string Detail = 2;</code>
     *
     * @return The detail.
     */
    public java.lang.String getDetail() {
      java.lang.Object ref = detail_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        detail_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }

    /**
     * <code>string Detail = 2;</code>
     *
     * @param value The detail to set.
     * @return This builder for chaining.
     */
    public Builder setDetail(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      detail_ = value;
      onChanged();
      return this;
    }

    /**
     * <code>string Detail = 2;</code>
     *
     * @return The bytes for detail.
     */
    public com.google.protobuf.ByteString getDetailBytes() {
      java.lang.Object ref = detail_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        detail_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    /**
     * <code>string Detail = 2;</code>
     *
     * @param value The bytes for detail to set.
     * @return This builder for chaining.
     */
    public Builder setDetailBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      detail_ = value;
      onChanged();
      return this;
    }

    /**
     * <code>string Detail = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDetail() {

      detail_ = getDefaultInstance().getDetail();
      onChanged();
      return this;
    }

    /**
     * <code>bool Retry = 3;</code>
     *
     * @return The retry.
     */
    @java.lang.Override
    public boolean getRetry() {
      return retry_;
    }
    /**
     * <code>bool Retry = 3;</code>
     *
     * @param value The retry to set.
     * @return This builder for chaining.
     */
    public Builder setRetry(boolean value) {

      retry_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool Retry = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearRetry() {

      retry_ = false;
      onChanged();
      return this;
    }

    @java.lang.Override
    public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }

    // @@protoc_insertion_point(builder_scope:grep11.Grep11Error)
  }
}
