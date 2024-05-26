import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

public class DockerConnectTest {

    public static void main(String[] args) {
        DockerClient build = DockerClientBuilder.getInstance("tcp://localhost:2375").build();
        System.out.println(build.infoCmd().exec());


    }

}
