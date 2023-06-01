package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RolesDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder encoder;
    private final RolesDao rolesDao;

    public UserServiceImpl(UserDao userDao, PasswordEncoder encoder, RolesDao rolesDao) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.rolesDao = rolesDao;
    }

    @Override
    @Transactional
    public void addUser(User user, String[] role) {
        user.setRoles(rolesDao.getRoles(role));
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.addUser(user, role);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user, String[] role) {
        user.setRoles(rolesDao.getRoles(role));
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.updateUser(user, role);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public User findByUsername(String userName) {
        return userDao.findByUsername(userName);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = findByUsername(username);
        if (byUsername == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return byUsername;
    }
}
