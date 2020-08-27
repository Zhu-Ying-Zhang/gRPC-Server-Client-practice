import io.grpc.stub.StreamObserver;
import somatt.hello.GreeterGrpc;
import somatt.hello.HelloReply;
import somatt.hello.HelloRequest;

class HelloService extends GreeterGrpc.GreeterImplBase{

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

        String name = request.getName();

        if (name.isEmpty()){
            name = "Default Name";
        }

        HelloReply reply = HelloReply.newBuilder().setMessage(name).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}