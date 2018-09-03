package io.indices.discordbots.kurt.commands;

import io.indices.discordbots.kurt.commands.admin.SayCommand;
import io.indices.discordbots.kurt.commands.info.HelpCommand;
import io.indices.discordbots.kurt.commands.info.VersionCommand;
import io.indices.discordbots.kurt.commands.util.SetTimeRegionsCommand;
import io.indices.discordbots.kurt.commands.util.UrbanDictionaryCommand;
import io.indices.discordbots.kurt.commands.util.WolframAlphaCommand;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CommandManager {

    private Set<Command> commands = new HashSet<>();

    public void registerCommands() {
        // info
        commands.add(new HelpCommand("help"));
        commands.add(new VersionCommand("kurtversion", "kurtv"));

        // util
        commands.add(new SetTimeRegionsCommand("trset", Permission.GUILD_ADMIN, "settimeregions"));
        commands.add(new UrbanDictionaryCommand("urban"));
        commands.add(new WolframAlphaCommand("wolframalpha", "wolf", "calc", "tellme"));

        // admin
        commands.add(new SayCommand("say"));
    }

    public Optional<Command> getCommand(String label) {
        return commands.stream()
                .filter(cmd -> cmd.name.equalsIgnoreCase(label) || cmd.aliases.contains(label))
                .findAny();
    }
}
