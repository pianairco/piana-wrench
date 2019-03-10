package ir.piana.dev.wrench.rest.http.module;

import ir.piana.dev.wrench.core.QPException;
import ir.piana.dev.wrench.rest.http.construct.QPRepositoryConstruct;
import ir.piana.dev.wrench.rest.http.core.QPHttpHandlerWithPrincipal;
import ir.piana.dev.wrench.rest.http.core.QPHttpHandlerWithoutPrincipal;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mohammad Rahmati, 2/18/2019
 */
public class QPHttpRepositoryManager {
    private QPRepositoryConstruct repositoryConstruct;
    private Map<String, QPHttpHandlerWithPrincipal> httpHandlerWithPrincipalMap =
            new LinkedHashMap<>();
    private Map<String, QPHttpHandlerWithoutPrincipal> httpHandlerWithoutPrincipalMap =
            new LinkedHashMap<>();

    public QPHttpRepositoryManager(
            QPRepositoryConstruct repositoryConstruct) {
        this.repositoryConstruct = repositoryConstruct;
    }

    public boolean isWithPrincipal(
            String handlerName) {
        try {
            Field field = repositoryConstruct.getaClass()
                        .getDeclaredField(handlerName);
                if (QPHttpHandlerWithPrincipal.class.isAssignableFrom(field.getType()))
                    return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
        return false;
    }

    public QPHttpHandlerWithPrincipal resolveWithPrincipal(
            String handlerName)
            throws QPException {
        try {
            QPHttpHandlerWithPrincipal qpHttpHandler = httpHandlerWithPrincipalMap
                    .get(handlerName);
            if(qpHttpHandler != null)
                return qpHttpHandler;
            else {
                Field field = repositoryConstruct.getaClass()
                        .getDeclaredField(handlerName);
                if (QPHttpHandlerWithPrincipal.class.isAssignableFrom(field.getType())) {
                    qpHttpHandler = (QPHttpHandlerWithPrincipal) field
                            .get(repositoryConstruct.getSingletonInstance());
                    httpHandlerWithPrincipalMap.put(handlerName, qpHttpHandler);
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

    public boolean isWithoutPrincipal(
            String handlerName) {
        try {
            Field field = repositoryConstruct.getaClass()
                    .getDeclaredField(handlerName);
            if (QPHttpHandlerWithoutPrincipal.class.isAssignableFrom(field.getType()))
                return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
        return false;
    }

    public QPHttpHandlerWithoutPrincipal resolveWithoutPrincipal(
            String handlerName)
            throws QPException {
        try {
            QPHttpHandlerWithoutPrincipal qpHttpHandler = httpHandlerWithoutPrincipalMap
                    .get(handlerName);
            if(qpHttpHandler != null)
                return qpHttpHandler;
            else {
                Field field = repositoryConstruct.getaClass()
                        .getDeclaredField(handlerName);
                if (QPHttpHandlerWithoutPrincipal.class.isAssignableFrom(field.getType())) {
                    qpHttpHandler = (QPHttpHandlerWithoutPrincipal) field
                            .get(repositoryConstruct.getSingletonInstance());
                    httpHandlerWithoutPrincipalMap.put(handlerName, qpHttpHandler);
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
