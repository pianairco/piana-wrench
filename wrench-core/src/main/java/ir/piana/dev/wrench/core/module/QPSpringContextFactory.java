package ir.piana.dev.wrench.core.module;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mohammad Rahmati, 2/21/2019
 */
class QPSpringContextFactory {
    private Map<String, QPSpringContext>
            springContextMap;

    private static QPSpringContextFactory contextFactory;

    private QPSpringContextFactory(
            Map<String, QPSpringContext> springContextMap) {
        this.springContextMap = springContextMap;
    }

    public static QPSpringContextFactory getContextFactory() {
        if(contextFactory != null)
            return contextFactory;
        synchronized (QPSpringContextFactory.class) {
            Map<String, QPSpringContext> springContextMap =
                    new LinkedHashMap<>();
            springContextMap.put("default",
                    new QPSpringContextImpl(
                            new AnnotationConfigApplicationContext()));
            contextFactory = new QPSpringContextFactory(
                    springContextMap);
        }
        return contextFactory;
    }

    public QPSpringContext getSpringContext(){
        return this.getSpringContext("default");
    }

    public QPSpringContext getSpringContext(String contextName){
        if (springContextMap.containsKey(contextName))
            return springContextMap.get(contextName);
        QPSpringContextImpl springContext = new QPSpringContextImpl(
                new AnnotationConfigApplicationContext());
        springContextMap.put(contextName, springContext);
        return springContext;
    }

    static void refreshAll() {
        for (String contextName :
                getContextFactory().springContextMap.keySet()) {
            getContextFactory().refresh(contextName);
        }
    }

    static void refreshAll(Map<String, List<String>> contextResourceMap) {
        for (String contextName :
                getContextFactory().springContextMap.keySet()) {
            List<String> resourceNames = contextResourceMap.get(contextName);
            if(resourceNames != null && !resourceNames.isEmpty())
                getContextFactory().refresh(contextName, resourceNames);
            else
                getContextFactory().refresh(contextName);
        }
    }

    private void refresh(String contextName) {
        QPSpringContextImpl springContext =
                (QPSpringContextImpl)this.getSpringContext(contextName);
//        GenericApplicationContext createdContext =
//                new GenericApplicationContext(springContext.applicationContext);
        Resource resource = springContext.applicationContext
                .getResource("file:./applicationContext.xml");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(
                springContext.applicationContext);
        int i = reader.loadBeanDefinitions(resource);
        springContext.refresh();
    }

    private void refresh(String contextName, List<String> resourceNames) {
        QPSpringContextImpl springContext =
                (QPSpringContextImpl)this.getSpringContext(contextName);
        for(String resourceName : resourceNames) {
            Resource resource = springContext.applicationContext
                    .getResource("file:./" + resourceName + ".xml");
            XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(
                    springContext.applicationContext);
            int i = reader.loadBeanDefinitions(resource);
        }
        springContext.refresh();
    }

    private static class QPSpringContextImpl
            implements QPSpringContext {
        private AnnotationConfigApplicationContext applicationContext;

        public QPSpringContextImpl(
                AnnotationConfigApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
//            this.applicationContext.refresh();
        }

        private void refresh() {
            this.applicationContext.refresh();
        }

        @Override
        public <T> QPSpringContext register(Class<T> beanClass) {
            this.applicationContext.register(beanClass);
//            this.applicationContext.refresh();
            return this;
        }

        @Override
        public <T> QPSpringContext registerBean(
                String beanName, Class<T> beanClass) {
            applicationContext.registerBean(beanName, beanClass,
                    beanDefinition -> {});
            return this;
        }

        @Override
        public <T> T getBean(String beanName, Class<T> beanClass) {
            return applicationContext.getBean(beanName, beanClass);
        }

        @Override
        public <T> T getBean(Class<T> beanClass) {
            return applicationContext.getBean(beanClass);
        }
    }
}
