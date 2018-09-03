package io.indices.discordbots.kurt.command.util;

import io.indices.discordbots.kurt.command.Command;
import io.indices.discordbots.kurt.rest.UrbanDictionaryApi;
import java.util.Optional;
import javax.inject.Inject;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

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

        Optional<String> optDef = urbanDictionaryApi.getDefinition(commandArgs[0]);

        if (optDef.isPresent()) {
            message.getChannel().sendMessage(commandArgs[0] + ": " + optDef.get()).queue();
        } else {
            message.getChannel().sendMessage("Couldn't get an Urban Dictionary definition for that term.").queue();
        }
    }

    @Override
    public void help(MessageChannel user) {
        //
    }
}
