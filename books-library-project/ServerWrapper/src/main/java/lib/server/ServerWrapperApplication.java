package lib.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(scanBasePackages = "lib")
public class ServerWrapperApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(ServerWrapperApplication.class, args);
	}

}
