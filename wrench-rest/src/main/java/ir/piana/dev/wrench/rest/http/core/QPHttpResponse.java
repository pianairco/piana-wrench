package ir.piana.dev.wrench.rest.http.core;

/**
 * @author Mohammad Rahmati, 1/19/2019
 */
public interface QPHttpResponse<T> {
    QPHttpStatus getHttpStatus();
    void setHttpStatus(QPHttpStatus httpStatus);
    T getEntity();
    void setEntity(T entity);
    QPHttpMediaType getMediaType();
    void setMediaType(QPHttpMediaType httpMediaType);
    String getCharset();
    void setCharset(String charset);
    void apply();
}
