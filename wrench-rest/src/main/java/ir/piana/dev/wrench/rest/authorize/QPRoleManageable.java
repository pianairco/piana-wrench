package ir.piana.dev.wrench.rest.authorize;

import ir.piana.dev.wrench.core.QPException;
import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;

import java.util.List;

/**
 * @author Mohammad Rahmati, 2/19/2019
 */
public interface QPRoleManageable {
    long registerRole(String roleName) throws QPException;
    boolean isRegistered(PrincipalEntity principal);
    void registerPrincipalRoles(
            PrincipalEntity principal,
            List<QPRoleProvidable> roleProvidables) throws QPException;
    void registerResourceRoles(
            String resource,
            List<QPRoleProvidable> roleProvidables) throws QPException;
//    void injectRequiredRoles(List<String> requiredRoleNames);
    long createRolesId(List<QPRoleProvidable> roleNames) throws QPException;
    long hasAnyRole(long requiredRolesId, long authenticatedRolesId);
    long hasAnyRole(PrincipalEntity principal, long requiredRolesId);
}
