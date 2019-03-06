package ir.piana.dev.wrench.module.basicauth;

import ir.piana.dev.wrench.core.module.QPBaseModule;
import ir.piana.dev.wrench.module.basicauth.entity.BasicUserEntity;
import ir.piana.dev.wrench.module.basicauth.repo.UserRepository;
import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;
import ir.piana.dev.wrench.rest.authorize.QPRoleProvider;
import ir.piana.dev.wrench.rest.authorize.QPRoleProvidable;
import ir.piana.dev.wrench.rest.http.core.QPHttpAuthenticated;
import ir.piana.dev.wrench.rest.http.core.QPHttpAuthenticator;
import ir.piana.dev.wrench.rest.http.core.QPHttpRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mohammad Rahmati, 2/23/2019
 */
public class BasicAuthenticationModule
        extends QPBaseModule
        implements QPHttpAuthenticator {
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

    @Override
    public QPHttpAuthenticated authenticate(QPHttpRequest request) {
        UserRepository userRepository = getSpringContext()
                .getBean(UserRepository.class);
        String authorization = request.getHeader("Authorization");
        if(authorization == null)
            return new BasicHttpAuthenticated(null);
        try {
            String userPass = new String(
                    Base64.getDecoder().decode(authorization
                            .substring("Basic ".length())), "UTF-8");
            String[] split = userPass.split(":");
            BasicUserEntity userEntity = userRepository
                    .findByUsernameAndPassword(split[0], split[1]);
            if(userEntity != null)
                return new BasicHttpAuthenticated(userEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    static class BasicHttpAuthenticated
            implements QPHttpAuthenticated {
        private PrincipalEntity principalEntity;
        private List<QPRoleProvidable> roleProvidables;

        public BasicHttpAuthenticated(BasicUserEntity userEntity) {
            if(userEntity != null) {
                this.principalEntity = userEntity.getPrincipalEntity();
                this.roleProvidables =
                        userEntity.getPrincipalEntity().getRoles().stream()
                                .map(role -> new QPRoleProvider(role.getName()))
                                .collect(Collectors.toList());
            } else {
                this.roleProvidables = new ArrayList<>();
            }
        }

        public PrincipalEntity getPrincipal() {
            return principalEntity;
        }

//        @Override
//        public BasicUserEntity getAuthenticated() {
//            return principalEntity;
//        }

        @Override
        public List<QPRoleProvidable> getAuthenticatedRoles() {
            return this.roleProvidables;
        }
    }
}
