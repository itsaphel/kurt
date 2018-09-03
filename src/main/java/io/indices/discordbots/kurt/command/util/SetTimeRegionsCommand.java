package io.indices.discordbots.kurt.command.util;

import io.indices.discordbots.kurt.command.Command;
import io.indices.discordbots.kurt.command.Permission;
import io.indices.discordbots.kurt.scheduler.RegionChangeScheduler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.utils.PermissionUtil;

public class SetTimeRegionsCommand extends Command {

    @Inject
    private RegionChangeScheduler regionChangeScheduler;

    private String ARG_SYNTAX_REGEX = "^[0-23]{2}[0-60]{2}:[a-zA-Z-]*";
    private Permission permission = Permission.USER;

    public SetTimeRegionsCommand(String name, Permission permission, String... aliases) {
        this(name, aliases);
        this.permission = permission;
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
          net.dv8tion.jda.core.Permission.MANAGE_SERVER)) {
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

        regionChangeScheduler.addCustomGuildSchedule(message.getGuild(), timeRegionMap);
    }

    @Override
    public void help(MessageChannel context) {
        context.sendMessage("Usage: " + name + " time:region|time:region [...]").queue();
    }

    @Override
    public Permission getRequiredPermission() {
        return permission;
    }
}
