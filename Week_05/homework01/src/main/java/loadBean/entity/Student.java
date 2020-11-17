package loadBean.Entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

@Scope("singleton")
public class Student {
    @Value("${student.name}")
    public String name;

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
