package ir.piana.dev.wrench.rest.http.repo;

import ir.piana.dev.wrench.rest.http.core.QPHttpException;
import ir.piana.dev.wrench.rest.http.core.QPHttpHandler;
import ir.piana.dev.wrench.rest.http.core.QPHttpStatus;
import ir.piana.dev.wrench.core.module.QPBaseModule;
import ir.piana.dev.wrench.rest.http.module.QPHttpRepository;
import ir.piana.dev.wrench.rest.http.module.QPStaticResourceResolverModule;
import ir.piana.dev.wrench.rest.http.staticresource.QPStaticResource;

/**
 * @author Mohammad Rahmati, 2/17/2019
 */
public class QPUtilRepository extends QPHttpRepository {
    public QPHttpHandler staticResourceHandler = (principal, config, request, response) -> {
        String moduleName = config.getValue("static-resolver-module-name");
        QPStaticResource staticResource = null;
        try {
            staticResource = QPBaseModule.getModule(
                    moduleName, QPStaticResourceResolverModule.class)
                    .resolve(request.getAsteriskPath());
        } catch (Exception e) {
            throw new QPHttpException(QPHttpStatus.NOT_FOUND_404);
        }
        response.setEntity(staticResource);
    };
}
