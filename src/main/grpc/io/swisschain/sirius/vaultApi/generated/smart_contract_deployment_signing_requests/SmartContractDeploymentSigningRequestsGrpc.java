package io.swisschain.sirius.vaultApi.generated.smart_contract_deployment_signing_requests;

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
    comments = "Source: smart-contract-deployment-signing-requests.proto")
public final class SmartContractDeploymentSigningRequestsGrpc {

  private SmartContractDeploymentSigningRequestsGrpc() {}

  public static final String SERVICE_NAME =
      "swisschain.sirius.vaultApi.smartContractDeploymentSigningRequests.SmartContractDeploymentSigningRequests";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .GetSmartContractDeploymentSigningRequestsRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .GetSmartContractDeploymentSigningRequestsResponse>
      getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Get",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .GetSmartContractDeploymentSigningRequestsRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .GetSmartContractDeploymentSigningRequestsResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .GetSmartContractDeploymentSigningRequestsRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .GetSmartContractDeploymentSigningRequestsResponse>
      getGetMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .GetSmartContractDeploymentSigningRequestsRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .GetSmartContractDeploymentSigningRequestsResponse>
        getGetMethod;
    if ((getGetMethod = SmartContractDeploymentSigningRequestsGrpc.getGetMethod) == null) {
      synchronized (SmartContractDeploymentSigningRequestsGrpc.class) {
        if ((getGetMethod = SmartContractDeploymentSigningRequestsGrpc.getGetMethod) == null) {
          SmartContractDeploymentSigningRequestsGrpc.getGetMethod =
              getGetMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_deployment_signing_requests
                              .SmartContractDeploymentSigningRequestsOuterClass
                              .GetSmartContractDeploymentSigningRequestsRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_deployment_signing_requests
                              .SmartContractDeploymentSigningRequestsOuterClass
                              .GetSmartContractDeploymentSigningRequestsResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Get"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_deployment_signing_requests
                                  .SmartContractDeploymentSigningRequestsOuterClass
                                  .GetSmartContractDeploymentSigningRequestsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_deployment_signing_requests
                                  .SmartContractDeploymentSigningRequestsOuterClass
                                  .GetSmartContractDeploymentSigningRequestsResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new SmartContractDeploymentSigningRequestsMethodDescriptorSupplier("Get"))
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
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .ConfirmSmartContractDeploymentSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .ConfirmSmartContractDeploymentSigningRequestResponse>
      getConfirmMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Confirm",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .ConfirmSmartContractDeploymentSigningRequestRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .ConfirmSmartContractDeploymentSigningRequestResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .ConfirmSmartContractDeploymentSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .ConfirmSmartContractDeploymentSigningRequestResponse>
      getConfirmMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .ConfirmSmartContractDeploymentSigningRequestRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .ConfirmSmartContractDeploymentSigningRequestResponse>
        getConfirmMethod;
    if ((getConfirmMethod = SmartContractDeploymentSigningRequestsGrpc.getConfirmMethod) == null) {
      synchronized (SmartContractDeploymentSigningRequestsGrpc.class) {
        if ((getConfirmMethod = SmartContractDeploymentSigningRequestsGrpc.getConfirmMethod)
            == null) {
          SmartContractDeploymentSigningRequestsGrpc.getConfirmMethod =
              getConfirmMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_deployment_signing_requests
                              .SmartContractDeploymentSigningRequestsOuterClass
                              .ConfirmSmartContractDeploymentSigningRequestRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_deployment_signing_requests
                              .SmartContractDeploymentSigningRequestsOuterClass
                              .ConfirmSmartContractDeploymentSigningRequestResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Confirm"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_deployment_signing_requests
                                  .SmartContractDeploymentSigningRequestsOuterClass
                                  .ConfirmSmartContractDeploymentSigningRequestRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_deployment_signing_requests
                                  .SmartContractDeploymentSigningRequestsOuterClass
                                  .ConfirmSmartContractDeploymentSigningRequestResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new SmartContractDeploymentSigningRequestsMethodDescriptorSupplier(
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
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .RejectSmartContractDeploymentSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .RejectSmartContractDeploymentSigningRequestResponse>
      getRejectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Reject",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .RejectSmartContractDeploymentSigningRequestRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .RejectSmartContractDeploymentSigningRequestResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .RejectSmartContractDeploymentSigningRequestRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .smart_contract_deployment_signing_requests
              .SmartContractDeploymentSigningRequestsOuterClass
              .RejectSmartContractDeploymentSigningRequestResponse>
      getRejectMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .RejectSmartContractDeploymentSigningRequestRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .RejectSmartContractDeploymentSigningRequestResponse>
        getRejectMethod;
    if ((getRejectMethod = SmartContractDeploymentSigningRequestsGrpc.getRejectMethod) == null) {
      synchronized (SmartContractDeploymentSigningRequestsGrpc.class) {
        if ((getRejectMethod = SmartContractDeploymentSigningRequestsGrpc.getRejectMethod)
            == null) {
          SmartContractDeploymentSigningRequestsGrpc.getRejectMethod =
              getRejectMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_deployment_signing_requests
                              .SmartContractDeploymentSigningRequestsOuterClass
                              .RejectSmartContractDeploymentSigningRequestRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .smart_contract_deployment_signing_requests
                              .SmartContractDeploymentSigningRequestsOuterClass
                              .RejectSmartContractDeploymentSigningRequestResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Reject"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_deployment_signing_requests
                                  .SmartContractDeploymentSigningRequestsOuterClass
                                  .RejectSmartContractDeploymentSigningRequestRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated
                                  .smart_contract_deployment_signing_requests
                                  .SmartContractDeploymentSigningRequestsOuterClass
                                  .RejectSmartContractDeploymentSigningRequestResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new SmartContractDeploymentSigningRequestsMethodDescriptorSupplier(
                              "Reject"))
                      .build();
        }
      }
    }
    return getRejectMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static SmartContractDeploymentSigningRequestsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SmartContractDeploymentSigningRequestsStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<SmartContractDeploymentSigningRequestsStub>() {
          @java.lang.Override
          public SmartContractDeploymentSigningRequestsStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new SmartContractDeploymentSigningRequestsStub(channel, callOptions);
          }
        };
    return SmartContractDeploymentSigningRequestsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SmartContractDeploymentSigningRequestsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SmartContractDeploymentSigningRequestsBlockingStub>
        factory =
            new io.grpc.stub.AbstractStub.StubFactory<
                SmartContractDeploymentSigningRequestsBlockingStub>() {
              @java.lang.Override
              public SmartContractDeploymentSigningRequestsBlockingStub newStub(
                  io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new SmartContractDeploymentSigningRequestsBlockingStub(channel, callOptions);
              }
            };
    return SmartContractDeploymentSigningRequestsBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static SmartContractDeploymentSigningRequestsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SmartContractDeploymentSigningRequestsFutureStub>
        factory =
            new io.grpc.stub.AbstractStub.StubFactory<
                SmartContractDeploymentSigningRequestsFutureStub>() {
              @java.lang.Override
              public SmartContractDeploymentSigningRequestsFutureStub newStub(
                  io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new SmartContractDeploymentSigningRequestsFutureStub(channel, callOptions);
              }
            };
    return SmartContractDeploymentSigningRequestsFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class SmartContractDeploymentSigningRequestsImplBase
      implements io.grpc.BindableService {

    /** */
    public void get(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .GetSmartContractDeploymentSigningRequestsRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .GetSmartContractDeploymentSigningRequestsResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /** */
    public void confirm(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .ConfirmSmartContractDeploymentSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .ConfirmSmartContractDeploymentSigningRequestResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getConfirmMethod(), responseObserver);
    }

    /** */
    public void reject(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .RejectSmartContractDeploymentSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .RejectSmartContractDeploymentSigningRequestResponse>
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
                          .smart_contract_deployment_signing_requests
                          .SmartContractDeploymentSigningRequestsOuterClass
                          .GetSmartContractDeploymentSigningRequestsRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_deployment_signing_requests
                          .SmartContractDeploymentSigningRequestsOuterClass
                          .GetSmartContractDeploymentSigningRequestsResponse>(this, METHODID_GET)))
          .addMethod(
              getConfirmMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_deployment_signing_requests
                          .SmartContractDeploymentSigningRequestsOuterClass
                          .ConfirmSmartContractDeploymentSigningRequestRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_deployment_signing_requests
                          .SmartContractDeploymentSigningRequestsOuterClass
                          .ConfirmSmartContractDeploymentSigningRequestResponse>(
                      this, METHODID_CONFIRM)))
          .addMethod(
              getRejectMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_deployment_signing_requests
                          .SmartContractDeploymentSigningRequestsOuterClass
                          .RejectSmartContractDeploymentSigningRequestRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_deployment_signing_requests
                          .SmartContractDeploymentSigningRequestsOuterClass
                          .RejectSmartContractDeploymentSigningRequestResponse>(
                      this, METHODID_REJECT)))
          .build();
    }
  }

