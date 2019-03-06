package ir.piana.dev.wrench.module.httpserver.grizzly;

import com.google.gson.Gson;
import ir.piana.dev.wrench.rest.http.staticresource.QPStaticResource;
import ir.piana.dev.wrench.rest.http.core.QPHttpMediaType;
import ir.piana.dev.wrench.rest.http.core.QPHttpResponse;
import ir.piana.dev.wrench.rest.http.core.QPHttpStatus;
import org.glassfish.grizzly.http.server.Response;

import java.io.IOException;

/**
 * @author Mohammad Rahmati, 2/16/2019
 */
class QPGrizzlyHttpResponse<T> implements QPHttpResponse<T> {
    private Response response;

    private QPHttpStatus httpStatus;
    private T entity;
    private QPHttpMediaType mediaType;
    private String charset;
    private static Gson gson = new Gson();

    QPGrizzlyHttpResponse(Response response) {
        this.response = response;
    }

//    QPGrizzlyHttpResponse(
//            QPHttpStatus httpStatus,
//            T entity,
//            QPHttpMediaType mediaType,
//            String charset) {
//        this.httpStatus = httpStatus;
//        this.entity = entity;
//        this.mediaType = mediaType;
//        this.charset = charset;
//    }

    @Override
    public QPHttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public void setHttpStatus(QPHttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public T getEntity() {
        return entity;
    }

    @Override
    public void setEntity(T entity) {
        this.entity = entity;
    }

    @Override
    public QPHttpMediaType getMediaType() {
        return mediaType;
    }

    @Override
    public void setMediaType(QPHttpMediaType mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String getCharset() {
        return charset;
    }

    @Override
    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
    public void apply() {
        if(httpStatus == null)
            httpStatus = QPHttpStatus.OK_200;
        response.setStatus(httpStatus.getStatusCode());

        String entityString = null;
        if(entity == null) {
            entityString = "";
        } else if(entity instanceof QPStaticResource) {
            QPStaticResource staticResource = (QPStaticResource) entity;
            charset = charset != null ? charset : "UTF-8";
            response.setHeader("content-type",
                    staticResource.getMediaType()
                            .concat(";charset=")
                            .concat(charset));
            try {
                response.getOutputBuffer().write(staticResource.getBytes());
                response.getOutputBuffer().flush();
                response.resume();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if(!(entity instanceof String)) {
                if (mediaType == QPHttpMediaType.APPLICATION_JSON) {
                    entityString = gson.toJson(entity);
                } else {
                    entityString = new String((byte[])entity);
                }
            } else {
                entityString = (String)entity;
            }
        }

        mediaType = mediaType != null ? mediaType : QPHttpMediaType.TEXT_PLAIN;
        charset = charset != null ? charset : "UTF-8";
        response.setHeader("content-type",
                mediaType.getCode()
                        .concat(";charset=")
                        .concat(charset));
        try {
            response.getWriter().write(
                    entityString);
            response.getWriter().flush();
            response.resume();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
