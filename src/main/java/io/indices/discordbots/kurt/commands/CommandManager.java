package io.indices.discordbots.kurt.commands;

import io.indices.discordbots.kurt.Bot;
import io.indices.discordbots.kurt.commands.info.HelpCommand;
import io.indices.discordbots.kurt.commands.info.VersionCommand;
import io.indices.discordbots.kurt.commands.util.SetTimeRegionsCommand;
import io.indices.discordbots.kurt.commands.util.WolframAlphaCommand;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CommandManager {

    private Set<Command> commands = new HashSet<>();
    private Bot main;

    public CommandManager(Bot main) {
        this.main = main;
    }

    public void registerCommands() {
        // info
        commands.add(new HelpCommand("help"));
        commands.add(new VersionCommand("kurtversion", "kurtv"));

        // util
        commands.add(new SetTimeRegionsCommand(main, "trset", "settimeregions"));
        commands.add(new WolframAlphaCommand(main, "wolframalpha", "wolf", "calc"));
    }

    public Optional<Command> getCommand(String label) {
        return commands.stream()
            .filter(cmd -> cmd.name.equalsIgnoreCase(label) || cmd.aliases.contains(label))
            .findAny();
    }
}
