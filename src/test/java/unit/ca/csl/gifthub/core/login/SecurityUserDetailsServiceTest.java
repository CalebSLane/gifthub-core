package ca.csl.gifthub.core.login;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ca.csl.gifthub.core.model.account.User;
import ca.csl.gifthub.core.persistence.service.account.UserService;

public class SecurityUserDetailsServiceTest {

    private SecurityUserDetailsService securityUsersDetailsService;
    private UserService userService;
    private User validUser;

    public static final String validUsername = "alice";
    public static final String validEmail = "alice@alice.com";
    public static final String validPassword = "AliceHasAValidPassword";

    public static final String existingUsername = validUsername;
    public static final String nonExistingUsername = "IDontExist";

    @Before
    public void setUp() {
        this.userService = mock(UserService.class);
        this.validUser = new User(validUsername, validEmail, validPassword);
        when(this.userService.getUserByUsername(existingUsername)).thenReturn(Optional.of(this.validUser));
        when(this.userService.getUserByUsername(nonExistingUsername)).thenReturn(Optional.empty());
        this.securityUsersDetailsService = new SecurityUserDetailsService(this.userService);
    }

    @Test
    public void testLoadUserByUsername() {
        org.springframework.security.core.userdetails.User expected = new org.springframework.security.core.userdetails.User(
                this.validUser.getUsername(), this.validUser.getPassword(), !this.validUser.getDisabled(),
                !this.validUser.isExpired(), !this.validUser.areCredentialsExpired(), !this.validUser.getLocked(),
                new ArrayList<>());
        UserDetails user = this.securityUsersDetailsService.loadUserByUsername(existingUsername);
        assertEquals(expected, user);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsernameInvalidUsername() {
        this.securityUsersDetailsService.loadUserByUsername(nonExistingUsername);
    }

}
