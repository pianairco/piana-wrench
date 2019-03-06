package ir.piana.dev.wrench.rest.authenticate.repo;

import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mohammad Rahmati, 3/4/2019
 */
public interface PrincipalRepository
        extends JpaRepository<PrincipalEntity, Long> {

}
