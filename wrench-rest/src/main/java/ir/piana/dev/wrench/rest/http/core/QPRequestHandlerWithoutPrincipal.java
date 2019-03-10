package ir.piana.dev.wrench.rest.http.core;

import ir.piana.dev.wrench.core.QPException;
import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;
import ir.piana.dev.wrench.rest.http.construct.QPHandlerConstruct;
import ir.piana.dev.wrench.rest.http.core.*;

/**
 * @author Mohammad Rahmati, 3/10/2019
 */
public class QPRequestHandlerWithoutPrincipal
        implements QPRequestHandler {
    private QPHandlerConstruct handlerConstruct;

    public QPRequestHandlerWithoutPrincipal(
            QPHandlerConstruct handlerConstruct) {
        this.handlerConstruct = handlerConstruct;
    }

    @Override
    public QPHttpResponse handle(QPHttpRequest request)
            throws QPHttpException {
        return invokeHttpHandlerWithoutPrincipal(request);
    }

    protected QPHttpResponse invokeHttpHandlerWithoutPrincipal(
            QPHttpRequest request)
            throws QPHttpException {
        QPHttpResponse response = null;
        try {
            response = handlerConstruct.getRepoManager()
                    .resolveWithoutPrincipal(handlerConstruct.getHandlerName())
                    .handle(request, handlerConstruct.getHandlerConfig());
        } catch (QPException e) {
            throw new QPHttpException(QPHttpStatus.BAD_REQUEST_400);
        }
        return response;
    }
}
