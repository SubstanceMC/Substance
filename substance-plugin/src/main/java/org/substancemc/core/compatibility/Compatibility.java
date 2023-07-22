package org.substancemc.core.compatibility;

public interface Compatibility {
    boolean checkCompatible();
    default <T> T executeAction(CompatibilityAction<T> action) {
        return action.execute(checkCompatible());
    }
}
