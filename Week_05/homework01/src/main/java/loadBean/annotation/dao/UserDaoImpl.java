package loadBean.annotation.dao;

import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements UserDao{
    @Override
    public void save() {
        System.out.println("userDao执行save()方法");
    }
}
