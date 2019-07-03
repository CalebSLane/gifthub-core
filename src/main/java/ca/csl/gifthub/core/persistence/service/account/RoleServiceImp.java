package ca.csl.gifthub.core.persistence.service.account;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.csl.gifthub.core.model.account.Role;
import ca.csl.gifthub.core.persistence.repository.account.RoleRepository;
import ca.csl.gifthub.core.persistence.service.PersistentObjectServiceImp;

@Service
@Transactional
public class RoleServiceImp extends PersistentObjectServiceImp<Role, Integer, RoleRepository> implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        super(Role.class, roleRepository);
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> getByName(String name) {
        return Optional.ofNullable(this.roleRepository.findFirstRoleByName(name));
    }

}
