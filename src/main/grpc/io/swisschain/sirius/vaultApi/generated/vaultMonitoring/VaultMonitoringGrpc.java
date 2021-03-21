package io.swisschain.sirius.vaultApi.generated.vaultMonitoring;

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
    comments = "Source: vault-monitoring.proto")
public final class VaultMonitoringGrpc {

  private VaultMonitoringGrpc() {}

  public static final String SERVICE_NAME =
      "swisschain.sirius.vaultApi.vaultMonitoring.VaultMonitoring";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .vaultMonitoring
              .VaultMonitoringOuterClass
              .UpdateVaultStatusRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .vaultMonitoring
              .VaultMonitoringOuterClass
              .UpdateVaultStatusResponse>
      getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Update",
      requestType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .vaultMonitoring
              .VaultMonitoringOuterClass
              .UpdateVaultStatusRequest
              .class,
      responseType =
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .vaultMonitoring
              .VaultMonitoringOuterClass
              .UpdateVaultStatusResponse
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .vaultMonitoring
              .VaultMonitoringOuterClass
              .UpdateVaultStatusRequest,
          io.swisschain
              .sirius
              .vaultApi
              .generated
              .vaultMonitoring
              .VaultMonitoringOuterClass
              .UpdateVaultStatusResponse>
      getUpdateMethod() {
    io.grpc.MethodDescriptor<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .vaultMonitoring
                .VaultMonitoringOuterClass
                .UpdateVaultStatusRequest,
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .vaultMonitoring
                .VaultMonitoringOuterClass
                .UpdateVaultStatusResponse>
        getUpdateMethod;
    if ((getUpdateMethod = VaultMonitoringGrpc.getUpdateMethod) == null) {
      synchronized (VaultMonitoringGrpc.class) {
        if ((getUpdateMethod = VaultMonitoringGrpc.getUpdateMethod) == null) {
          VaultMonitoringGrpc.getUpdateMethod =
              getUpdateMethod =
                  io.grpc.MethodDescriptor
                      .<io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .vaultMonitoring
                              .VaultMonitoringOuterClass
                              .UpdateVaultStatusRequest,
                          io.swisschain
                              .sirius
                              .vaultApi
                              .generated
                              .vaultMonitoring
                              .VaultMonitoringOuterClass
                              .UpdateVaultStatusResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Update"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.vaultMonitoring
                                  .VaultMonitoringOuterClass.UpdateVaultStatusRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              io.swisschain.sirius.vaultApi.generated.vaultMonitoring
                                  .VaultMonitoringOuterClass.UpdateVaultStatusResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(new VaultMonitoringMethodDescriptorSupplier("Update"))
                      .build();
        }
      }
    }
    return getUpdateMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static VaultMonitoringStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VaultMonitoringStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<VaultMonitoringStub>() {
          @java.lang.Override
          public VaultMonitoringStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new VaultMonitoringStub(channel, callOptions);
          }
        };
    return VaultMonitoringStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VaultMonitoringBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VaultMonitoringBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<VaultMonitoringBlockingStub>() {
          @java.lang.Override
          public VaultMonitoringBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new VaultMonitoringBlockingStub(channel, callOptions);
          }
        };
    return VaultMonitoringBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static VaultMonitoringFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VaultMonitoringFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<VaultMonitoringFutureStub>() {
          @java.lang.Override
          public VaultMonitoringFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new VaultMonitoringFutureStub(channel, callOptions);
          }
        };
    return VaultMonitoringFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class VaultMonitoringImplBase implements io.grpc.BindableService {

    /** */
    public void update(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .vaultMonitoring
                .VaultMonitoringOuterClass
                .UpdateVaultStatusRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .vaultMonitoring
                    .VaultMonitoringOuterClass
                    .UpdateVaultStatusResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getUpdateMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .vaultMonitoring
                          .VaultMonitoringOuterClass
                          .UpdateVaultStatusRequest,
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .vaultMonitoring
                          .VaultMonitoringOuterClass
                          .UpdateVaultStatusResponse>(this, METHODID_UPDATE)))
          .build();
    }
  }

  /** */
  public static final class VaultMonitoringStub
      extends io.grpc.stub.AbstractAsyncStub<VaultMonitoringStub> {
    private VaultMonitoringStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VaultMonitoringStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VaultMonitoringStub(channel, callOptions);
    }

    /** */
    public void update(
        io.swisschain
                .sirius
                .vaultApi
                .generated
                .vaultMonitoring
                .VaultMonitoringOuterClass
                .UpdateVaultStatusRequest
            request,
        io.grpc.stub.StreamObserver<
                io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .vaultMonitoring
                    .VaultMonitoringOuterClass
                    .UpdateVaultStatusResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /** */
  public static final class VaultMonitoringBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<VaultMonitoringBlockingStub> {
    private VaultMonitoringBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VaultMonitoringBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VaultMonitoringBlockingStub(channel, callOptions);
    }

    /** */
    public io.swisschain
            .sirius
            .vaultApi
            .generated
            .vaultMonitoring
            .VaultMonitoringOuterClass
            .UpdateVaultStatusResponse
        update(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .vaultMonitoring
                    .VaultMonitoringOuterClass
                    .UpdateVaultStatusRequest
                request) {
      return blockingUnaryCall(getChannel(), getUpdateMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class VaultMonitoringFutureStub
      extends io.grpc.stub.AbstractFutureStub<VaultMonitoringFutureStub> {
    private VaultMonitoringFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VaultMonitoringFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VaultMonitoringFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            io.swisschain
                .sirius
                .vaultApi
                .generated
                .vaultMonitoring
                .VaultMonitoringOuterClass
                .UpdateVaultStatusResponse>
        update(
            io.swisschain
                    .sirius
                    .vaultApi
                    .generated
                    .vaultMonitoring
                    .VaultMonitoringOuterClass
                    .UpdateVaultStatusRequest
                request) {
      return futureUnaryCall(getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_UPDATE = 0;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final VaultMonitoringImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(VaultMonitoringImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPDATE:
          serviceImpl.update(
              (io.swisschain
                      .sirius
                      .vaultApi
                      .generated
                      .vaultMonitoring
                      .VaultMonitoringOuterClass
                      .UpdateVaultStatusRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      io.swisschain
                          .sirius
                          .vaultApi
                          .generated
                          .vaultMonitoring
                          .VaultMonitoringOuterClass
                          .UpdateVaultStatusResponse>)
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

  private abstract static class VaultMonitoringBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    VaultMonitoringBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.swisschain.sirius.vaultApi.generated.vaultMonitoring.VaultMonitoringOuterClass
          .getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("VaultMonitoring");
    }
  }

  private static final class VaultMonitoringFileDescriptorSupplier
      extends VaultMonitoringBaseDescriptorSupplier {
    VaultMonitoringFileDescriptorSupplier() {}
  }

  private static final class VaultMonitoringMethodDescriptorSupplier
      extends VaultMonitoringBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    VaultMonitoringMethodDescriptorSupplier(String methodName) {
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
      synchronized (VaultMonitoringGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new VaultMonitoringFileDescriptorSupplier())
                      .addMethod(getUpdateMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
