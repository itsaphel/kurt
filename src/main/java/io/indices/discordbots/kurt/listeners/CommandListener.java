package io.indices.discordbots.kurt.listeners;

import io.indices.discordbots.kurt.Bot;
import java.util.Arrays;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

public class CommandListener {

    private Bot main;

    public CommandListener(Bot main) {
        this.main = main;
    }

    @SubscribeEvent
    public void onCommand(MessageReceivedEvent event) {
        String rawMessage = event.getMessage().getContentRaw();

        if (rawMessage.startsWith(Bot.CHAT_PREFIX)) {
            rawMessage = rawMessage.trim();
            String[] args = rawMessage.split("\\p{javaSpaceChar}+");

            if (rawMessage.isEmpty() || args.length < 1) {
                return;
            }

            String commandLabel = args[0];
            main.getCommandManager().getCommand(commandLabel).ifPresent(command -> {
                command.onInvoke(Arrays.copyOfRange(args, 1, args.length), event.getMessage());
            });
        }
    }
}
