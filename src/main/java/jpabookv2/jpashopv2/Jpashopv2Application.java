package jpabookv2.jpashopv2;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Jpashopv2Application {

	public static void main(String[] args) {
		SpringApplication.run(Jpashopv2Application.class, args);
	}

//	@Bean
//	Hibernate5JakartaModule hibernate5Module() {
//		return new Hibernate5JakartaModule();
//	}
	// 여기 뭐 어떻게 하라는거지?

	@Bean
	Hibernate5JakartaModule hibernate5Module() {
		Hibernate5JakartaModule hibernate5Module = new Hibernate5JakartaModule();
		//강제 지연 로딩 설정
		hibernate5Module.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true);
		return hibernate5Module;
	}

}
