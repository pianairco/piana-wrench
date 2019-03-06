package ir.piana.dev.wrench.rest.http.core;

import ir.piana.dev.wrench.core.QPException;

/**
 * @author Mohammad Rahmati, 2/18/2019
 */
public interface QPHttpRepositoryManager {
    QPHttpHandler resolve(String handlerName) throws QPException;
}
