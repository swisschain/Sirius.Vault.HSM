package io.swisschain.sirius.vaultApi.generated.wallets;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.31.1)",
    comments = "Source: wallets.proto")
public final class WalletsGrpc {

  private WalletsGrpc() {}

  public static final String SERVICE_NAME = "swisschain.sirius.vaultApi.wallets.Wallets";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .GetWalletGenerationRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .GetWalletGenerationRequestResponse>
      getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Get",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .GetWalletGenerationRequestRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .GetWalletGenerationRequestResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .GetWalletGenerationRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .GetWalletGenerationRequestResponse>
      getGetMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .GetWalletGenerationRequestRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .GetWalletGenerationRequestResponse>
        getGetMethod;
    if ((getGetMethod = WalletsGrpc.getGetMethod) == null) {
      synchronized (WalletsGrpc.class) {
        if ((getGetMethod = WalletsGrpc.getGetMethod) == null) {
          WalletsGrpc.getGetMethod =
              getGetMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .wallets
                              .WalletsOuterClass
                              .GetWalletGenerationRequestRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .wallets
                              .WalletsOuterClass
                              .GetWalletGenerationRequestResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Get"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.wallets.WalletsOuterClass
                                  .GetWalletGenerationRequestRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.wallets.WalletsOuterClass
                                  .GetWalletGenerationRequestResponse.getDefaultInstance()))
                      .setSchemaDescriptor(new WalletsMethodDescriptorSupplier("Get"))
                      .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .ConfirmWalletGenerationRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .ConfirmWalletGenerationRequestResponse>
      getConfirmMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Confirm",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .ConfirmWalletGenerationRequestRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .ConfirmWalletGenerationRequestResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .ConfirmWalletGenerationRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .ConfirmWalletGenerationRequestResponse>
      getConfirmMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .ConfirmWalletGenerationRequestRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .ConfirmWalletGenerationRequestResponse>
        getConfirmMethod;
    if ((getConfirmMethod = WalletsGrpc.getConfirmMethod) == null) {
      synchronized (WalletsGrpc.class) {
        if ((getConfirmMethod = WalletsGrpc.getConfirmMethod) == null) {
          WalletsGrpc.getConfirmMethod =
              getConfirmMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .wallets
                              .WalletsOuterClass
                              .ConfirmWalletGenerationRequestRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .wallets
                              .WalletsOuterClass
                              .ConfirmWalletGenerationRequestResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Confirm"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.wallets.WalletsOuterClass
                                  .ConfirmWalletGenerationRequestRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.wallets.WalletsOuterClass
                                  .ConfirmWalletGenerationRequestResponse.getDefaultInstance()))
                      .setSchemaDescriptor(new WalletsMethodDescriptorSupplier("Confirm"))
                      .build();
        }
      }
    }
    return getConfirmMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .RejectWalletGenerationRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .RejectWalletGenerationRequestResponse>
      getRejectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Reject",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .RejectWalletGenerationRequestRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .RejectWalletGenerationRequestResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .RejectWalletGenerationRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .wallets
              .WalletsOuterClass
              .RejectWalletGenerationRequestResponse>
      getRejectMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .RejectWalletGenerationRequestRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .RejectWalletGenerationRequestResponse>
        getRejectMethod;
    if ((getRejectMethod = WalletsGrpc.getRejectMethod) == null) {
      synchronized (WalletsGrpc.class) {
        if ((getRejectMethod = WalletsGrpc.getRejectMethod) == null) {
          WalletsGrpc.getRejectMethod =
              getRejectMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .wallets
                              .WalletsOuterClass
                              .RejectWalletGenerationRequestRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .wallets
                              .WalletsOuterClass
                              .RejectWalletGenerationRequestResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Reject"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.wallets.WalletsOuterClass
                                  .RejectWalletGenerationRequestRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.wallets.WalletsOuterClass
                                  .RejectWalletGenerationRequestResponse.getDefaultInstance()))
                      .setSchemaDescriptor(new WalletsMethodDescriptorSupplier("Reject"))
                      .build();
        }
      }
    }
    return getRejectMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static WalletsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WalletsStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<WalletsStub>() {
          @java.lang.Override
          public WalletsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new WalletsStub(channel, callOptions);
          }
        };
    return WalletsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WalletsBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WalletsBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<WalletsBlockingStub>() {
          @java.lang.Override
          public WalletsBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new WalletsBlockingStub(channel, callOptions);
          }
        };
    return WalletsBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static WalletsFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WalletsFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<WalletsFutureStub>() {
          @java.lang.Override
          public WalletsFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new WalletsFutureStub(channel, callOptions);
          }
        };
    return WalletsFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class WalletsImplBase implements io.grpc.BindableService {

    /** */
    public void get(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .GetWalletGenerationRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .GetWalletGenerationRequestResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /** */
    public void confirm(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .ConfirmWalletGenerationRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .ConfirmWalletGenerationRequestResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getConfirmMethod(), responseObserver);
    }

    /** */
    public void reject(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .RejectWalletGenerationRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .RejectWalletGenerationRequestResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getRejectMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .wallets
                          .WalletsOuterClass
                          .GetWalletGenerationRequestRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .wallets
                          .WalletsOuterClass
                          .GetWalletGenerationRequestResponse>(this, METHODID_GET)))
          .addMethod(
              getConfirmMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .wallets
                          .WalletsOuterClass
                          .ConfirmWalletGenerationRequestRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .wallets
                          .WalletsOuterClass
                          .ConfirmWalletGenerationRequestResponse>(this, METHODID_CONFIRM)))
          .addMethod(
              getRejectMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .wallets
                          .WalletsOuterClass
                          .RejectWalletGenerationRequestRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .wallets
                          .WalletsOuterClass
                          .RejectWalletGenerationRequestResponse>(this, METHODID_REJECT)))
          .build();
    }
  }

  /** */
  public static final class WalletsStub extends io.grpc.stub.AbstractAsyncStub<WalletsStub> {
    private WalletsStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletsStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WalletsStub(channel, callOptions);
    }

    /** */
    public void get(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .GetWalletGenerationRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .GetWalletGenerationRequestResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /** */
    public void confirm(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .ConfirmWalletGenerationRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .ConfirmWalletGenerationRequestResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getConfirmMethod(), getCallOptions()), request, responseObserver);
    }

    /** */
    public void reject(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .RejectWalletGenerationRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .RejectWalletGenerationRequestResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRejectMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /** */
  public static final class WalletsBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<WalletsBlockingStub> {
    private WalletsBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletsBlockingStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WalletsBlockingStub(channel, callOptions);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .wallets
            .WalletsOuterClass
            .GetWalletGenerationRequestResponse
        get(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .GetWalletGenerationRequestRequest
                request) {
      return blockingUnaryCall(getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .wallets
            .WalletsOuterClass
            .ConfirmWalletGenerationRequestResponse
        confirm(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .ConfirmWalletGenerationRequestRequest
                request) {
      return blockingUnaryCall(getChannel(), getConfirmMethod(), getCallOptions(), request);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .wallets
            .WalletsOuterClass
            .RejectWalletGenerationRequestResponse
        reject(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .RejectWalletGenerationRequestRequest
                request) {
      return blockingUnaryCall(getChannel(), getRejectMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class WalletsFutureStub
      extends io.grpc.stub.AbstractFutureStub<WalletsFutureStub> {
    private WalletsFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletsFutureStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WalletsFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .GetWalletGenerationRequestResponse>
        get(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .GetWalletGenerationRequestRequest
                request) {
      return futureUnaryCall(getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .ConfirmWalletGenerationRequestResponse>
        confirm(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .ConfirmWalletGenerationRequestRequest
                request) {
      return futureUnaryCall(getChannel().newCall(getConfirmMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .wallets
                .WalletsOuterClass
                .RejectWalletGenerationRequestResponse>
        reject(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .wallets
                    .WalletsOuterClass
                    .RejectWalletGenerationRequestRequest
                request) {
      return futureUnaryCall(getChannel().newCall(getRejectMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET = 0;
  private static final int METHODID_CONFIRM = 1;
  private static final int METHODID_REJECT = 2;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WalletsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WalletsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET:
          serviceImpl.get(
              (io.swisschain
                      .sirius
                      .vaultApi
                      .generated
                      .wallets
                      .WalletsOuterClass
                      .GetWalletGenerationRequestRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .wallets
                          .WalletsOuterClass
                          .GetWalletGenerationRequestResponse>)
                  responseObserver);
          break;
        case METHODID_CONFIRM:
          serviceImpl.confirm(
              (io.swisschain
                      .sirius
                      .vaultApi
                      .generated
                      .wallets
                      .WalletsOuterClass
                      .ConfirmWalletGenerationRequestRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .wallets
                          .WalletsOuterClass
                          .ConfirmWalletGenerationRequestResponse>)
                  responseObserver);
          break;
        case METHODID_REJECT:
          serviceImpl.reject(
              (io.swisschain
                      .sirius
                      .vaultApi
                      .generated
                      .wallets
                      .WalletsOuterClass
                      .RejectWalletGenerationRequestRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .wallets
                          .WalletsOuterClass
                          .RejectWalletGenerationRequestResponse>)
                  responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private abstract static class WalletsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WalletsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.swisschain.sirius.vaultApi.generated.wallets.WalletsOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Wallets");
    }
  }

  private static final class WalletsFileDescriptorSupplier extends WalletsBaseDescriptorSupplier {
    WalletsFileDescriptorSupplier() {}
  }

  private static final class WalletsMethodDescriptorSupplier extends WalletsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WalletsMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WalletsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new WalletsFileDescriptorSupplier())
                      .addMethod(getGetMethod())
                      .addMethod(getConfirmMethod())
                      .addMethod(getRejectMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
