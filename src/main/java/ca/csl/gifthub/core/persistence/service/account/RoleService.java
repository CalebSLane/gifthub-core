package ca.csl.gifthub.core.persistence.service.account;

import java.util.Optional;

import ca.csl.gifthub.core.model.account.Role;
import ca.csl.gifthub.core.persistence.service.PersistentObjectService;

public interface RoleService extends PersistentObjectService<Role, Integer> {

    Optional<Role> getByName(String name);
}
