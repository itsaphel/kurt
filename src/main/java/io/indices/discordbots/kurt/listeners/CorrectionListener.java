package io.indices.discordbots.kurt.listeners;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

public class CorrectionListener {

    @SubscribeEvent
    public void onCorrection(MessageReceivedEvent event) {
        String rawMessage = event.getMessage().getContentRaw();

        if (rawMessage.startsWith("c/")) {
            rawMessage = rawMessage.trim();
            String[] args = rawMessage.split("/");

            if (rawMessage.isEmpty() || args.length != 3) {
                return;
            }

            sendCorrectionMessage(event.getMessage(), args[1], args[2]);
        }
    }

    private void sendCorrectionMessage(Message correctionMsg, String extract, String replacement) {
        correctionMsg.getChannel().getHistoryBefore(correctionMsg, 25).queue(msgs -> {
            msgs.getRetrievedHistory().stream().filter(msg -> msg.getContentRaw().contains(extract)).findFirst().ifPresent(targetMessage -> {
                String replaced = targetMessage.getContentRaw().replace(extract, "**" + replacement + "**");

                correctionMsg.getChannel().sendMessage("Correction, " + correctionMsg.getAuthor().getAsMention() + ", " + replaced).queue();
            });
        });
    }
}
