package my.test.java.service;

import my.test.java.entity.User;
import my.test.java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser() {
        return userRepository.save(new User(1l, "11Name", "password", new Date(), new Date()));
    }
}
