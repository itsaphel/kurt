package io.indices.discordbots.kurt.command.util;

import io.indices.discordbots.kurt.command.Command;
import io.indices.discordbots.kurt.rest.WolframApi;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.extern.java.Log;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

@Log
public class WolframAlphaCommand extends Command {

    @Inject
    private WolframApi wolframApi;

    public WolframAlphaCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {
        message.getChannel().sendTyping().queue();

        Optional<String> optImg = wolframApi.getQueryImage(String.join(" ", commandArgs));

        if (optImg.isPresent()) {
            try {
                BufferedImage imageStream = ImageIO.read(new URL(optImg.get()));
                File file = new File("WolframOutput.gif");
                ImageIO.write(imageStream, "gif", file);
                message.getChannel().sendFile(file).queue();
            } catch (IOException e) {
                log.log(Level.WARNING, "Failed to parse URL from Wolfram API", e);
            }
        } else {
            message.getChannel().sendMessage("No results for query.").queue();
        }
    }

    @Override
    public void help(MessageChannel user) {

    }
}
