package ir.piana.dev.wrench.rest.http.core;

import java.util.Set;

/**
 * @author Mohammad Rahmati, 1/19/2019
 */
public interface QPHttpRequest {
    String getRequestURI();
    QPHttpMethodType getMethodType() throws Exception;
    <T> T getBodyAs(Class classType) throws Exception;
    <T> T getBodyAs(Class classType, boolean isClone) throws Exception;
    String getHeader(String name);
    String[] getParam(String name);
    String getFirstParam(String name);
    Set<String> getNameOfParams();
    String getAsteriskPath();
    void setAsteriskPath(String asteriskPath);
}
