package org.substancemc.item.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.addon.snippet.AddonSnippetScope;
import org.substancemc.item.SubstanceItem;
import org.substancemc.item.SubstanceItemType;

public class SubstanceItemEvent<T extends Event> extends Event implements Cancellable, AddonSnippetScope {

    private static final HandlerList handlers = new HandlerList();

    private final T context;
    private final SubstanceItem item;
    public SubstanceItemEvent(SubstanceItem item, T context)
    {
        this.context = context;
        this.item = item;
        SubstancePlugin.get().getAddonManager().callMatchingSnippets(item, item.getType().getId(), getClass());
    }

    public T getContext()
    {
        return context;
    }

    public SubstanceItem getItem()
    {
        return item;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
