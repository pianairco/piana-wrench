package ir.piana.dev.wrench.module.httpserver.grizzly;

import ir.piana.dev.wrench.core.module.QPBaseModule;
import org.glassfish.grizzly.http.server.*;
import org.jpos.transaction.Context;

public class QPGrizzlyHttpServerModule extends QPBaseModule {
    private String host;
    private int port;
    private HttpServer server;


    @Override
    protected void configBeforeRegisterQPModule() throws Exception {
        host = cfg.get("host", "localhost");
        port = cfg.getInt("port", 9090);
    }

    @Override
    protected void initBeforeRegisterQPModule() throws Exception {
        server = new HttpServer();
        final ServerConfiguration config = server.getServerConfiguration();
        final NetworkListener listener =
                new NetworkListener("grizzly",
                        host,
                        port);
        server.addListener(listener);
        server.getServerConfiguration().addHttpHandler(
                new HttpHandler() {
                    public void service(Request request, Response response) throws Exception {
                        response.suspend();
                        Context context = new Context();
                        context.put("qp-httpmodule-request",
                                new QPGrizzlyHttpRequest(request));
                        context.put("qp-httpmodule-response",
                                new QPGrizzlyHttpResponseBuilder(response));
                        out(context);
                    }
                });
    }

//    @Override
//    protected void initBeforeRegisterQPModule() throws Exception {
//        server = new HttpServer();
//        final ServerConfiguration config = server.getServerConfiguration();
//        final NetworkListener listener =
//                new NetworkListener("grizzly",
//                        host,
//                        port);
//        server.addListener(listener);
//        server.getServerConfiguration().addHttpHandler(
//                new HttpHandler() {
//                    public void service(Request request, Response response) throws Exception {
//                        response.suspend();
//                        Context context = new Context();
//                        context.put("qp-httpmodule-request",
//                                new QPGrizzlyHttpRequest(request));
//                        context.put("qp-httpmodule-response",
//                                new QPGrizzlyHttpResponseBuilder(response));
//                        out(context);
//                    }
//                });
//    }

    @Override
    protected void configForSpringContext() throws Exception {

    }

    @Override
    protected void initAfterRegisterQPModule() throws Exception {

    }

    @Override
    protected void configAfterRegisterQPModule() throws Exception {

    }

    @Override
    protected void startQPModule() throws Exception {
        try {
            server.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    protected void stopQPModule() throws Exception {

    }

    @Override
    protected void destroyQPModule() throws Exception {

    }
}
