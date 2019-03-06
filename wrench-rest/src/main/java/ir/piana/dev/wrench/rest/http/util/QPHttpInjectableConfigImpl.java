package ir.piana.dev.wrench.rest.http.util;

import ir.piana.dev.wrench.rest.http.core.QPHttpInjectableConfig;
import org.jdom2.Element;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mohammad Rahmati, 2/17/2019
 */
public class QPHttpInjectableConfigImpl
        implements QPHttpInjectableConfig {
    protected Map<String, String> configMap;

    private QPHttpInjectableConfigImpl(Map<String, String> configMap) {
        this.configMap = configMap;
    }

    public static QPHttpInjectableConfig build(
            List<Element> properties) {
        Map<String, String> configMap = new LinkedHashMap<>();
        properties.parallelStream()
                .forEach(propertyElement -> {
                    configMap.put(
                            propertyElement.getAttributeValue("name"),
                            propertyElement.getAttributeValue("value"));
                });
        return new QPHttpInjectableConfigImpl(configMap);
    }

    @Override
    public Set<String> getKeys() {
        return configMap.keySet();
    }

    @Override
    public String getValue(String key) {
        return configMap.get(key);
    }
}
