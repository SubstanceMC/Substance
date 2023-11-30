package org.substancemc.core.addon.snippet;


public interface AddonSnippet<O, C> {
    default C getCase(){
        return null;
    }
    Class<? extends AddonSnippetScope>[] getScopes();
    default boolean isCaseMet(C otherCase)
    {
        return getCase().equals(otherCase);
    }
    Class<?> getCaseClass();
    void call(O context);
    Class<?> getContextClass();
}
