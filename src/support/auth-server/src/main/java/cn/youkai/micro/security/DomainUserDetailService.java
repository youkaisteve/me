package cn.youkai.micro.security;

import cn.youkai.micro.dao.UserDao;
import cn.youkai.micro.domain.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * @author youkai
 * @date 2017/10/27.
 */
@Service
public class DomainUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser realUser = userDao.findByUsername(username);
        if (null == realUser) {
            throw new UsernameNotFoundException(username);
        }

        return new User(realUser.getUsername(), realUser.getPassword(), Collections.emptyList());
    }
}
