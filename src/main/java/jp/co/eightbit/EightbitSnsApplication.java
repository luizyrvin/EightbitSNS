package jp.co.eightbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EightbitSnsApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(EightbitSnsApplication.class, args);
	}
	
	@Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
       return application.sources(EightbitSnsApplication.class); // DemoApplicationの名前はプロジェクトによって異なる場合があります
   }
}
