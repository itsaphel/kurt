package io.indices.discordbots.kurt.commands.util;

import io.indices.discordbots.kurt.Bot;
import io.indices.discordbots.kurt.commands.Command;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class WolframAlphaCommand extends Command {

    private Bot main;
    private Logger logger = Logger.getLogger(WolframAlphaCommand.class.getName());

    public WolframAlphaCommand(Bot main, String name, String... aliases) {
        super(name, aliases);
        this.main = main;
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {
        message.getChannel().sendTyping().queue();

        main.getWolframApi().getQueryImage(String.join(" ", commandArgs))
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
