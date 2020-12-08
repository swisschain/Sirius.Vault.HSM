// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hsm.proto

package com.ibm.crypto.grep11.grpc;

/** Protobuf type {@code grep11.DecryptSingleResponse} */
public final class DecryptSingleResponse extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:grep11.DecryptSingleResponse)
    DecryptSingleResponseOrBuilder {
  public static final int PLAIN_FIELD_NUMBER = 4;
  private static final long serialVersionUID = 0L;
  // @@protoc_insertion_point(class_scope:grep11.DecryptSingleResponse)
  private static final com.ibm.crypto.grep11.grpc.DecryptSingleResponse DEFAULT_INSTANCE;
  private static final com.google.protobuf.Parser<DecryptSingleResponse> PARSER =
      new com.google.protobuf.AbstractParser<DecryptSingleResponse>() {
        @java.lang.Override
        public DecryptSingleResponse parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DecryptSingleResponse(input, extensionRegistry);
        }
      };

  static {
    DEFAULT_INSTANCE = new com.ibm.crypto.grep11.grpc.DecryptSingleResponse();
  }

  private com.google.protobuf.ByteString plain_;
  private byte memoizedIsInitialized = -1;

  // Use DecryptSingleResponse.newBuilder() to construct.
  private DecryptSingleResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DecryptSingleResponse() {
    plain_ = com.google.protobuf.ByteString.EMPTY;
  }
  private DecryptSingleResponse(
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
          case 34:
            {
              plain_ = input.readBytes();
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
    return com.ibm.crypto.grep11.grpc.Hsm.internal_static_grep11_DecryptSingleResponse_descriptor;
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(com.ibm.crypto.grep11.grpc.DecryptSingleResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  public static com.ibm.crypto.grep11.grpc.DecryptSingleResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  public static com.google.protobuf.Parser<DecryptSingleResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DecryptSingleResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.ibm.crypto.grep11.grpc.Hsm
        .internal_static_grep11_DecryptSingleResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.ibm.crypto.grep11.grpc.DecryptSingleResponse.class,
            com.ibm.crypto.grep11.grpc.DecryptSingleResponse.Builder.class);
  }

  /**
   * <code>bytes Plain = 4;</code>
   *
   * @return The plain.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getPlain() {
    return plain_;
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
    if (!plain_.isEmpty()) {
      output.writeBytes(4, plain_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!plain_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream.computeBytesSize(4, plain_);
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
    if (!(obj instanceof com.ibm.crypto.grep11.grpc.DecryptSingleResponse)) {
      return super.equals(obj);
    }
    com.ibm.crypto.grep11.grpc.DecryptSingleResponse other =
        (com.ibm.crypto.grep11.grpc.DecryptSingleResponse) obj;

    if (!getPlain().equals(other.getPlain())) return false;
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
    hash = (37 * hash) + PLAIN_FIELD_NUMBER;
    hash = (53 * hash) + getPlain().hashCode();
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
  public com.google.protobuf.Parser<DecryptSingleResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.ibm.crypto.grep11.grpc.DecryptSingleResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

  /** Protobuf type {@code grep11.DecryptSingleResponse} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:grep11.DecryptSingleResponse)
      com.ibm.crypto.grep11.grpc.DecryptSingleResponseOrBuilder {
    private com.google.protobuf.ByteString plain_ = com.google.protobuf.ByteString.EMPTY;

    // Construct using com.ibm.crypto.grep11.grpc.DecryptSingleResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.ibm.crypto.grep11.grpc.Hsm.internal_static_grep11_DecryptSingleResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.ibm.crypto.grep11.grpc.Hsm
          .internal_static_grep11_DecryptSingleResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.ibm.crypto.grep11.grpc.DecryptSingleResponse.class,
              com.ibm.crypto.grep11.grpc.DecryptSingleResponse.Builder.class);
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {}
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      plain_ = com.google.protobuf.ByteString.EMPTY;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.ibm.crypto.grep11.grpc.Hsm.internal_static_grep11_DecryptSingleResponse_descriptor;
    }

    @java.lang.Override
    public com.ibm.crypto.grep11.grpc.DecryptSingleResponse getDefaultInstanceForType() {
      return com.ibm.crypto.grep11.grpc.DecryptSingleResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.ibm.crypto.grep11.grpc.DecryptSingleResponse build() {
      com.ibm.crypto.grep11.grpc.DecryptSingleResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.ibm.crypto.grep11.grpc.DecryptSingleResponse buildPartial() {
      com.ibm.crypto.grep11.grpc.DecryptSingleResponse result =
          new com.ibm.crypto.grep11.grpc.DecryptSingleResponse(this);
      result.plain_ = plain_;
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
      if (other instanceof com.ibm.crypto.grep11.grpc.DecryptSingleResponse) {
        return mergeFrom((com.ibm.crypto.grep11.grpc.DecryptSingleResponse) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.ibm.crypto.grep11.grpc.DecryptSingleResponse other) {
      if (other == com.ibm.crypto.grep11.grpc.DecryptSingleResponse.getDefaultInstance())
        return this;
      if (other.getPlain() != com.google.protobuf.ByteString.EMPTY) {
        setPlain(other.getPlain());
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
      com.ibm.crypto.grep11.grpc.DecryptSingleResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.ibm.crypto.grep11.grpc.DecryptSingleResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    /**
     * <code>bytes Plain = 4;</code>
     *
     * @return The plain.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getPlain() {
      return plain_;
    }
    /**
     * <code>bytes Plain = 4;</code>
     *
     * @param value The plain to set.
     * @return This builder for chaining.
     */
    public Builder setPlain(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }

      plain_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes Plain = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPlain() {

      plain_ = getDefaultInstance().getPlain();
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

    // @@protoc_insertion_point(builder_scope:grep11.DecryptSingleResponse)
  }
}
