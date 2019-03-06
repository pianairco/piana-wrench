package ir.piana.dev.wrench.rest.http.core;

import java.util.Set;

/**
 * @author Mohammad Rahmati, 2/16/2019
 */
public interface QPHttpInjectableConfig {
    Set<String> getKeys();
    String getValue(String key);
}
