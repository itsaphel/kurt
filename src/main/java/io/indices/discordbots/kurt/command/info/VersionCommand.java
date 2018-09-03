package io.indices.discordbots.kurt.command.info;

import io.indices.discordbots.kurt.command.Command;
import javax.inject.Inject;
import javax.inject.Named;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class VersionCommand extends Command {

    @Inject
    @Named("BotVersion")
    private String botVersion;

    public VersionCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {
        message.getChannel().sendMessage("Running version: " + botVersion).queue();
    }

    @Override
    public void help(MessageChannel user) {
        //
    }
}
