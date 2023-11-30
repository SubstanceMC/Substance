package org.substancemc.core.command;

import dev.jorel.commandapi.CommandAPICommand;
import org.substancemc.core.util.structure.SubstanceManager;

import java.util.ArrayList;
import java.util.List;

public class SubstanceCommandManager implements SubstanceManager {

    private final List<CommandAPICommand> subCommands = new ArrayList<>();

    @Override
    public void load() {
        CommandAPICommand substanceCommand = new CommandAPICommand("substance")
                .withAliases("st")
                .withAliases("sbs")
                .withPermission("substance.command");
        if(!subCommands.isEmpty())
        {
            substanceCommand.withSubcommands(subCommands.toArray(new CommandAPICommand[0]));
            substanceCommand.register();
        }
    }

    public void addSubCommand(SubstanceCommandHolder commandHolder)
    {
        subCommands.add(commandHolder.getCommand());
    }

    @Override
    public void unload() {
        subCommands.clear();
    }
}
