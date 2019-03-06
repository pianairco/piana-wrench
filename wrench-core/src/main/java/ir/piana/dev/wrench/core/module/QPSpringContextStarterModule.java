package ir.piana.dev.wrench.core.module;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class QPSpringContextStarterModule
        extends QPBaseModule {
//        implements QPSpringContext {
//    private List<Class> configBeanList;
//    private List<String> configPackageList;
//    private AnnotationConfigApplicationContext springContext;

    Map<String, List<String>> contextResourceMap = new LinkedHashMap<>();

    @Override
    protected void configBeforeRegisterQPModule()
            throws Exception {
        List<Element> contextResourceElements = getPersist()
                .getChildren("spring-context-xml-resource");
        for (Element e : contextResourceElements) {
            String contextName = e.getAttributeValue("context-name");
            String resourceName = e.getAttributeValue("resource-name");
            if(!contextResourceMap.containsKey(contextName)) {
                contextResourceMap.put(contextName, new ArrayList<>());
            }
            contextResourceMap.get(contextName).add(resourceName);
        }
//        configBeanList = new ArrayList<>();
//        configBeanElements.parallelStream().forEach(element -> {
//            try {
//                configBeanList.add(Class.forName(element.getText()));
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        });
//
//        List<Element> configPackageElements = getPersist()
//                .getChildren("config-package");
//        configPackageList = new ArrayList<>();
//        configPackageElements.parallelStream().forEach(element -> {
//            configPackageList.add(element.getText());
//        });
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
        /*springContext = new AnnotationConfigApplicationContext();
        configBeanList.stream().forEach(bean -> {
            springContext.register(bean);
        });
        configPackageList.stream().forEach(packageName -> {
            springContext.scan(packageName);
        });

//        BeanDefinitionRegistry definitionRegistry = (BeanDefinitionRegistry)
//                springContext.getAutowireCapableBeanFactory();
//        definitionRegistry.removeBeanDefinition("");

        springContext.refresh();*/
    }

    @Override
    protected void startQPModule() throws Exception {
        QPSpringContextFactory.refreshAll(contextResourceMap);
//        QPSpringContextFactory.refreshAll();
//        pianaSpringContextFactory.getApplicationContext()
//                .register(QPSpringConfiguration.class);
//        pianaSpringContextFactory.refresh();
    }

    @Override
    protected void stopQPModule() throws Exception {

    }

    @Override
    protected void destroyQPModule() throws Exception {

    }
}
