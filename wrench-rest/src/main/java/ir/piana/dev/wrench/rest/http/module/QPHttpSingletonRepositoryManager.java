package ir.piana.dev.wrench.rest.http.module;

import ir.piana.dev.wrench.core.QPException;
import ir.piana.dev.wrench.rest.http.core.QPHttpHandler;
import ir.piana.dev.wrench.rest.http.core.QPHttpRepositoryManager;
import ir.piana.dev.wrench.rest.http.construct.QPRepositoryConstruct;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mohammad Rahmati, 2/18/2019
 */
public class QPHttpSingletonRepositoryManager
        implements QPHttpRepositoryManager {
    private QPRepositoryConstruct repositoryConstruct;
    private Map<String, QPHttpHandler> httpHandlerMap = new LinkedHashMap<>();

    public QPHttpSingletonRepositoryManager(
            QPRepositoryConstruct repositoryConstruct) {
        this.repositoryConstruct = repositoryConstruct;
    }

    @Override
    public QPHttpHandler resolve(String handlerName)
            throws QPException {
        try {
            QPHttpHandler qpHttpHandler = httpHandlerMap
                    .get(handlerName);
            if(qpHttpHandler != null)
                return qpHttpHandler;
            else {
                Field field = repositoryConstruct.getaClass()
                        .getDeclaredField(handlerName);
                if (QPHttpHandler.class.isAssignableFrom(field.getType())) {
                    qpHttpHandler = (QPHttpHandler) field
                            .get(repositoryConstruct.getSingletonInstance());
                    httpHandlerMap.put(handlerName, qpHttpHandler);
                    return qpHttpHandler;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new QPException(handlerName
                .concat(" not exist in repository named ")
                .concat(repositoryConstruct.getName()));
    }
}
