package io.indices.discordbots.kurt.commands;

import io.indices.discordbots.kurt.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.utils.PermissionUtil;

import java.util.Arrays;
import java.util.List;

public abstract class Command {

    public final String name;
    public final List<String> aliases;

    public Command(String name, String... aliases) {
        this.name = name;
        this.aliases = Arrays.asList(aliases);
    }

    public void invoke(String[] commandArgs, Message message) {
        if (checkPermissions(message)) {
            onInvoke(commandArgs, message);
        }
    }

    public abstract void onInvoke(String[] commandArgs, Message message);

    public abstract void help(MessageChannel user);

    private boolean checkPermissions(Message message) {
        if (getRequiredPermission() == Permission.USER
                || Bot.botAdmins.contains(message.getAuthor().getId())) {
            return true;
        }

        if (getRequiredPermission() == Permission.GUILD_ADMIN
                && PermissionUtil.checkPermission(message.getMember(), net.dv8tion.jda.core.Permission.ADMINISTRATOR)) {
            return true;
        }

        return false;
    }

    public Permission getRequiredPermission() {
        return Permission.USER;
    }
}
