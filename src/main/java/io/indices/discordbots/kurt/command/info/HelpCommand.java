package io.indices.discordbots.kurt.command.info;

import io.indices.discordbots.kurt.command.Command;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class HelpCommand extends Command {

    public HelpCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {
        message.getChannel().sendMessage("See docs for help.").queue();
    }

    @Override
    public void help(MessageChannel user) {
        //
    }

}
