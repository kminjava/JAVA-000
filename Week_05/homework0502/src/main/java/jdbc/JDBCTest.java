package jdbc;

import jdbc.dto.Student;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JDBCTest {
    public static void main(String[] args) throws SQLException {
        //通过工具类获取数据库连接对象
        Connection con = JDBCUtils.getConnection();
        //通过连接创建数据库执行对象
        Statement sta = con.createStatement();
        //为查询的结果集准备接收对象
        ResultSet rs = null;
        String insertSql = "INSERT INTO STUDENT VALUES(1134,'小白白',13)";
        int r = sta.executeUpdate(insertSql);
        System.out.println("插入执行结果:"+r);


        String upateSql = "UPDATE STUDENT SET NAME = '静静' WHERE ID = 1134";
        int u = sta.executeUpdate(upateSql);
        System.out.println("更新执行结果:"+u);

        String querySql = "SELECT ID,NAME,AGE FROM STUDENT";
        rs = sta.executeQuery(querySql);

        List<Student> studentList = new ArrayList<>();
        while(rs.next()){
          Integer id = rs.getInt("id");
          String name = rs.getString("name");
          Integer age = rs.getInt("age");
            Student student = new Student();
            student.setId(id);
            student.setName(name);
            student.setAge(age);
            studentList.add(student);
        }

        String insertSql1 = "INSERT INTO STUDENT VALUES(1135,'小白白',14)";
        PreparedStatement preparedStatement = con.prepareStatement(insertSql1);
        preparedStatement.addBatch(upateSql);
        studentList.forEach(row -> log.info(row.toString()));
        JDBCUtils.close(con,sta,rs);

    }
}
