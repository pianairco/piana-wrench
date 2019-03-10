package ir.piana.dev.wrench.rest.http.core;

/**
 * @author Mohammad Rahmati, 3/10/2019
 */
public interface QPRequestHandler {
    QPHttpResponse handle(QPHttpRequest request) throws QPHttpException;
}
