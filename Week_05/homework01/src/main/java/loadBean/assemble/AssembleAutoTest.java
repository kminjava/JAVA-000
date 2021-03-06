package loadBean.assemble;

import loadBean.annotation.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AssembleAutoTest {
    public static void main(String[] args) {
        // 定义配置文件路径
        String xmlPath = "/beansAuto.xml";
        // 加载配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(xmlPath);
        // 获取UserController实例
        UserServiceImpl userService =
                (UserServiceImpl) applicationContext.getBean("userService");
        // 调用UserController中的save()方法
        userService.save();
    }
}
