import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import somatt.hello.GreeterGrpc;
import somatt.hello.GreeterGrpc.GreeterBlockingStub;
import somatt.hello.HelloReply;
import somatt.hello.HelloRequest;
import somatt.login.LoginReply;
import somatt.login.LoginRequest;
import somatt.login.UserLoginGrpc;
import somatt.login.UserLoginGrpc.UserLoginBlockingStub;

import java.util.concurrent.TimeUnit;

public class HelloClient {

    private final ManagedChannel managedChannel;
    private final GreeterBlockingStub greeterBlockingStub;
    private final UserLoginBlockingStub userLoginBlockingStub;

    public HelloClient(String host, int port){
        managedChannel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        greeterBlockingStub = GreeterGrpc.newBlockingStub(managedChannel);
        userLoginBlockingStub = UserLoginGrpc.newBlockingStub(managedChannel);
    }

    public void shutdown() throws InterruptedException {
        managedChannel.awaitTermination(5, TimeUnit.SECONDS);
    }

    public void createHello(){
        HelloRequest request = HelloRequest.newBuilder().setName("11111").build();
        HelloReply response = greeterBlockingStub.sayHello(request);
        System.out.println(response.getMessage());
    }

    public void userLogin(){
        LoginRequest request = LoginRequest.newBuilder().setUsermane("joeyz.zhu").setPassword("a123456").build();
        LoginReply reply = userLoginBlockingStub.userLogin(request);
        System.out.println(reply.getStatusCode() + " " + reply.getMessage());
    }

    public static void main(String[] args) throws InterruptedException {
        HelloClient client = new HelloClient("192.168.0.16", 8080);

        try {
            client.createHello();
            client.userLogin();
        } finally {
            client.shutdown();
        }

    }
}
