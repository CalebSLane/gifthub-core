package ca.csl.gifthub.core.persistence.service.account;

import java.util.List;
import java.util.Optional;

import ca.csl.gifthub.core.model.account.User;
import ca.csl.gifthub.core.persistence.service.PersistentObjectService;

public interface UserService extends PersistentObjectService<User, Integer> {

    Optional<User> getUserByUsername(String username);

    User addNewUser(String username, String plaintextPassword, String email);

    User editExistingUser(int id, String username, String password, String email);

    void removeUser(int id);

    boolean removeAllUsers(List<Integer> ids);
}
