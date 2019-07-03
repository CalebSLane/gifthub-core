package ca.csl.gifthub.core.model.account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ca.csl.gifthub.core.model.account.PasswordValidator.HashStatus;
import ca.csl.gifthub.core.persistence.PersistentObject;
import ca.csl.gifthub.core.util.DateUtil;
import ca.csl.gifthub.core.util.spring.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User extends PersistentObject<Integer> {

    public static final String DEFAULT_USER_PASSWORD_HASH_ID = "bcrypt";

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_PASSWORD_EXPIRY_IN_YEARS = 2;
    private static final int DEFAULT_ACCOUNT_EXPIRY_IN_YEARS = 5;
    private static final String PASSWORD_MASK = "?????????????";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ValidUsername
    @Column(name = "username", unique = true)
    private String username;
    @NotNull
    @Email
    @Column(name = "email", unique = true)
    private String email;
    @ValidPassword(status = HashStatus.HASHED)
    @Column(name = "password")
    private String password;
    @NotNull
    @Column(name = "disabled")
    private Boolean disabled;
    @NotNull
    @Column(name = "locked")
    private Boolean locked;
    @NotNull
    @Column(name = "credentials_expiry_date")
    private Date credentialsExpiryDate;
    @NotNull
    @Column(name = "account_expiry_date")
    private Date accountExpiryDate;
    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    // for hibernate which requires a constructor
    User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.setDefaultUserData();
    }

    public User(String username, String password, String email, boolean fillAllDefaultUserData) {
        this(username, password, email);
        if (fillAllDefaultUserData) {
            this.setDefaultUserData();
        }
    }

    public void encryptPassword() {
        this.setPassword(BeanUtil.getBean(PasswordEncoder.class).encode(this.getPassword()));
    }

    public User withEncryptedPassword() {
        this.encryptPassword();
        return this;
    }

    public boolean areCredentialsExpired() {
        Date currentDate = new Date();
        return currentDate.after(this.credentialsExpiryDate);
    }

    public boolean isExpired() {
        Date currentDate = new Date();
        return currentDate.after(this.accountExpiryDate);
    }

    public void setDefaultAccountExpiry() {
        Calendar calendar = DateUtil.getCalendarToday();
        calendar.add(Calendar.YEAR, DEFAULT_ACCOUNT_EXPIRY_IN_YEARS);
        this.accountExpiryDate = calendar.getTime();
    }

    public void setDefaultPasswordExpiry() {
        Calendar calendar = DateUtil.getCalendarToday();
        calendar.add(Calendar.YEAR, DEFAULT_PASSWORD_EXPIRY_IN_YEARS);
        this.credentialsExpiryDate = calendar.getTime();
    }

    public void setDefaultUserData() {
        this.disabled = false;
        this.locked = false;
        this.roles = new ArrayList<>();
        this.setDefaultAccountExpiry();
        this.setDefaultPasswordExpiry();
    }

    public void maskPassword() {
        this.setPassword(PASSWORD_MASK);
    }

    public void editFields(String username, String password, String email) {
        this.setUsername(username);
        if (!PASSWORD_MASK.equals(password)) {
            this.setPassword(password);
        }
        this.setEmail(email);
    }

}
