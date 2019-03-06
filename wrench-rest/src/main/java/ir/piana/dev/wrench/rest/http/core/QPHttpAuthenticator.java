package ir.piana.dev.wrench.rest.http.core;

/**
 * @author Mohammad Rahmati, 2/18/2019
 */
public interface QPHttpAuthenticator {
    QPHttpAuthenticated authenticate(
            QPHttpRequest request);
}
