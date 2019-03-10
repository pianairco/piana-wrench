package ir.piana.dev.wrench.rest.http.repo;

import ir.piana.dev.wrench.rest.http.core.*;
import ir.piana.dev.wrench.core.module.QPBaseModule;
import ir.piana.dev.wrench.rest.http.module.QPHttpRepository;
import ir.piana.dev.wrench.rest.http.module.QPStaticResourceResolverModule;
import ir.piana.dev.wrench.rest.http.staticresource.QPStaticResource;

/**
 * @author Mohammad Rahmati, 2/17/2019
 */
public class QPUtilRepository extends QPHttpRepository {
    public QPHttpHandlerWithPrincipal staticResourceHandler = (request, config, principal) -> {
        String moduleName = config.getValue("static-resolver-module-name");
        QPStaticResource staticResource = null;
        QPHttpResponse response = new QPHttpResponse();
        try {
            staticResource = QPBaseModule.getModule(
                    moduleName, QPStaticResourceResolverModule.class)
                    .resolve(request.getAsteriskPath());
            response.setEntity(staticResource);
            return response;
        } catch (Exception e) {
            throw new QPHttpException(QPHttpStatus.NOT_FOUND_404);
        }
    };
}
