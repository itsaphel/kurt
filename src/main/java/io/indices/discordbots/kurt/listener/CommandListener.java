package io.indices.discordbots.kurt.listener;

import io.indices.discordbots.kurt.Bot;
import io.indices.discordbots.kurt.command.CommandModule;
import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.java.Log;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

@Log
@Singleton
public class CommandListener {

    @Inject
    private CommandModule commandModule;

    @SubscribeEvent
    public void onCommand(MessageReceivedEvent event) {
        log.info("Received message: " + event.getMessage().getContentRaw());
        String rawMessage = event.getMessage().getContentRaw();

        if (rawMessage.startsWith(Bot.CHAT_PREFIX)) {
            rawMessage = rawMessage.trim();
            String[] args = rawMessage.split("\\p{javaSpaceChar}+");

            if (rawMessage.isEmpty() || args.length < 1) {
                return;
            }

            String commandLabel = args[0].substring(1);
            commandModule.getCommand(commandLabel)
              .ifPresent(command -> command.invoke(Arrays.copyOfRange(args, 1, args.length), event.getMessage()));
        }
    }
}
