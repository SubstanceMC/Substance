package org.substancemc.item.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.substancemc.core.command.SubstanceCommandHolder;
import org.substancemc.item.SubstanceItemType;
import org.substancemc.item.SubstanceItemAddon;

import java.util.List;
import java.util.Objects;

public class ItemCommand implements SubstanceCommandHolder {
    public CommandAPICommand getCommand() {
        return new CommandAPICommand("item")
                .withSubcommand(getItemGetCommand());
    }

    //TODO: Tab Completion for externally registered Items
    public CommandAPICommand getItemGetCommand()
    {
        return new CommandAPICommand("get")
                .withArguments(new StringArgument("itemId").replaceSuggestions(ArgumentSuggestions.strings(SubstanceItemAddon.get().getItemManager().getRegisteredItemTypeIds())))
                .executesPlayer((player, args) -> {
                    SubstanceItemType result = SubstanceItemAddon.get().getItemManager().getRegisteredItemTypeById(Objects.requireNonNull(args.get(0)).toString());
                    if(result == null){
                        player.sendMessage(Component.text("No item with id " + args.get(0) + " exists.").color(NamedTextColor.RED));
                        return;
                    }
                    player.getInventory().addItem(result.createDefaultItem().getPhysical());
                });
    }
}
