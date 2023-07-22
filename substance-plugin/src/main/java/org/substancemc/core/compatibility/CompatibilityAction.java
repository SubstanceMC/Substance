package org.substancemc.core.compatibility;

public interface CompatibilityAction<T> {
    default T execute(boolean present){
        if(present) return executePresent(); else return executeAbsent();
    }
    T executeAbsent();
    T executePresent();
}
