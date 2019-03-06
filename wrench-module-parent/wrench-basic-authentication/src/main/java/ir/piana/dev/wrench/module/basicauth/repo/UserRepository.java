package ir.piana.dev.wrench.module.basicauth.repo;

import ir.piana.dev.wrench.module.basicauth.entity.BasicUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mohammad Rahmati, 2/21/2019
 */
@Repository
public interface UserRepository extends JpaRepository<BasicUserEntity, Long> {
    BasicUserEntity findById(long id);
    BasicUserEntity findByUsernameAndPassword(
            String username, String password);
}
