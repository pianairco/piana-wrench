package ir.piana.dev.wrench.core.module;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.TransactionParticipant;

import java.io.Serializable;

/**
 * @author Mohammad Rahmati, 3/5/2019
 */
public abstract class BaseParticipant
        implements TransactionParticipant, ParticipantActor, Configurable {
    String springContext;
//    ParticipantActor participantActor;

    @Override
    public final int prepare(long id, Serializable context) {
        return this.prepare(id,
                QPSpringContextFactory.getContextFactory()
                        .getSpringContext(springContext),
                context);
    }

    @Override
    public final void commit(long id, Serializable context) {
        this.commit(id,
                QPSpringContextFactory.getContextFactory()
                        .getSpringContext(springContext),
                context);
    }

    @Override
    public final void abort(long id, Serializable context) {
        this.abort(id,
                QPSpringContextFactory.getContextFactory()
                        .getSpringContext(springContext),
                context);
    }

    @Override
    public void setConfiguration(Configuration cfg)
            throws ConfigurationException {
        springContext = cfg.get("spring-context", "default");
//        String participantActorName = cfg.get("participant-actor", "");
//        try {
//
//            participantActor = ParticipantActorRepository
//                    .getSingleton().getParticipantActor(
//                            participantActorName);
//        } catch (QPException e) {
//            e.printStackTrace();
//            throw new ConfigurationException("actor not existed!");
//        }
    }
}
