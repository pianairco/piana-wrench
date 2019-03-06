package ir.piana.dev.wrench.rest.authenticate.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author Mohammad Rahmati, 3/4/2019
 */
@Entity
@Table(name = "role")
public class RoleEntity {
    private int id;
    private String name;
    private Collection<PrincipalEntity> principals;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleEntity that = (RoleEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToMany(mappedBy = "roles")
    public Collection<PrincipalEntity> getPrincipals() {
        return principals;
    }

    public void setPrincipals(Collection<PrincipalEntity> principals) {
        this.principals = principals;
    }
}
