package ir.piana.dev.wrench.module.httpserver.grizzly;

import com.google.gson.Gson;
import ir.piana.dev.wrench.rest.http.core.*;
import ir.piana.dev.wrench.rest.http.staticresource.QPStaticResource;
import org.glassfish.grizzly.http.server.Response;

import java.io.IOException;

/**
 * @author Mohammad Rahmati, 2/16/2019
 */
class QPGrizzlyHttpResponseBuilder<T>
        implements QPHttpResponseBuilder<T> {
    private Response response;

    private static Gson gson = new Gson();
    private QPHttpResponse<T> qpHttpResponse;

    QPGrizzlyHttpResponseBuilder(Response response) {
        this.response = response;
    }

    @Override
    public void setQPHttpResponse(QPHttpResponse qpHttpResponse) {
        this.qpHttpResponse = qpHttpResponse;
    }

    @Override
    public void apply() {
        if(qpHttpResponse == null) {
            response.setHeader("content-type",
                    "TEXT/HTML; charset=");
            response.setStatus(QPHttpStatus.FOUND_302.getStatusCode());
            try {
                response.getWriter().write(
                        "<p>resource found but result not exist!</p>");
                response.getWriter().flush();
                response.resume();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return;
            }
        }
        if(qpHttpResponse.getHttpStatus() == null)
            qpHttpResponse.setHttpStatus(QPHttpStatus.OK_200);
        response.setStatus(qpHttpResponse.getHttpStatus().getStatusCode());

        String entityString = null;
        if(qpHttpResponse.getEntity() == null) {
            entityString = "";
        } else if(qpHttpResponse.getEntity() instanceof QPStaticResource) {
            QPStaticResource staticResource = (QPStaticResource) qpHttpResponse.getEntity();
            if(qpHttpResponse.getCharset() == null)
                qpHttpResponse.setCharset("UTF-8");
            response.setHeader("content-type",
                    staticResource.getMediaType()
                            .concat(";charset=")
                            .concat(qpHttpResponse.getCharset()));
            try {
                response.getOutputBuffer().write(staticResource.getBytes());
                response.getOutputBuffer().flush();
                response.resume();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if(!(qpHttpResponse.getEntity() instanceof String)) {
                if (qpHttpResponse.getMediaType() == QPHttpMediaType.APPLICATION_JSON) {
                    entityString = gson.toJson(qpHttpResponse.getEntity());
                } else {
                    entityString = new String((byte[])qpHttpResponse.getEntity());
                }
            } else {
                entityString = (String)qpHttpResponse.getEntity();
            }
        }

        if(qpHttpResponse.getMediaType() == null)
            qpHttpResponse.setMediaType(QPHttpMediaType.TEXT_PLAIN);
        if(qpHttpResponse.getCharset() == null)
            qpHttpResponse.setCharset("UTF-8");
        response.setHeader("content-type",
                qpHttpResponse.getMediaType().getCode()
                        .concat(";charset=")
                        .concat(qpHttpResponse.getCharset()));
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
