package ir.piana.dev.project.store.data.repo;

import ir.piana.dev.project.store.data.entity.UserInfoEntity;
import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mohammad Rahmati, 2/21/2019
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
    UserInfoEntity findByPrincipalEntity(PrincipalEntity principal);
}
