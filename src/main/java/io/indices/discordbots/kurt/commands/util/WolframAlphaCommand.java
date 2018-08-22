package io.indices.discordbots.kurt.commands.util;

import io.indices.discordbots.kurt.commands.Command;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class WolframAlphaCommand extends Command {

    public WolframAlphaCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {

    }

    @Override
    public void help(MessageChannel user) {

    }
}
