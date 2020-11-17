package loadBean.assemble;

import loadBean.Entity.Student;
import loadBean.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class AssembleConfigTest {
    public static void main(String[] args) {
        //获取Spring的IOC容器
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringConfig.class);
        //从容器中获取bean
        Student stu = (Student) applicationContext.getBean("student");
        //获取环境变量
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        String property = environment.getProperty("student.name");
        System.out.println(property);
        applicationContext.close();
    }
}
