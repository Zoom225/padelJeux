package padeJeux1.com;

import org.springframework.boot.SpringApplication;

public class TestPadeJeux1Application {

	public static void main(String[] args) {
		SpringApplication.from(PadeJeux1Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
