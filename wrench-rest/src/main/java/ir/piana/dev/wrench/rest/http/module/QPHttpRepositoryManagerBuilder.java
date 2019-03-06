package ir.piana.dev.wrench.rest.http.module;

import ir.piana.dev.wrench.core.QPException;
import ir.piana.dev.wrench.rest.http.core.QPHttpRepositoryManager;
import ir.piana.dev.wrench.rest.http.construct.QPRepositoryConstruct;

/**
 * @author Mohammad Rahmati, 2/18/2019
 */
public class QPHttpRepositoryManagerBuilder {
    private QPHttpRepositoryManagerBuilder(){}

    public static QPHttpRepositoryManager build(
            QPRepositoryConstruct repositoryConstruct)
            throws QPException {
        if (repositoryConstruct.getScope()
                .equalsIgnoreCase("singleton")) {
            return new QPHttpSingletonRepositoryManager(repositoryConstruct);
        } else if (repositoryConstruct.getScope()
                .equalsIgnoreCase("prototype")) {
            return new QPHttpPrototypeRepositoryManager(repositoryConstruct);
        }
        throw new QPException("repository scope not support");
    }
}
