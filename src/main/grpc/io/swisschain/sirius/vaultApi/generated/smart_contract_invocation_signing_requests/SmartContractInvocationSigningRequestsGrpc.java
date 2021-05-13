package io.swisschain.sirius.vaultApi.generated.smart_contract_invocation_signing_requests;

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
    comments = "Source: smart-contract-invocation-signing-requests.proto")
public final class SmartContractInvocationSigningRequestsGrpc {

  private SmartContractInvocationSigningRequestsGrpc() {}

  public static final String SERVICE_NAME =
      "swisschain.sirius.vaultApi.smartContractInvocationSigningRequests.SmartContractInvocationSigningRequests";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .GetSmartContractInvocationSigningRequestsRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .GetSmartContractInvocationSigningRequestsResponse>
      getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Get",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .GetSmartContractInvocationSigningRequestsRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .GetSmartContractInvocationSigningRequestsResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .GetSmartContractInvocationSigningRequestsRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .GetSmartContractInvocationSigningRequestsResponse>
      getGetMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .GetSmartContractInvocationSigningRequestsRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .GetSmartContractInvocationSigningRequestsResponse>
        getGetMethod;
    if ((getGetMethod = SmartContractInvocationSigningRequestsGrpc.getGetMethod) == null) {
      synchronized (SmartContractInvocationSigningRequestsGrpc.class) {
        if ((getGetMethod = SmartContractInvocationSigningRequestsGrpc.getGetMethod) == null) {
          SmartContractInvocationSigningRequestsGrpc.getGetMethod =
              getGetMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_invocation_signing_requests
                              .SmartContractInvocationSigningRequestsOuterClass
                              .GetSmartContractInvocationSigningRequestsRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_invocation_signing_requests
                              .SmartContractInvocationSigningRequestsOuterClass
                              .GetSmartContractInvocationSigningRequestsResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Get"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_invocation_signing_requests
                                  .SmartContractInvocationSigningRequestsOuterClass
                                  .GetSmartContractInvocationSigningRequestsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_invocation_signing_requests
                                  .SmartContractInvocationSigningRequestsOuterClass
                                  .GetSmartContractInvocationSigningRequestsResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new SmartContractInvocationSigningRequestsMethodDescriptorSupplier("Get"))
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
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .ConfirmSmartContractInvocationSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .ConfirmSmartContractInvocationSigningRequestResponse>
      getConfirmMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Confirm",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .ConfirmSmartContractInvocationSigningRequestRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .ConfirmSmartContractInvocationSigningRequestResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .ConfirmSmartContractInvocationSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .ConfirmSmartContractInvocationSigningRequestResponse>
      getConfirmMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .ConfirmSmartContractInvocationSigningRequestRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .ConfirmSmartContractInvocationSigningRequestResponse>
        getConfirmMethod;
    if ((getConfirmMethod = SmartContractInvocationSigningRequestsGrpc.getConfirmMethod) == null) {
      synchronized (SmartContractInvocationSigningRequestsGrpc.class) {
        if ((getConfirmMethod = SmartContractInvocationSigningRequestsGrpc.getConfirmMethod)
            == null) {
          SmartContractInvocationSigningRequestsGrpc.getConfirmMethod =
              getConfirmMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_invocation_signing_requests
                              .SmartContractInvocationSigningRequestsOuterClass
                              .ConfirmSmartContractInvocationSigningRequestRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_invocation_signing_requests
                              .SmartContractInvocationSigningRequestsOuterClass
                              .ConfirmSmartContractInvocationSigningRequestResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Confirm"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_invocation_signing_requests
                                  .SmartContractInvocationSigningRequestsOuterClass
                                  .ConfirmSmartContractInvocationSigningRequestRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_invocation_signing_requests
                                  .SmartContractInvocationSigningRequestsOuterClass
                                  .ConfirmSmartContractInvocationSigningRequestResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new SmartContractInvocationSigningRequestsMethodDescriptorSupplier(
                              "Confirm"))
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
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .RejectSmartContractInvocationSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .RejectSmartContractInvocationSigningRequestResponse>
      getRejectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Reject",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .RejectSmartContractInvocationSigningRequestRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .RejectSmartContractInvocationSigningRequestResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .RejectSmartContractInvocationSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_invocation_signing_requests
              .SmartContractInvocationSigningRequestsOuterClass
              .RejectSmartContractInvocationSigningRequestResponse>
      getRejectMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .RejectSmartContractInvocationSigningRequestRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .RejectSmartContractInvocationSigningRequestResponse>
        getRejectMethod;
    if ((getRejectMethod = SmartContractInvocationSigningRequestsGrpc.getRejectMethod) == null) {
      synchronized (SmartContractInvocationSigningRequestsGrpc.class) {
        if ((getRejectMethod = SmartContractInvocationSigningRequestsGrpc.getRejectMethod)
            == null) {
          SmartContractInvocationSigningRequestsGrpc.getRejectMethod =
              getRejectMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_invocation_signing_requests
                              .SmartContractInvocationSigningRequestsOuterClass
                              .RejectSmartContractInvocationSigningRequestRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_invocation_signing_requests
                              .SmartContractInvocationSigningRequestsOuterClass
                              .RejectSmartContractInvocationSigningRequestResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Reject"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_invocation_signing_requests
                                  .SmartContractInvocationSigningRequestsOuterClass
                                  .RejectSmartContractInvocationSigningRequestRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_invocation_signing_requests
                                  .SmartContractInvocationSigningRequestsOuterClass
                                  .RejectSmartContractInvocationSigningRequestResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new SmartContractInvocationSigningRequestsMethodDescriptorSupplier(
                              "Reject"))
                      .build();
        }
      }
    }
    return getRejectMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static SmartContractInvocationSigningRequestsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SmartContractInvocationSigningRequestsStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<SmartContractInvocationSigningRequestsStub>() {
          @java.lang.Override
          public SmartContractInvocationSigningRequestsStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new SmartContractInvocationSigningRequestsStub(channel, callOptions);
          }
        };
    return SmartContractInvocationSigningRequestsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SmartContractInvocationSigningRequestsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SmartContractInvocationSigningRequestsBlockingStub>
        factory =
            new io.grpc.stub.AbstractStub.StubFactory<
                SmartContractInvocationSigningRequestsBlockingStub>() {
              @java.lang.Override
              public SmartContractInvocationSigningRequestsBlockingStub newStub(
                  io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new SmartContractInvocationSigningRequestsBlockingStub(channel, callOptions);
              }
            };
    return SmartContractInvocationSigningRequestsBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static SmartContractInvocationSigningRequestsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SmartContractInvocationSigningRequestsFutureStub>
        factory =
            new io.grpc.stub.AbstractStub.StubFactory<
                SmartContractInvocationSigningRequestsFutureStub>() {
              @java.lang.Override
              public SmartContractInvocationSigningRequestsFutureStub newStub(
                  io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new SmartContractInvocationSigningRequestsFutureStub(channel, callOptions);
              }
            };
    return SmartContractInvocationSigningRequestsFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class SmartContractInvocationSigningRequestsImplBase
      implements io.grpc.BindableService {

    /** */
    public void get(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .GetSmartContractInvocationSigningRequestsRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .GetSmartContractInvocationSigningRequestsResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /** */
    public void confirm(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .ConfirmSmartContractInvocationSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .ConfirmSmartContractInvocationSigningRequestResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getConfirmMethod(), responseObserver);
    }

    /** */
    public void reject(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .RejectSmartContractInvocationSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .RejectSmartContractInvocationSigningRequestResponse>
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
                          .smart_contract_invocation_signing_requests
                          .SmartContractInvocationSigningRequestsOuterClass
                          .GetSmartContractInvocationSigningRequestsRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_invocation_signing_requests
                          .SmartContractInvocationSigningRequestsOuterClass
                          .GetSmartContractInvocationSigningRequestsResponse>(this, METHODID_GET)))
          .addMethod(
              getConfirmMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_invocation_signing_requests
                          .SmartContractInvocationSigningRequestsOuterClass
                          .ConfirmSmartContractInvocationSigningRequestRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_invocation_signing_requests
                          .SmartContractInvocationSigningRequestsOuterClass
                          .ConfirmSmartContractInvocationSigningRequestResponse>(
                      this, METHODID_CONFIRM)))
          .addMethod(
              getRejectMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_invocation_signing_requests
                          .SmartContractInvocationSigningRequestsOuterClass
                          .RejectSmartContractInvocationSigningRequestRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_invocation_signing_requests
                          .SmartContractInvocationSigningRequestsOuterClass
                          .RejectSmartContractInvocationSigningRequestResponse>(
                      this, METHODID_REJECT)))
          .build();
    }
  }

  /** */
  public static final class SmartContractInvocationSigningRequestsStub
      extends io.grpc.stub.AbstractAsyncStub<SmartContractInvocationSigningRequestsStub> {
    private SmartContractInvocationSigningRequestsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartContractInvocationSigningRequestsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SmartContractInvocationSigningRequestsStub(channel, callOptions);
    }

    /** */
    public void get(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .GetSmartContractInvocationSigningRequestsRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .GetSmartContractInvocationSigningRequestsResponse>
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
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .ConfirmSmartContractInvocationSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .ConfirmSmartContractInvocationSigningRequestResponse>
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
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .RejectSmartContractInvocationSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .RejectSmartContractInvocationSigningRequestResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRejectMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /** */
  public static final class SmartContractInvocationSigningRequestsBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<
          SmartContractInvocationSigningRequestsBlockingStub> {
    private SmartContractInvocationSigningRequestsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartContractInvocationSigningRequestsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SmartContractInvocationSigningRequestsBlockingStub(channel, callOptions);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .smart_contract_invocation_signing_requests
            .SmartContractInvocationSigningRequestsOuterClass
            .GetSmartContractInvocationSigningRequestsResponse
        get(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .GetSmartContractInvocationSigningRequestsRequest
                request) {
      return blockingUnaryCall(getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .smart_contract_invocation_signing_requests
            .SmartContractInvocationSigningRequestsOuterClass
            .ConfirmSmartContractInvocationSigningRequestResponse
        confirm(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .ConfirmSmartContractInvocationSigningRequestRequest
                request) {
      return blockingUnaryCall(getChannel(), getConfirmMethod(), getCallOptions(), request);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .smart_contract_invocation_signing_requests
            .SmartContractInvocationSigningRequestsOuterClass
            .RejectSmartContractInvocationSigningRequestResponse
        reject(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .RejectSmartContractInvocationSigningRequestRequest
                request) {
      return blockingUnaryCall(getChannel(), getRejectMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class SmartContractInvocationSigningRequestsFutureStub
      extends io.grpc.stub.AbstractFutureStub<SmartContractInvocationSigningRequestsFutureStub> {
    private SmartContractInvocationSigningRequestsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartContractInvocationSigningRequestsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SmartContractInvocationSigningRequestsFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .GetSmartContractInvocationSigningRequestsResponse>
        get(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .GetSmartContractInvocationSigningRequestsRequest
                request) {
      return futureUnaryCall(getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .ConfirmSmartContractInvocationSigningRequestResponse>
        confirm(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .ConfirmSmartContractInvocationSigningRequestRequest
                request) {
      return futureUnaryCall(getChannel().newCall(getConfirmMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_invocation_signing_requests
                .SmartContractInvocationSigningRequestsOuterClass
                .RejectSmartContractInvocationSigningRequestResponse>
        reject(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_invocation_signing_requests
                    .SmartContractInvocationSigningRequestsOuterClass
                    .RejectSmartContractInvocationSigningRequestRequest
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
    private final SmartContractInvocationSigningRequestsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SmartContractInvocationSigningRequestsImplBase serviceImpl, int methodId) {
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
                      .smart_contract_invocation_signing_requests
                      .SmartContractInvocationSigningRequestsOuterClass
                      .GetSmartContractInvocationSigningRequestsRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_invocation_signing_requests
                          .SmartContractInvocationSigningRequestsOuterClass
                          .GetSmartContractInvocationSigningRequestsResponse>)
                  responseObserver);
          break;
        case METHODID_CONFIRM:
          serviceImpl.confirm(
              (io.swisschain
                      .sirius
                      .vaultApi
                      .generated
                      .smart_contract_invocation_signing_requests
                      .SmartContractInvocationSigningRequestsOuterClass
                      .ConfirmSmartContractInvocationSigningRequestRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_invocation_signing_requests
                          .SmartContractInvocationSigningRequestsOuterClass
                          .ConfirmSmartContractInvocationSigningRequestResponse>)
                  responseObserver);
          break;
        case METHODID_REJECT:
          serviceImpl.reject(
              (io.swisschain
                      .sirius
                      .vaultApi
                      .generated
                      .smart_contract_invocation_signing_requests
                      .SmartContractInvocationSigningRequestsOuterClass
                      .RejectSmartContractInvocationSigningRequestRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_invocation_signing_requests
                          .SmartContractInvocationSigningRequestsOuterClass
                          .RejectSmartContractInvocationSigningRequestResponse>)
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

  private abstract static class SmartContractInvocationSigningRequestsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SmartContractInvocationSigningRequestsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.swisschain.sirius.vaultApi.generated.smart_contract_invocation_signing_requests
          .SmartContractInvocationSigningRequestsOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SmartContractInvocationSigningRequests");
    }
  }

  private static final class SmartContractInvocationSigningRequestsFileDescriptorSupplier
      extends SmartContractInvocationSigningRequestsBaseDescriptorSupplier {
    SmartContractInvocationSigningRequestsFileDescriptorSupplier() {}
  }

  private static final class SmartContractInvocationSigningRequestsMethodDescriptorSupplier
      extends SmartContractInvocationSigningRequestsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SmartContractInvocationSigningRequestsMethodDescriptorSupplier(String methodName) {
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
      synchronized (SmartContractInvocationSigningRequestsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(
                          new SmartContractInvocationSigningRequestsFileDescriptorSupplier())
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
