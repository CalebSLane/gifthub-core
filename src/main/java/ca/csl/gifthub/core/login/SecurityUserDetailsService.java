package ca.csl.gifthub.core.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.csl.gifthub.core.model.account.Privilege;
import ca.csl.gifthub.core.model.account.Role;
import ca.csl.gifthub.core.model.account.User;
import ca.csl.gifthub.core.persistence.service.account.UserService;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private UserService userService;

    public SecurityUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = this.userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                "Unique Username not found, could be duplicates in database or it doesn't exist"));
        boolean disabled = user.getDisabled();
        boolean expired = user.isExpired();
        boolean credentialsExpired = user.areCredentialsExpired();
        boolean locked = user.getLocked();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), !disabled,
                !expired, !credentialsExpired, !locked, this.getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return this.getGrantedAuthorities(this.getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<Privilege> privileges = new ArrayList<>();
        for (Role role : roles) {
            privileges.addAll(role.getPrivileges());
        }
        List<String> privilegeNames = new ArrayList<>();
        for (Privilege privilege : privileges) {
            privilegeNames.add(privilege.getName());
        }
        return privilegeNames;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
