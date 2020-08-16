import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HelloServer {

    private final int port;
    private final Server server;

    public HelloServer(int port){
        this(ServerBuilder.forPort(port), port);
    }

    public HelloServer(ServerBuilder serverBuilder, int port){
        this.port = port;
        HelloService helloService = new HelloService();
        this.server = serverBuilder.addService(helloService).build();
    }

    public void start() throws IOException{
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                try {
                    HelloServer.this.stop();
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
        HelloServer helloServer = new HelloServer(8080);
        helloServer.start();
        helloServer.blockUntilShutdown();
    }
}
