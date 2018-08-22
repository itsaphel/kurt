package io.indices.discordbots.kurt.commands.log;

import io.indices.discordbots.kurt.commands.Command;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class PurgeLogListenCommand extends Command {

    public PurgeLogListenCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {

    }

    @Override
    public void help(MessageChannel user) {

    }
}
