package io.indices.discordbots.kurt.commands.util;

import io.indices.discordbots.kurt.Bot;
import io.indices.discordbots.kurt.commands.Command;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.utils.PermissionUtil;

public class SetTimeRegionsCommand extends Command {

    private String ARG_SYNTAX_REGEX = "^[0-23]{2}[0-60]{2}:[a-zA-Z-]*";
    private Bot main;

    public SetTimeRegionsCommand(Bot main, String name, String... aliases) {
        super(name, aliases);
        this.main = main;
    }

    public SetTimeRegionsCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(String[] commandArgs, Message message) {
        String[] series = commandArgs[0].split("|");

        if (commandArgs.length != 1
            || !Arrays.stream(series).allMatch(p -> p.matches(ARG_SYNTAX_REGEX))
            || !PermissionUtil.checkPermission(message.getGuild().getMember(message.getAuthor()),
            Permission.MANAGE_SERVER)) {
            help(message.getChannel());
            return;
        }

        Map<String, String> timeRegionMap = new HashMap<>();

        Arrays.stream(series).forEach(s -> {
            String[] entry = s.split(":");

            if (entry.length == 2) {
                timeRegionMap.put(entry[0], entry[1]);
            }
        });

        main.getRegionChangeScheduler().addCustomGuildSchedule(message.getGuild(), timeRegionMap);
    }

    @Override
    public void help(MessageChannel context) {
        context.sendMessage("Usage: " + name + " time:region|time:region [...]").queue();
    }
}
