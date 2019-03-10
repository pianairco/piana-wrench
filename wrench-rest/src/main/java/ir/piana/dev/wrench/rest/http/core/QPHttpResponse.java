package ir.piana.dev.wrench.rest.http.core;

import ir.piana.dev.wrench.rest.http.staticresource.QPStaticResource;

import java.io.IOException;

/**
 * @author Mohammad Rahmati, 2/16/2019
 */
public class QPHttpResponse<T>  {
    private QPHttpStatus httpStatus;
    private T entity;
    private QPHttpMediaType mediaType;
    private String charset;

    public QPHttpResponse() {

    }

    public QPHttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(QPHttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public QPHttpMediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(QPHttpMediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
