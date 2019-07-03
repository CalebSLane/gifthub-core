package ca.csl.gifthub.core.persistence.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.csl.gifthub.core.model.account.Privilege;
import ca.csl.gifthub.core.persistence.repository.PersistentObjectRepository;

@Repository
public interface PrivilegeRepository
        extends PersistentObjectRepository<Privilege, Integer>, JpaRepository<Privilege, Integer> {

    Privilege findFirstPrivilegeByName(String name);

}
