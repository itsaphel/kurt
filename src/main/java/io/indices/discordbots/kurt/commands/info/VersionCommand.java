package io.indices.discordbots.kurt.commands.info;

import io.indices.discordbots.kurt.commands.Command;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class VersionCommand extends Command {

    public VersionCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {
        message.getChannel().sendMessage("Running version: " + VersionCommand.class.getPackage().getImplementationVersion());
    }

    @Override
    public void help(MessageChannel user) {
        //
    }
}
