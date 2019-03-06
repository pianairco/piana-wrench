package ir.piana.dev.wrench.rest.authorize;

/**
 * @author Mohammad Rahmati, 3/2/2019
 */
public class QPRoleProvider implements QPRoleProvidable {
    private String name;
    private String sign;

    public QPRoleProvider(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSign() {
        return sign;
    }
}
