import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class gRPCServer {

    private final int port;
    private final Server server;

    public gRPCServer(int port){
        this(ServerBuilder.forPort(port), port);
    }

    public gRPCServer(ServerBuilder serverBuilder, int port){
        this.port = port;
        HelloService helloService = new HelloService();
        LoginService loginService = new LoginService();
        this.server = serverBuilder.addService(helloService).addService(loginService).build();
    }

    public void start() throws IOException{
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                try {
                    gRPCServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void stop() throws InterruptedException {
        if (server != null){
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null){
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        gRPCServer gRPCServer = new gRPCServer(8080);
        gRPCServer.start();
        gRPCServer.blockUntilShutdown();
    }
}
