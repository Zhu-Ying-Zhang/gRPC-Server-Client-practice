import io.grpc.stub.StreamObserver;
import somatt.login.LoginReply;
import somatt.login.LoginRequest;
import somatt.login.UserLoginGrpc;

public class LoginService extends UserLoginGrpc.UserLoginImplBase {

    private final String username = "joeyz.zhu";
    private final String password = "a123456";

    @Override
    public void userLogin(LoginRequest request, StreamObserver<LoginReply> responseObserver) {
        LoginReply reply;
        if (!username.equals(request.getUsermane())){
            reply = LoginReply.newBuilder().setStatusCode(200).setMessage("User not exist").build();
        }else if (!password.equals(request.getPassword())){
            reply = LoginReply.newBuilder().setStatusCode(200).setMessage("User password incorrect").build();
        }else {
            reply = LoginReply.newBuilder().setStatusCode(200).setMessage("success").build();
        }
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
