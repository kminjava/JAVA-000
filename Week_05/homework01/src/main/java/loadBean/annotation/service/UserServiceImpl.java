package loadBean.annotation.service;

import loadBean.annotation.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService{
    @Resource(name = "userDao")
    private UserDao userDao;
    @Override
    public void save() {
        this.userDao.save();
        System.out.println("userService执行save()方法");
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
