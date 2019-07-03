package ca.csl.gifthub.core.persistence.service.account;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.csl.gifthub.core.model.account.Privilege;
import ca.csl.gifthub.core.persistence.repository.account.PrivilegeRepository;
import ca.csl.gifthub.core.persistence.service.PersistentObjectServiceImp;

@Service
@Transactional
public class PrivilegeServiceImp extends PersistentObjectServiceImp<Privilege, Integer, PrivilegeRepository>
        implements PrivilegeService {

    private PrivilegeRepository privilegeRepository;

    public PrivilegeServiceImp(PrivilegeRepository privilegeRepository) {
        super(Privilege.class, privilegeRepository);
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Optional<Privilege> getByName(String name) {
        return Optional.ofNullable(this.privilegeRepository.findFirstPrivilegeByName(name));
    }

}