  /** */
  public static final class SmartContractDeploymentSigningRequestsStub
      extends io.grpc.stub.AbstractAsyncStub<SmartContractDeploymentSigningRequestsStub> {
    private SmartContractDeploymentSigningRequestsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartContractDeploymentSigningRequestsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SmartContractDeploymentSigningRequestsStub(channel, callOptions);
    }

    /** */
    public void get(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .GetSmartContractDeploymentSigningRequestsRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .GetSmartContractDeploymentSigningRequestsResponse>
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
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .ConfirmSmartContractDeploymentSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .ConfirmSmartContractDeploymentSigningRequestResponse>
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
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .RejectSmartContractDeploymentSigningRequestRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .RejectSmartContractDeploymentSigningRequestResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRejectMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /** */
  public static final class SmartContractDeploymentSigningRequestsBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<
          SmartContractDeploymentSigningRequestsBlockingStub> {
    private SmartContractDeploymentSigningRequestsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartContractDeploymentSigningRequestsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SmartContractDeploymentSigningRequestsBlockingStub(channel, callOptions);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .smart_contract_deployment_signing_requests
            .SmartContractDeploymentSigningRequestsOuterClass
            .GetSmartContractDeploymentSigningRequestsResponse
        get(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .GetSmartContractDeploymentSigningRequestsRequest
                request) {
      return blockingUnaryCall(getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .smart_contract_deployment_signing_requests
            .SmartContractDeploymentSigningRequestsOuterClass
            .ConfirmSmartContractDeploymentSigningRequestResponse
        confirm(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .ConfirmSmartContractDeploymentSigningRequestRequest
                request) {
      return blockingUnaryCall(getChannel(), getConfirmMethod(), getCallOptions(), request);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .smart_contract_deployment_signing_requests
            .SmartContractDeploymentSigningRequestsOuterClass
            .RejectSmartContractDeploymentSigningRequestResponse
        reject(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .RejectSmartContractDeploymentSigningRequestRequest
                request) {
      return blockingUnaryCall(getChannel(), getRejectMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class SmartContractDeploymentSigningRequestsFutureStub
      extends io.grpc.stub.AbstractFutureStub<SmartContractDeploymentSigningRequestsFutureStub> {
    private SmartContractDeploymentSigningRequestsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartContractDeploymentSigningRequestsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SmartContractDeploymentSigningRequestsFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .GetSmartContractDeploymentSigningRequestsResponse>
        get(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .GetSmartContractDeploymentSigningRequestsRequest
                request) {
      return futureUnaryCall(getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .ConfirmSmartContractDeploymentSigningRequestResponse>
        confirm(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .ConfirmSmartContractDeploymentSigningRequestRequest
                request) {
      return futureUnaryCall(getChannel().newCall(getConfirmMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .smart_contract_deployment_signing_requests
                .SmartContractDeploymentSigningRequestsOuterClass
                .RejectSmartContractDeploymentSigningRequestResponse>
        reject(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .smart_contract_deployment_signing_requests
                    .SmartContractDeploymentSigningRequestsOuterClass
                    .RejectSmartContractDeploymentSigningRequestRequest
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
    private final SmartContractDeploymentSigningRequestsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SmartContractDeploymentSigningRequestsImplBase serviceImpl, int methodId) {
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
                      .smart_contract_deployment_signing_requests
                      .SmartContractDeploymentSigningRequestsOuterClass
                      .GetSmartContractDeploymentSigningRequestsRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_deployment_signing_requests
                          .SmartContractDeploymentSigningRequestsOuterClass
                          .GetSmartContractDeploymentSigningRequestsResponse>)
                  responseObserver);
          break;
        case METHODID_CONFIRM:
          serviceImpl.confirm(
              (io.swisschain
                      .sirius
                      .vaultApi
                      .generated
                      .smart_contract_deployment_signing_requests
                      .SmartContractDeploymentSigningRequestsOuterClass
                      .ConfirmSmartContractDeploymentSigningRequestRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_deployment_signing_requests
                          .SmartContractDeploymentSigningRequestsOuterClass
                          .ConfirmSmartContractDeploymentSigningRequestResponse>)
                  responseObserver);
          break;
        case METHODID_REJECT:
          serviceImpl.reject(
              (io.swisschain
                      .sirius
                      .vaultApi
                      .generated
                      .smart_contract_deployment_signing_requests
                      .SmartContractDeploymentSigningRequestsOuterClass
                      .RejectSmartContractDeploymentSigningRequestRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .smart_contract_deployment_signing_requests
                          .SmartContractDeploymentSigningRequestsOuterClass
                          .RejectSmartContractDeploymentSigningRequestResponse>)
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

  private abstract static class SmartContractDeploymentSigningRequestsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SmartContractDeploymentSigningRequestsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.swisschain.sirius.vaultApi.generated.smart_contract_deployment_signing_requests
          .SmartContractDeploymentSigningRequestsOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SmartContractDeploymentSigningRequests");
    }
  }

  private static final class SmartContractDeploymentSigningRequestsFileDescriptorSupplier
      extends SmartContractDeploymentSigningRequestsBaseDescriptorSupplier {
    SmartContractDeploymentSigningRequestsFileDescriptorSupplier() {}
  }

  private static final class SmartContractDeploymentSigningRequestsMethodDescriptorSupplier
      extends SmartContractDeploymentSigningRequestsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SmartContractDeploymentSigningRequestsMethodDescriptorSupplier(String methodName) {
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
      synchronized (SmartContractDeploymentSigningRequestsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(
                          new SmartContractDeploymentSigningRequestsFileDescriptorSupplier())
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
