package io.swisschain.sirius.vaultApi.generated.transferSigningRequests;

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
    comments = "Source: transfer-signing-requests.proto")
public final class TransferSigningRequestsGrpc {

  private TransferSigningRequestsGrpc() {}

  public static final String SERVICE_NAME =
      "swisschain.sirius.vaultApi.transferSigningRequests.TransferSigningRequests";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .GetTransferSigningRequestsRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .GetTransferSigningRequestsResponse>
      getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Get",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .GetTransferSigningRequestsRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .GetTransferSigningRequestsResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .GetTransferSigningRequestsRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .GetTransferSigningRequestsResponse>
      getGetMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .GetTransferSigningRequestsRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .GetTransferSigningRequestsResponse>
        getGetMethod;
    if ((getGetMethod = TransferSigningRequestsGrpc.getGetMethod) == null) {
      synchronized (TransferSigningRequestsGrpc.class) {
        if ((getGetMethod = TransferSigningRequestsGrpc.getGetMethod) == null) {
          TransferSigningRequestsGrpc.getGetMethod =
              getGetMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .transferSigningRequests
                              .TransferSigningRequestsOuterClass
                              .GetTransferSigningRequestsRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .transferSigningRequests
                              .TransferSigningRequestsOuterClass
                              .GetTransferSigningRequestsResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Get"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.transferSigningRequests
                                  .TransferSigningRequestsOuterClass
                                  .GetTransferSigningRequestsRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.transferSigningRequests
                                  .TransferSigningRequestsOuterClass
                                  .GetTransferSigningRequestsResponse.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new TransferSigningRequestsMethodDescriptorSupplier("Get"))
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
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .ConfirmTransferSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .ConfirmTransferSigningRequestResponse>
      getConfirmMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Confirm",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .ConfirmTransferSigningRequestRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .ConfirmTransferSigningRequestResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .ConfirmTransferSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .ConfirmTransferSigningRequestResponse>
      getConfirmMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .ConfirmTransferSigningRequestRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .ConfirmTransferSigningRequestResponse>
        getConfirmMethod;
    if ((getConfirmMethod = TransferSigningRequestsGrpc.getConfirmMethod) == null) {
      synchronized (TransferSigningRequestsGrpc.class) {
        if ((getConfirmMethod = TransferSigningRequestsGrpc.getConfirmMethod) == null) {
          TransferSigningRequestsGrpc.getConfirmMethod =
              getConfirmMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .transferSigningRequests
                              .TransferSigningRequestsOuterClass
                              .ConfirmTransferSigningRequestRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .transferSigningRequests
                              .TransferSigningRequestsOuterClass
                              .ConfirmTransferSigningRequestResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Confirm"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.transferSigningRequests
                                  .TransferSigningRequestsOuterClass
                                  .ConfirmTransferSigningRequestRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.transferSigningRequests
                                  .TransferSigningRequestsOuterClass
                                  .ConfirmTransferSigningRequestResponse.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new TransferSigningRequestsMethodDescriptorSupplier("Confirm"))
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
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .RejectTransferSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .RejectTransferSigningRequestResponse>
      getRejectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Reject",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .RejectTransferSigningRequestRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .RejectTransferSigningRequestResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .RejectTransferSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .transferSigningRequests
              .TransferSigningRequestsOuterClass
              .RejectTransferSigningRequestResponse>
      getRejectMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .RejectTransferSigningRequestRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .RejectTransferSigningRequestResponse>
        getRejectMethod;
    if ((getRejectMethod = TransferSigningRequestsGrpc.getRejectMethod) == null) {
      synchronized (TransferSigningRequestsGrpc.class) {
        if ((getRejectMethod = TransferSigningRequestsGrpc.getRejectMethod) == null) {
          TransferSigningRequestsGrpc.getRejectMethod =
              getRejectMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .transferSigningRequests
                              .TransferSigningRequestsOuterClass
                              .RejectTransferSigningRequestRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .transferSigningRequests
                              .TransferSigningRequestsOuterClass
                              .RejectTransferSigningRequestResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Reject"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.transferSigningRequests
                                  .TransferSigningRequestsOuterClass
                                  .RejectTransferSigningRequestRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.transferSigningRequests
                                  .TransferSigningRequestsOuterClass
                                  .RejectTransferSigningRequestResponse.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new TransferSigningRequestsMethodDescriptorSupplier("Reject"))
                      .build();
        }
      }
    }
    return getRejectMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static TransferSigningRequestsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TransferSigningRequestsStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<TransferSigningRequestsStub>() {
          @java.lang.Override
          public TransferSigningRequestsStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new TransferSigningRequestsStub(channel, callOptions);
          }
        };
    return TransferSigningRequestsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TransferSigningRequestsBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TransferSigningRequestsBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<TransferSigningRequestsBlockingStub>() {
          @java.lang.Override
          public TransferSigningRequestsBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new TransferSigningRequestsBlockingStub(channel, callOptions);
          }
        };
    return TransferSigningRequestsBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static TransferSigningRequestsFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TransferSigningRequestsFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<TransferSigningRequestsFutureStub>() {
          @java.lang.Override
          public TransferSigningRequestsFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new TransferSigningRequestsFutureStub(channel, callOptions);
          }
        };
    return TransferSigningRequestsFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class TransferSigningRequestsImplBase implements io.grpc.BindableService {

    /** */
    public void get(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .GetTransferSigningRequestsRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .GetTransferSigningRequestsResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /** */
    public void confirm(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .ConfirmTransferSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .ConfirmTransferSigningRequestResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getConfirmMethod(), responseObserver);
    }

    /** */
    public void reject(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .RejectTransferSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .RejectTransferSigningRequestResponse>
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
                          .transferSigningRequests
                          .TransferSigningRequestsOuterClass
                          .GetTransferSigningRequestsRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .transferSigningRequests
                          .TransferSigningRequestsOuterClass
                          .GetTransferSigningRequestsResponse>(this, METHODID_GET)))
          .addMethod(
              getConfirmMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .transferSigningRequests
                          .TransferSigningRequestsOuterClass
                          .ConfirmTransferSigningRequestRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .transferSigningRequests
                          .TransferSigningRequestsOuterClass
                          .ConfirmTransferSigningRequestResponse>(this, METHODID_CONFIRM)))
          .addMethod(
              getRejectMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .transferSigningRequests
                          .TransferSigningRequestsOuterClass
                          .RejectTransferSigningRequestRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .transferSigningRequests
                          .TransferSigningRequestsOuterClass
                          .RejectTransferSigningRequestResponse>(this, METHODID_REJECT)))
          .build();
    }
  }

  /** */
  public static final class TransferSigningRequestsStub
      extends io.grpc.stub.AbstractAsyncStub<TransferSigningRequestsStub> {
    private TransferSigningRequestsStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TransferSigningRequestsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TransferSigningRequestsStub(channel, callOptions);
    }

    /** */
    public void get(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .GetTransferSigningRequestsRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .GetTransferSigningRequestsResponse>
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
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .ConfirmTransferSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .ConfirmTransferSigningRequestResponse>
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
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .RejectTransferSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .RejectTransferSigningRequestResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRejectMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /** */
  public static final class TransferSigningRequestsBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<TransferSigningRequestsBlockingStub> {
    private TransferSigningRequestsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TransferSigningRequestsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TransferSigningRequestsBlockingStub(channel, callOptions);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .transferSigningRequests
            .TransferSigningRequestsOuterClass
            .GetTransferSigningRequestsResponse
        get(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .GetTransferSigningRequestsRequest
                request) {
      return blockingUnaryCall(getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .transferSigningRequests
            .TransferSigningRequestsOuterClass
            .ConfirmTransferSigningRequestResponse
        confirm(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .ConfirmTransferSigningRequestRequest
                request) {
      return blockingUnaryCall(getChannel(), getConfirmMethod(), getCallOptions(), request);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .transferSigningRequests
            .TransferSigningRequestsOuterClass
            .RejectTransferSigningRequestResponse
        reject(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .RejectTransferSigningRequestRequest
                request) {
      return blockingUnaryCall(getChannel(), getRejectMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class TransferSigningRequestsFutureStub
      extends io.grpc.stub.AbstractFutureStub<TransferSigningRequestsFutureStub> {
    private TransferSigningRequestsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TransferSigningRequestsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TransferSigningRequestsFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .GetTransferSigningRequestsResponse>
        get(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .GetTransferSigningRequestsRequest
                request) {
      return futureUnaryCall(getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .ConfirmTransferSigningRequestResponse>
        confirm(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .ConfirmTransferSigningRequestRequest
                request) {
      return futureUnaryCall(getChannel().newCall(getConfirmMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .transferSigningRequests
                .TransferSigningRequestsOuterClass
                .RejectTransferSigningRequestResponse>
        reject(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .transferSigningRequests
                    .TransferSigningRequestsOuterClass
                    .RejectTransferSigningRequestRequest
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
    private final TransferSigningRequestsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TransferSigningRequestsImplBase serviceImpl, int methodId) {
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
                      .transferSigningRequests
                      .TransferSigningRequestsOuterClass
                      .GetTransferSigningRequestsRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .transferSigningRequests
                          .TransferSigningRequestsOuterClass
                          .GetTransferSigningRequestsResponse>)
                  responseObserver);
          break;
        case METHODID_CONFIRM:
          serviceImpl.confirm(
              (io.swisschain
                      .sirius
                      .vaultApi
                      .generated
                      .transferSigningRequests
                      .TransferSigningRequestsOuterClass
                      .ConfirmTransferSigningRequestRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .transferSigningRequests
                          .TransferSigningRequestsOuterClass
                          .ConfirmTransferSigningRequestResponse>)
                  responseObserver);
          break;
        case METHODID_REJECT:
          serviceImpl.reject(
              (io.swisschain
                      .sirius
                      .vaultApi
                      .generated
                      .transferSigningRequests
                      .TransferSigningRequestsOuterClass
                      .RejectTransferSigningRequestRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .transferSigningRequests
                          .TransferSigningRequestsOuterClass
                          .RejectTransferSigningRequestResponse>)
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

  private abstract static class TransferSigningRequestsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TransferSigningRequestsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.swisschain.sirius.vaultApi.generated.transferSigningRequests
          .TransferSigningRequestsOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TransferSigningRequests");
    }
  }

  private static final class TransferSigningRequestsFileDescriptorSupplier
      extends TransferSigningRequestsBaseDescriptorSupplier {
    TransferSigningRequestsFileDescriptorSupplier() {}
  }

  private static final class TransferSigningRequestsMethodDescriptorSupplier
      extends TransferSigningRequestsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TransferSigningRequestsMethodDescriptorSupplier(String methodName) {
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
      synchronized (TransferSigningRequestsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new TransferSigningRequestsFileDescriptorSupplier())
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
