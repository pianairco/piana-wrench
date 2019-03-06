package ir.piana.dev.wrench.rest.authenticate;

import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;
import ir.piana.dev.wrench.rest.authenticate.repo.PrincipalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

/**
 * @author Mohammad Rahmati, 3/5/2019
 */
@Service
public class AuthenticateService {
    @Autowired
    private PrincipalRepository principalRepository;

    public PrincipalEntity createNew() {
        PrincipalEntity principalEntity = new PrincipalEntity();
        principalEntity.setAlias("football");
        principalRepository.save(principalEntity);
        return principalEntity;
    }
}
