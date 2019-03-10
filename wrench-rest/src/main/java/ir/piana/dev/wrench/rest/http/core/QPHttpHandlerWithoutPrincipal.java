package ir.piana.dev.wrench.rest.http.core;

/**
 * @author Mohammad Rahmati, 2/16/2019
 */
public interface QPHttpHandlerWithoutPrincipal extends QPHttpHandler {
    QPHttpResponse handle(
            QPHttpRequest request,
            QPHttpInjectableConfig config)
            throws QPHttpException;
}
