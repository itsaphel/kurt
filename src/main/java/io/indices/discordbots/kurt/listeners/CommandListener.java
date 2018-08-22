package io.indices.discordbots.kurt.listeners;

import io.indices.discordbots.kurt.Bot;
import java.util.Arrays;
import java.util.logging.Logger;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

public class CommandListener {

    private Bot main;
    private Logger logger = Logger.getLogger(CommandListener.class.getName());

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

            logger.info("Command: " + rawMessage);

            String commandLabel = args[0].substring(1);
            main.getCommandManager().getCommand(commandLabel).ifPresent(command -> {
                command.onInvoke(Arrays.copyOfRange(args, 1, args.length), event.getMessage());
            });
        }
    }
}
