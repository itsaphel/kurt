package io.indices.discordbots.kurt.commands.util;

import io.indices.discordbots.kurt.commands.Command;
import io.indices.discordbots.kurt.rest.UrbanDictionaryApi;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import javax.inject.Inject;

public class UrbanDictionaryCommand extends Command {

    @Inject
    private UrbanDictionaryApi urbanDictionaryApi;

    public UrbanDictionaryCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {
        if (urbanDictionaryApi.getBlacklistedWords().contains(commandArgs[0])) {
            return;
        }

        message.getChannel().sendTyping().queue();

        urbanDictionaryApi.getDefinition(commandArgs[0]).ifPresentOrElse(
                present -> message.getChannel().sendMessage(commandArgs[0] + ": " + present).queue(),
                () -> message.getChannel().sendMessage("Couldn't get an Urban Dictionary definition for that term.").queue()
        );
    }

    @Override
    public void help(MessageChannel user) {
        //
    }
}
