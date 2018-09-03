package io.indices.discordbots.kurt.listeners;

import io.indices.discordbots.kurt.Bot;
import io.indices.discordbots.kurt.commands.CommandManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

import javax.inject.Inject;
import java.util.Arrays;

public class CommandListener {

    @Inject
    private CommandManager commandManager;

    @SubscribeEvent
    public void onCommand(MessageReceivedEvent event) {
        String rawMessage = event.getMessage().getContentRaw();

        if (rawMessage.startsWith(Bot.CHAT_PREFIX)) {
            rawMessage = rawMessage.trim();
            String[] args = rawMessage.split("\\p{javaSpaceChar}+");

            if (rawMessage.isEmpty() || args.length < 1) {
                return;
            }

            String commandLabel = args[0].substring(1);
            commandManager.getCommand(commandLabel).ifPresent(command -> {
                command.invoke(Arrays.copyOfRange(args, 1, args.length), event.getMessage());
            });
        }
    }
}
