package loadBean.config;

import loadBean.Entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("student.properties")
public class SpringConfig {
    @Bean
    public Student student(){
        return new Student();
    }
}
