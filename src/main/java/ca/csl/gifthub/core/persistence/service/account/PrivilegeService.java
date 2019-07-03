package ca.csl.gifthub.core.persistence.service.account;

import java.util.Optional;

import ca.csl.gifthub.core.model.account.Privilege;
import ca.csl.gifthub.core.persistence.service.PersistentObjectService;

public interface PrivilegeService extends PersistentObjectService<Privilege, Integer> {

    Optional<Privilege> getByName(String name);
}
