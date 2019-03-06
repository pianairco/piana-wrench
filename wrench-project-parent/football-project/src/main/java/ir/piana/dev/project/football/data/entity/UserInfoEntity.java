package ir.piana.dev.project.football.data.entity;

import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Mohammad Rahmati, 2/3/2019
 */
@Entity
@Table(name = "user_info")
public class UserInfoEntity implements Serializable {
    private Long id;
    private PrincipalEntity principalEntity;
    private String firstName;
    private String lastName;

    public UserInfoEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToOne
    @JoinColumn(name = "principal_id")
    public PrincipalEntity getPrincipalEntity() {
        return principalEntity;
    }

    public void setPrincipalEntity(PrincipalEntity principalEntity) {
        this.principalEntity = principalEntity;
    }
}
