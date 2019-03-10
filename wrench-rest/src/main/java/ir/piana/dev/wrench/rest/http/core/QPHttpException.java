package ir.piana.dev.wrench.rest.http.core;

import ir.piana.dev.wrench.core.QPException;

/**
 * @author Mohammad Rahmati, 2/17/2019
 */
public class QPHttpException extends QPException {
    private QPHttpStatus status;
    private String description;

    public QPHttpException(QPHttpStatus status) {
        this(status, "");
    }

    public QPHttpException(
            QPHttpStatus status, String description) {
        this.status = status;
        this.description = description != null ? description : "";
    }

    public void applyResponse(QPHttpResponseBuilder response) {
        QPHttpResponse qpResponse = new QPHttpResponse();
        qpResponse.setHttpStatus(status);
        qpResponse.setEntity(description);
        qpResponse.setMediaType(QPHttpMediaType.TEXT_PLAIN);
        response.setQPHttpResponse(qpResponse);
        response.apply();
    }
}
