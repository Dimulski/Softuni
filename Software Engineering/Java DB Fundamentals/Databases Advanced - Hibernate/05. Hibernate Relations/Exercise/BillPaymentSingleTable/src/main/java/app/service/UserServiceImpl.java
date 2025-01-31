package app.service;

import app.domain.User;
import app.repositories.UserRepository;
import app.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(User user) {
        this.userRepository.saveAndFlush(user);
    }
}
