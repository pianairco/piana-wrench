package ir.piana.dev.project.dmlswitch.data.repo;

import ir.piana.dev.project.dmlswitch.data.entity.FinancialShetabV87Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mohammad Rahmati, 3/5/2019
 */
@Repository
public interface FinancialShetab87Repo
        extends JpaRepository<FinancialShetabV87Entity, Long> {
}
