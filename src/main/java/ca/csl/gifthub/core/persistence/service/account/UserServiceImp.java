package ca.csl.gifthub.core.persistence.service.account;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.csl.gifthub.core.model.account.User;
import ca.csl.gifthub.core.persistence.repository.account.UserRepository;
import ca.csl.gifthub.core.persistence.service.PersistentObjectServiceImp;

@Service
@Transactional
public class UserServiceImp extends PersistentObjectServiceImp<User, Integer, UserRepository> implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImp.class);
    private static final boolean DELETE_AS_BATCH_ONLY = false;

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        super(User.class, userRepository);
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(this.userRepository.findFirstByUsername(username));
    }

    @Override
    public User addNewUser(String username, String plaintextPassword, String email) {
        User user = new User(username, plaintextPassword, email).withEncryptedPassword();
        this.insert(user);
        return user;
    }

    @Override
    public User editExistingUser(int id, String username, String password, String email) {
        User user = this.getById(id);
        user.editFields(username, password, email);
        return this.update(user);
    }

    @Override
    public void removeUser(int id) {
        User user = this.getById(id);
        this.delete(user);
    }

    @Override
    public boolean removeAllUsers(List<Integer> ids) {
        boolean successAll = true;
        if (DELETE_AS_BATCH_ONLY) {
            this.deleteAllById(ids);
        } else {
            for (Integer id : ids) {
                try {
                    this.deleteById(id);
                } catch (RuntimeException e) {
                    successAll = false;
                    LOG.error("could not delete user with id: " + id, e);
                }
            }
        }
        return successAll;
    }

}
