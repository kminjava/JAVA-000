package mystarter.aop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "student")
public class SchoolProperties {
    private int id;
    private String name;
}
