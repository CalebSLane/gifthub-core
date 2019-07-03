package ca.csl.gifthub.core.model.account;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ca.csl.gifthub.core.persistence.PersistentObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "privilege")
public class Privilege extends PersistentObject<Integer> {

    private static final long serialVersionUID = -3333911203701320115L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    // for hibernate which requires a constructor
    Privilege() {}

    public Privilege(String name) {
        this.setName(name);
    }
}
