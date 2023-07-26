package org.substancemc.item.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.substancemc.core.command.SubstanceCommandHolder;
import org.substancemc.item.SubstanceItem;
import org.substancemc.item.SubstanceItemAddon;

public class ItemCommand implements SubstanceCommandHolder {
    public CommandAPICommand getCommand() {
        return new CommandAPICommand("item")
                .withSubcommand(getItemGetCommand());
    }

    public CommandAPICommand getItemGetCommand()
    {
        return new CommandAPICommand("get")
                .withArguments(new StringArgument("itemId").replaceSuggestions(ArgumentSuggestions.strings(SubstanceItemAddon.get().getItemManager().getRegisteredItemIds())))
                .executesPlayer((player, args) -> {
                    SubstanceItem result = SubstanceItemAddon.get().getItemManager().getRegisteredItems().stream().filter(item -> item.getId().equals(args.get(0))).findAny().orElse(null);
                    if(result == null){
                        player.sendMessage(Component.text("No item with id " + args.get(0) + " exists.").color(NamedTextColor.RED));
                        return;
                    }
                    player.getInventory().addItem(result.createDefaultItem());
                });
    }
}
