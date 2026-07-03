package project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.model.User;
import project.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final ValidationService validationService;
    private final UserRepository userRepository;

    public UserService(ValidationService validationService, UserRepository userRepository) {
        this.validationService = validationService;
        this.userRepository = userRepository;
    }

    public Long createUser(String username) {
        validationService.checkUserNameDuplication(username);
        User user = new User(username);
        return userRepository.save(user).getId();
    }
}
