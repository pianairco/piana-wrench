package ir.piana.dev.wrench.module.basicauth.handler;

import ir.piana.dev.wrench.module.basicauth.entity.BasicUserEntity;
import ir.piana.dev.wrench.module.basicauth.repo.UserRepository;
import ir.piana.dev.wrench.rest.authenticate.AuthenticateService;
import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;
import ir.piana.dev.wrench.rest.http.core.*;
import ir.piana.dev.wrench.rest.http.module.QPHttpRepository;

import java.util.Map;

/**
 * @author Mohammad Rahmati, 2/16/2019
 */
public class BasicUserHandler extends QPHttpRepository {
    public QPHttpHandlerWithoutPrincipal signUp = (request, config) -> {
//        response.setEntity("Hello World!");
        try {
            QPHttpResponse response = new QPHttpResponse();
            UserRepository userRepository = springBeanProvider
                    .getBean(UserRepository.class);
            Map<String, String> bodyMap = request.getBodyAs(Map.class);
            BasicUserEntity userEntity = userRepository
                    .findByUsernameAndPassword(
                            bodyMap.get("username"), bodyMap.get("password"));
            if(userEntity == null) {
                AuthenticateService authenticateService = springBeanProvider
                        .getBean(AuthenticateService.class);
                PrincipalEntity principalEntity = authenticateService.createNew();
                userEntity = new BasicUserEntity();
                userEntity.setUsername(bodyMap.get("username"));
                userEntity.setPassword(bodyMap.get("password"));
                userEntity.setPrincipalEntity(principalEntity);
                userRepository.save(userEntity);
                response.setEntity("OK!");
            } else {
                response.setHttpStatus(QPHttpStatus.ACCEPTED_202);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QPHttpException(QPHttpStatus.INTERNAL_SERVER_ERROR_500);
        }
    };
}
