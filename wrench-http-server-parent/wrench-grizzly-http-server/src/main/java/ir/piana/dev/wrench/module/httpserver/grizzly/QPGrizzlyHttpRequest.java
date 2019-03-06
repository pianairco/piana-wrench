package ir.piana.dev.wrench.module.httpserver.grizzly;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.piana.dev.wrench.core.QPException;
import ir.piana.dev.wrench.rest.http.core.QPHttpMediaType;
import ir.piana.dev.wrench.rest.http.core.QPHttpMethodType;
import ir.piana.dev.wrench.rest.http.core.QPHttpRequest;
import org.glassfish.grizzly.http.server.Request;

import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Mohammad Rahmati, 2/16/2019
 */
class QPGrizzlyHttpRequest implements QPHttpRequest {
    private Request request;

    QPHttpMethodType httpMethodType;
    String body;
    QPHttpMediaType contentType;
    Map<String, String[]> queryParams;
    Object bodyObject;
    String asteriskPath;
    protected static Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.serializeNulls().create();
    }

    QPGrizzlyHttpRequest(Request request)
            throws Exception {
        this(request, "");
    }

    QPGrizzlyHttpRequest(Request request, String asteriskPath)
            throws Exception {
        this.request = request;
        httpMethodType = QPHttpMethodType.fromCode(
                request.getMethod().getMethodString());
        this.asteriskPath = asteriskPath == null ? "" : asteriskPath;
        if(httpMethodType == QPHttpMethodType.POST ||
                httpMethodType == QPHttpMethodType.PUT) {
            contentType = QPHttpMediaType
                    .fromCode((String) request
                            .getHeader("content-type"));
        }
        queryParams = Collections.unmodifiableMap(
                request.getParameterMap());
    }

    private void readBody() {
        if(body == null && httpMethodType.canHasBody()) {
            body = "";
            Scanner scanner = new Scanner(request.getInputStream());
            while(scanner.hasNextLine()) {
                body = body
                        .concat(scanner.nextLine());
            }
        }
    }

    @Override
    public String getRequestURI() {
        if(request.getRequestURI().startsWith("/"))
            return request.getRequestURI().substring(1);
        else
            return request.getRequestURI();
    }

    @Override
    public QPHttpMethodType getMethodType()
            throws Exception {
        return QPHttpMethodType.fromCode(
                request.getMethod().getMethodString());
    }

    @Override
    public <T> T getBodyAs(Class classType) throws Exception {
        return getBodyAs(classType, false);
    }

    @Override
    public <T> T getBodyAs(Class classType, boolean isClone) throws Exception {
        if(!isClone && bodyObject != null)
            return (T)bodyObject;
        if(!httpMethodType.canHasBody())
            throw new QPException("not have body");
        T bodyObject;
        readBody();
        try {
            switch (contentType) {
                case APPLICATION_JSON:
                    bodyObject = (T) gson.fromJson(body, classType);
                    break;
                case APPLICATION_XML:
                case APPLICATION_FORM_URLENCODED:
                case TEXT_PLAIN:
                case TEXT_HTML:
                    throw new QPException("not supported yet!");
                default:
                    throw new QPException("not supported yet!");

            }
        } catch (Exception e) {
            throw new QPException("body of json is incorrect!");
        }
        if(!isClone)
            this.bodyObject = bodyObject;
        return bodyObject;
    }

    @Override
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    @Override
    public String[] getParam(String name) {
        return queryParams.get(name);
    }

    @Override
    public String getFirstParam(String name) {
        String[] strings = queryParams.get(name);
        return  strings != null && strings.length > 0 ?
                strings[0] : null;
    }

    @Override
    public Set<String> getNameOfParams() {
        return queryParams.keySet();
    }

    @Override
    public String getAsteriskPath() {
        return asteriskPath;
    }

    @Override
    public void setAsteriskPath(String asteriskPath) {
        this.asteriskPath = asteriskPath;
    }
}
