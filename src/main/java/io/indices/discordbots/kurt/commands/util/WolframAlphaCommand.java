package io.indices.discordbots.kurt.commands.util;

import io.indices.discordbots.kurt.commands.Command;
import io.indices.discordbots.kurt.rest.WolframApi;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WolframAlphaCommand extends Command {

    @Inject
    private WolframApi wolframApi;

    private Logger logger = Logger.getLogger(WolframAlphaCommand.class.getName());

    public WolframAlphaCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {
        message.getChannel().sendTyping().queue();

        wolframApi.getQueryImage(String.join(" ", commandArgs))
                .ifPresentOrElse(present -> {
                    try {
                        BufferedImage imageStream = ImageIO.read(new URL(present));
                        File file = new File("WolframOutput.gif");
                        ImageIO.write(imageStream, "gif", file);
                        message.getChannel().sendFile(file).queue();
                    } catch (IOException e) {
                        logger.log(Level.WARNING, "Failed to parse URL from Wolfram API", e);
                    }
                }, () -> message.getChannel().sendMessage("No results for query.").queue());
    }

    @Override
    public void help(MessageChannel user) {

    }
}
