package io.indices.discordbots.kurt.commands;

import java.util.Arrays;
import java.util.List;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public abstract class Command {

    public final String name;
    public final List<String> aliases;

    public Command(String name, String... aliases) {
        this.name = name;
        this.aliases = Arrays.asList(aliases);
    }

    public abstract void onInvoke(String[] commandArgs, Message message);

    public abstract void help(MessageChannel user);
}
