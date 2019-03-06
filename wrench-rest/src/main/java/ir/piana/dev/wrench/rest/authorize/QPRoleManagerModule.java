package ir.piana.dev.wrench.rest.authorize;

import ir.piana.dev.wrench.core.QPException;
import ir.piana.dev.wrench.core.module.QPBaseModule;
import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mohammad Rahmati, 2/19/2019
 */
public class QPRoleManagerModule
        extends QPBaseModule
        implements QPRoleManageable {
    private Map<String, Long> roleIdMap = new LinkedHashMap<>();
    private Map<Long, Long> principalRoleIdMap = new LinkedHashMap<>();
    private Map<String, Long> resourceRoleIdMap = new LinkedHashMap<>();
    private Long maxId = 0x1l;

    @Override
    public synchronized long registerRole(String roleName)
            throws QPException {
        if(roleIdMap.containsKey(roleName.toUpperCase()))
            throw new QPException("duplicate role name");
        if(maxId == Long.MIN_VALUE)
            throw new QPException("64 roles are full completed already!");
        roleIdMap.put(roleName.toUpperCase(), maxId);
        long id = maxId;
        maxId = maxId << 1;
        return id;
    }

    @Override
    public boolean isRegistered(PrincipalEntity principal) {
        return principalRoleIdMap.containsKey(principal.getId());
    }

    @Override
    public void registerPrincipalRoles(
            PrincipalEntity principal,
            List<QPRoleProvidable> roleProvidables) throws QPException {
        if(!principalRoleIdMap.containsKey(principal.getId()))
            principalRoleIdMap.put(principal.getId(),
                    createRolesId(roleProvidables));
    }

    @Override
    public void registerResourceRoles(
            String resource,
            List<QPRoleProvidable> roleProvidables)
            throws QPException {
        if(!resourceRoleIdMap.containsKey(resource))
            resourceRoleIdMap.put(resource,
                    createRolesId(roleProvidables));
    }

    @Override
    public long createRolesId(List<QPRoleProvidable> roles)
            throws QPException {
        long rolesId = 0;
        for (QPRoleProvidable role : roles) {
            Long aLong = roleIdMap.get(role.getName().toUpperCase());
            if (aLong == null)
                rolesId |= registerRole(role.getName());
            else
                rolesId |= aLong;
        }
        return rolesId;
    }

    @Override
    public long hasAnyRole(long requiredRolesId, long authenticatedRolesId) {
        return requiredRolesId & authenticatedRolesId;
    }

    @Override
    public long hasAnyRole(PrincipalEntity principal, long requiredRolesId) {
        Long authenticatedRolesId = principalRoleIdMap
                .get(principal.getId());
        return hasAnyRole(requiredRolesId, authenticatedRolesId);
    }

    @Override
    protected void configBeforeRegisterQPModule() throws Exception {

    }

    @Override
    protected void initBeforeRegisterQPModule() throws Exception {

    }

    @Override
    protected void configForSpringContext() throws Exception {

    }

    @Override
    protected void configAfterRegisterQPModule() throws Exception {

    }

    @Override
    protected void initAfterRegisterQPModule() throws Exception {

    }

    @Override
    protected void startQPModule() throws Exception {

    }

    @Override
    protected void stopQPModule() throws Exception {

    }

    @Override
    protected void destroyQPModule() throws Exception {

    }

    public static void main(String[] args) throws QPException {
        QPRoleManageable roleManageable = new QPRoleManagerModule();
        roleManageable.registerRole("admin");
        roleManageable.registerRole("user");
        List<QPRoleProvidable> required = Arrays.asList(
                new String[]{"user", "admin"}).stream()
                .map(s -> new QPRoleProvider(s))
                .collect(Collectors.toList());
        long requiredRoles = roleManageable.createRolesId(required);

        List<QPRoleProvidable> authenticated = Arrays.asList(
                new String[]{"user", "customer"}).stream()
                .map(s -> new QPRoleProvider(s))
                .collect(Collectors.toList());
        long authenticatedRoles = roleManageable.createRolesId(authenticated);
        long l = roleManageable.hasAnyRole(requiredRoles, authenticatedRoles);
        System.out.println(l);
    }
}
