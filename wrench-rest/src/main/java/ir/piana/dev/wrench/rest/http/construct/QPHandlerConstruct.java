package ir.piana.dev.wrench.rest.http.construct;

import ir.piana.dev.wrench.rest.authorize.QPRoleProvidable;
import ir.piana.dev.wrench.rest.http.core.QPHttpInjectableConfig;
import ir.piana.dev.wrench.rest.http.core.QPHttpRepositoryManager;

import java.util.List;

/**
 * @author Mohammad Rahmati, 2/16/2019
 */
public class QPHandlerConstruct {
    private String handlerName;
    private String url;
    private String method;
    private String roles;
    private Long rolesId;
    private List<QPRoleProvidable> roleProvidables;
    private QPHttpRepositoryManager repoManager;
    private QPHttpInjectableConfig handlerConfig;

    public QPHandlerConstruct() {
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public QPHttpInjectableConfig getHandlerConfig() {
        return handlerConfig;
    }

    public void setHandlerConfig(QPHttpInjectableConfig handlerConfig) {
        this.handlerConfig = handlerConfig;
    }

    public QPHttpRepositoryManager getRepoManager() {
        return repoManager;
    }

    public void setRepoManager(QPHttpRepositoryManager repoManager) {
        this.repoManager = repoManager;
    }

    public List<QPRoleProvidable> getRoleProvidables() {
        return roleProvidables;
    }

    public void setRoleProvidables(List<QPRoleProvidable> roleProvidables) {
        this.roleProvidables = roleProvidables;
    }

    public Long getRolesId() {
        return rolesId;
    }

    public void setRolesId(Long rolesId) {
        this.rolesId = rolesId;
    }
}
