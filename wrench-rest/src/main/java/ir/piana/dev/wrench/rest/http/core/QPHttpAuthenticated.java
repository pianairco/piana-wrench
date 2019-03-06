package ir.piana.dev.wrench.rest.http.core;

import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;
import ir.piana.dev.wrench.rest.authorize.QPRoleProvidable;

import java.util.List;

/**
 * @author Mohammad Rahmati, 2/18/2019
 */
public interface QPHttpAuthenticated {
    PrincipalEntity getPrincipal();
    List<QPRoleProvidable> getAuthenticatedRoles();
}
