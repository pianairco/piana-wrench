package ir.piana.dev.project.dmlswitch.http.handler;

import ir.piana.dev.wrench.rest.http.core.QPHttpHandler;
import ir.piana.dev.wrench.rest.http.core.QPHttpHandlerWithoutPrincipal;
import ir.piana.dev.wrench.rest.http.core.QPHttpResponse;
import ir.piana.dev.wrench.rest.http.module.QPHttpRepository;
import org.jpos.iso.ISOMsg;
import org.jpos.space.SpaceFactory;

/**
 * @author Mohammad Rahmati, 3/6/2019
 */
public class IsoMessageHttpRepository extends QPHttpRepository {
    public QPHttpHandlerWithoutPrincipal createAndSendToChannelAdaptor = (request, config) -> {
        ISOMsg isoMsg = new ISOMsg("0200");
        isoMsg.set(2, "6104337804210969");
        isoMsg.set(3, "000000");
        String queue = config.getValue("out-queue");
        SpaceFactory.getSpace().out(queue, isoMsg);

        QPHttpResponse response = new QPHttpResponse();
        response.setEntity("successfully");
        return response;
    };
}
