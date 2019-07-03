package ca.csl.gifthub.core.persistence.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.csl.gifthub.core.model.account.Role;
import ca.csl.gifthub.core.persistence.repository.PersistentObjectRepository;

@Repository
public interface RoleRepository extends PersistentObjectRepository<Role, Integer>, JpaRepository<Role, Integer> {

    Role findFirstRoleByName(String name);

}
