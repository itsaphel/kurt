package io.indices.discordbots.kurt.commands.util;

import io.indices.discordbots.kurt.Bot;
import io.indices.discordbots.kurt.commands.Command;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class UrbanDictionaryCommand extends Command {

    private Bot main;

    public UrbanDictionaryCommand(Bot main, String name, String... aliases) {
        super(name, aliases);
        this.main = main;
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {
        if (main.getUrbanDictionaryApi().getBlacklistedWords().contains(commandArgs[0])) {
            return;
        }

        message.getChannel().sendTyping().queue();

        main.getUrbanDictionaryApi().getDefinition(commandArgs[0]).ifPresentOrElse(
          present -> message.getChannel().sendMessage(commandArgs[0] + ": " + present).queue(),
          () -> message.getChannel().sendMessage("Couldn't get an Urban Dictionary definition for that term.").queue()
        );
    }

    @Override
    public void help(MessageChannel user) {
        //
    }
}
