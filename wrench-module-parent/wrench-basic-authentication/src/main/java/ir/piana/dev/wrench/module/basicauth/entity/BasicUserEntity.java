package ir.piana.dev.wrench.module.basicauth.entity;

import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;

import javax.persistence.*;

/**
 * @author Mohammad Rahmati, 2/3/2019
 */
@Entity
@Table(name = "basic_user")
public class BasicUserEntity {
    private Long id;
    private String username;
    private String password;
    private PrincipalEntity principalEntity;

    public BasicUserEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
