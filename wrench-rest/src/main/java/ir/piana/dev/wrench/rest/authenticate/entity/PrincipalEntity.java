package ir.piana.dev.wrench.rest.authenticate.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author Mohammad Rahmati, 3/4/2019
 */
@Entity
@Table(name = "principal")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="type",
//        discriminatorType = DiscriminatorType.INTEGER)
public class PrincipalEntity {
    private Long id;
    private String alias = "user";
    private Collection<RoleEntity> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrincipalEntity that = (PrincipalEntity) o;
        if (id != that.id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        Long result = id;
        return result.intValue();
    }

    @ManyToMany
    @JoinTable(
            name = "principal_role",
            joinColumns = @JoinColumn(name = "principal_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }
}
