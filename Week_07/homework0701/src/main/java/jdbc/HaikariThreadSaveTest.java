package jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Slf4j
public class HaikariThreadSaveTest implements CommandLineRunner {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(HaikariThreadSaveTest.class);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("begin to insert data......");
        showConnection();
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(16);
        for (int i = 0 ; i< 100;i++){
            executor.execute(() ->{
                try {
                    saveData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        };
        executor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("execution time"+ (endTime - startTime)/1000.00 + "s");

    }




    private void saveData() throws SQLException {
        long startTime = System.currentTimeMillis();
        for(int i = 0;i<10000;i++){
            jdbcTemplate.execute("insert  into summer_user (username,password,phone,email) values('小小罗','123456','13412343456','adsad@163.com')");
        }
        long endTime = System.currentTimeMillis();
        double executeTime = (endTime - startTime)/1000.00;
        log.info("child thread execution time:"+executeTime+"s");
    }

    private void showConnection() throws SQLException {
        log.info(dataSource.toString());
        Connection conn = dataSource.getConnection();
        log.info(conn.toString());
        conn.close();
    }
}
