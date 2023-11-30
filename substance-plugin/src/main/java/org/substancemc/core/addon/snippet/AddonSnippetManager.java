package org.substancemc.core.addon.snippet;

import java.util.Arrays;
import java.util.List;

public interface AddonSnippetManager {
    List<AddonSnippet<?, ?>> getAllSnippets();

    default <E, C> void callMatchingSnippets(E object, C operationCase, Class<? extends AddonSnippetScope> scope)
    {
        getAllSnippets().forEach(snippet -> {
            if(
                        (object.getClass().equals(snippet.getContextClass()) || snippet.getContextClass().equals(object.getClass().getSuperclass()))
                    &&  (operationCase.getClass().equals(snippet.getCaseClass()) || snippet.getCaseClass().equals(operationCase.getClass().getSuperclass()))
            )
            {
                AddonSnippet<E, C> castedSnippet = (AddonSnippet<E, C>) snippet;
                if(castedSnippet.isCaseMet(operationCase) && Arrays.asList(castedSnippet.getScopes()).contains(scope))
                {
                    castedSnippet.call(object);
                }
            }
        });
    }
}
