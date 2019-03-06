package ir.piana.dev.wrench.rest.http.core;

import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;

/**
 * @author Mohammad Rahmati, 2/16/2019
 */
public interface QPHttpHandler {
    void handle(PrincipalEntity principal,
                QPHttpInjectableConfig config,
                QPHttpRequest request,
                QPHttpResponse response) throws QPHttpException;
//    QPHttpResponse handle(QPHttpRequest request);
}
