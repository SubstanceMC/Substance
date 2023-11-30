package org.substancemc.item;

import org.bukkit.event.Event;
import org.substancemc.core.addon.snippet.AddonSnippet;
import org.substancemc.core.addon.snippet.AddonSnippetScope;
import org.substancemc.item.event.SubstanceItemEvent;

public interface SubstanceItemSnippet<E extends Event> extends AddonSnippet<SubstanceItemEvent<E>, String> {
    @Override
    default Class<? extends AddonSnippetScope>[] getScopes()
    {
        return new Class[]{
              SubstanceItemEvent.class
        };
    }

    @Override
    default Class<String> getCaseClass()
    {
        return String.class;
    }

    @Override
    default Class<?> getContextClass()
    {
        return SubstanceItemEvent.class;
    }
}
