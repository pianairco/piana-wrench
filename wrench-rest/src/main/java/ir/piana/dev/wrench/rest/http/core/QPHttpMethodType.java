package ir.piana.dev.wrench.rest.http.core;

/**
 * @author Mohammad Rahmati, 1/19/2019
 */
public enum QPHttpMethodType {
    DELETE("delete", 0x8),
    GET("get", 0x1),
    HEAD("head", 0x10),
    OPTIONS("options", 0x20),
    POST("post", 0x2),
    PUT("put", 0x4),
    TRACE("trace", 0x40);

    private String name;
    private int code;

    QPHttpMethodType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public int getCode() {
        return code;
    }

    public boolean canHasBody() {
        int i = code | POST.code | PUT.code;
        return i > 0 ? true : false;
    }

    public static QPHttpMethodType fromCode(String methodType)
            throws Exception {
        for(QPHttpMethodType type : QPHttpMethodType.values()) {
            if(type.name.equalsIgnoreCase(methodType))
                return type;
        }
        throw new Exception("not supported method!");
    }
}
