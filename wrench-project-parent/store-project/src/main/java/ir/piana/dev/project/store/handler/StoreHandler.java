package ir.piana.dev.project.store.handler;

import ir.piana.dev.wrench.rest.http.core.*;
import ir.piana.dev.wrench.rest.http.module.QPHttpRepository;

import java.util.Map;

/**
 * @author Mohammad Rahmati, 3/11/2019
 */
public class StoreHandler extends QPHttpRepository {
    public QPHttpHandlerWithoutPrincipal test = (request, config) -> {
        QPHttpResponse response = new QPHttpResponse();
        response.setEntity("ok");
        response.setHttpStatus(QPHttpStatus.OK_200);
        response.setMediaType(QPHttpMediaType.TEXT_PLAIN);
        return response;
    };

    public QPHttpHandlerWithoutPrincipal postTest = (request, config) -> {
        try {
            QPHttpResponse response = new QPHttpResponse();

            request.getBodyAs(Map.class);
            response.setEntity("ok");
            response.setHttpStatus(QPHttpStatus.OK_200);
            response.setMediaType(QPHttpMediaType.TEXT_PLAIN);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new QPHttpException(QPHttpStatus.BAD_REQUEST_400);
    };
}
