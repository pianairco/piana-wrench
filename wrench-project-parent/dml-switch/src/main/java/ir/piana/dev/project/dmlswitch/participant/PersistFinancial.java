package ir.piana.dev.project.dmlswitch.participant;

import ir.piana.dev.wrench.core.module.BaseParticipant;
import ir.piana.dev.wrench.core.module.QPSpringContext;
import ir.piana.dev.project.dmlswitch.data.entity.FinancialShetabV87Entity;
import ir.piana.dev.project.dmlswitch.data.repo.FinancialShetab87Repo;
import org.jpos.iso.ISOMsg;

import java.io.Serializable;

/**
 * @author Mohammad Rahmati, 3/5/2019
 */
public class PersistFinancial extends BaseParticipant {
    @Override
    public int prepare(
            long id,
            QPSpringContext springContext,
            Serializable context) {
        FinancialShetab87Repo financialShetab87Repo = springContext
                .getBean(FinancialShetab87Repo.class);
        FinancialShetabV87Entity financialShetabV87Entity =
                new FinancialShetabV87Entity((ISOMsg) context);
        financialShetabV87Entity.setId(id);
        financialShetab87Repo.save(
                financialShetabV87Entity);
        return PREPARED;
    }

    @Override
    public void commit(long id, QPSpringContext springContext, Serializable context) {

    }

    @Override
    public void abort(long id, QPSpringContext springContext, Serializable context) {

    }
}
