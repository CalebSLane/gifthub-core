package ca.csl.gifthub.core.persistence.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.csl.gifthub.core.model.account.User;
import ca.csl.gifthub.core.persistence.repository.PersistentObjectRepository;

@Repository
public interface UserRepository extends PersistentObjectRepository<User, Integer>, JpaRepository<User, Integer> {

    User findFirstByUsername(String username);

}
