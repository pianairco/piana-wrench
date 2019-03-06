package ir.piana.dev.wrench.rest.http.module;

import ir.piana.dev.wrench.core.QPException;
import ir.piana.dev.wrench.rest.http.core.QPHttpHandler;
import ir.piana.dev.wrench.rest.http.core.QPHttpRepositoryManager;
import ir.piana.dev.wrench.rest.http.construct.QPRepositoryConstruct;

import java.lang.reflect.Field;

/**
 * @author Mohammad Rahmati, 2/18/2019
 */
public class QPHttpPrototypeRepositoryManager
        implements QPHttpRepositoryManager {
    private QPRepositoryConstruct repositoryConstruct;

    public QPHttpPrototypeRepositoryManager(
            QPRepositoryConstruct repositoryConstruct) {
        this.repositoryConstruct = repositoryConstruct;
    }

    @Override
    public QPHttpHandler resolve(String handlerName) throws QPException {
        try {
            Field field = repositoryConstruct.getaClass()
                    .getDeclaredField(handlerName);
            if (QPHttpHandler.class.isAssignableFrom(field.getType())) {

                QPHttpRepository repository = (QPHttpRepository)repositoryConstruct
                        .getaClass().newInstance();
                repository.springBeanProvider =
                        repositoryConstruct.getSpringBeanProvider();

                QPHttpHandler handler = (QPHttpHandler) field
                        .get(repository);
                return handler;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        throw new QPException(handlerName
                .concat(" not exist in repository named ")
                .concat(repositoryConstruct.getName()));
    }
}
