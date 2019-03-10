package ir.piana.dev.wrench.rest.http.core;

import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;

/**
 * @author Mohammad Rahmati, 2/16/2019
 */
public interface QPHttpHandlerWithPrincipal extends QPHttpHandler {
    QPHttpResponse handle(
            QPHttpRequest request,
            QPHttpInjectableConfig config,
            PrincipalEntity principal)
            throws QPHttpException;
}
