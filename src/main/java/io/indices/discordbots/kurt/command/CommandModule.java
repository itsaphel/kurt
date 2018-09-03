package io.indices.discordbots.kurt.command;

import com.google.inject.Injector;
import io.indices.discordbots.kurt.command.admin.SayCommand;
import io.indices.discordbots.kurt.command.info.HelpCommand;
import io.indices.discordbots.kurt.command.info.VersionCommand;
import io.indices.discordbots.kurt.command.util.SetTimeRegionsCommand;
import io.indices.discordbots.kurt.command.util.UrbanDictionaryCommand;
import io.indices.discordbots.kurt.command.util.WolframAlphaCommand;
import io.indices.discordbots.kurt.module.IModule;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommandModule implements IModule {

    @Inject
    private Injector injector;

    private Set<Command> commands = new HashSet<>();

    @Override
    public void enable() {
        registerCommands();
    }

    @Override
    public void disable() {

    }

    private void registerCommands() {
        // info
        Command helpCommand = new HelpCommand("help");
        Command versionCommand = new VersionCommand("kurtversion", "kurtv");
        injector.injectMembers(helpCommand);
        injector.injectMembers(versionCommand);

        commands.add(helpCommand);
        commands.add(versionCommand);

        // util
        Command setTimeRegionsCommand = new SetTimeRegionsCommand("trset", Permission.GUILD_ADMIN, "settimeregions");
        Command urbanDictionaryCommand = new UrbanDictionaryCommand("urban");
        Command wolframAlphaCommand = new WolframAlphaCommand("wolframalpha", "wolf", "calc", "tellme");
        injector.injectMembers(setTimeRegionsCommand);
        injector.injectMembers(urbanDictionaryCommand);
        injector.injectMembers(wolframAlphaCommand);

        commands.add(setTimeRegionsCommand);
        commands.add(urbanDictionaryCommand);
        commands.add(wolframAlphaCommand);

        // admin
        Command sayCommand = new SayCommand("say");
        injector.injectMembers(sayCommand);

        commands.add(sayCommand);
    }

    public Optional<Command> getCommand(String label) {
        System.out.println(String.join(Arrays.toString(commands.toArray())));
        return commands.stream()
          .filter(cmd -> cmd.name.equalsIgnoreCase(label) || cmd.aliases.contains(label))
          .findAny();
    }
}
