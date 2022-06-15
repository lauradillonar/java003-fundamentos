package com.confluenciacreativa.fundamentos.service;

import com.confluenciacreativa.fundamentos.entity.User;
import com.confluenciacreativa.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    private final Log LOG = LogFactory.getLog(UserService.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveTransactional(List<User> users){
        users.stream()
                .peek(user -> LOG.info("Usuario insertado: "+user))
                .forEach(userRepository::save);
                //.forEach(user -> userRepository.save(user));

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
