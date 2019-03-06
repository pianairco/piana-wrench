package ir.piana.dev.wrench.rest.http.core;

/**
 * @author Mohammad Rahmati, 1/19/2019
 */
public enum QPHttpMediaType {
    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    TEXT_PLAIN("text/plain"),
    TEXT_HTML("text/html");

    private String code;

    QPHttpMediaType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static QPHttpMediaType fromCode(String mediaType)
            throws Exception {
        for(QPHttpMediaType type : QPHttpMediaType.values()) {
            if(type.code.equalsIgnoreCase(mediaType))
                return type;
        }
        throw new Exception("not supported media type!");
    }
}
