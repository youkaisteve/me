package cn.youkai.micro.dao;

import cn.youkai.micro.domain.ApplicationUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @author youkai
 * @date 2017/10/27.
 */
@Mapper
public interface UserDao {
    ArrayList<ApplicationUser> getUserList();

    ApplicationUser findByUsername(String userName);

    void save(ApplicationUser user);
}
