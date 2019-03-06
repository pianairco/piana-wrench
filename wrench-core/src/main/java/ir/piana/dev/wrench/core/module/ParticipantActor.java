package ir.piana.dev.wrench.core.module;

import java.io.Serializable;

/**
 * @author Mohammad Rahmati, 3/5/2019
 */
public interface ParticipantActor {
    int prepare(long id, QPSpringContext springContext, Serializable context);
    void commit(long id, QPSpringContext springContext, Serializable context);
    void abort(long id, QPSpringContext springContext, Serializable context);
}
