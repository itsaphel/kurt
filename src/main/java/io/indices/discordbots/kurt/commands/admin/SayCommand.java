package io.indices.discordbots.kurt.commands.admin;

import io.indices.discordbots.kurt.Bot;
import io.indices.discordbots.kurt.commands.Command;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class SayCommand extends Command {

    public SayCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {
        if (Bot.botAdmins.contains(message.getAuthor().getId())) {
            message.delete().queue();
            message.getChannel().sendMessage(String.join(" ", commandArgs)).queue();
        }
    }

    @Override
    public void help(MessageChannel user) {

    }
}
