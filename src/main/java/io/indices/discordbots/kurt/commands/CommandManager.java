package io.indices.discordbots.kurt.commands;

import io.indices.discordbots.kurt.commands.util.SetTimeRegionsCommand;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CommandManager {

    private Set<Command> commands = new HashSet<>();

    public void registerCommands() {
        commands.add(new SetTimeRegionsCommand("trset", "settimeregions"));
    }

    public Optional<Command> getCommand(String label) {
        return commands.stream()
            .filter(cmd -> cmd.name.equalsIgnoreCase(label))
            .findAny();
    }
}
