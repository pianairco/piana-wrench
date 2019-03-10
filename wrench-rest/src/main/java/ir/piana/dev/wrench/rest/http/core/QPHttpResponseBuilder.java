package ir.piana.dev.wrench.rest.http.core;

/**
 * @author Mohammad Rahmati, 1/19/2019
 */
public interface QPHttpResponseBuilder<T> {
    void setQPHttpResponse(QPHttpResponse qpHttpResponse);
    void apply();
}
