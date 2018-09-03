package io.indices.discordbots.kurt.listener;

import io.indices.discordbots.kurt.db.Action;
import io.indices.discordbots.kurt.db.ActionType;
import io.indices.discordbots.kurt.db.model.EventLog;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Singleton;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.core.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.core.events.guild.update.GenericGuildUpdateEvent;
import net.dv8tion.jda.core.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.core.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.role.GenericRoleEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

@Singleton
public class LoggingListener {

    private Set<String> botPrefixes = new HashSet<>();

    public LoggingListener() {
        botPrefixes.add("!");
        botPrefixes.add("?");
        botPrefixes.add("+");
        botPrefixes.add(">>");
        botPrefixes.add("<<");
        botPrefixes.add("*");
        botPrefixes.add("-");
    }

    @SubscribeEvent
    public void guildBanEvent(GuildBanEvent event) {
        Action action = new Action();
        action.setType(ActionType.GUILD_GENERIC);
        action.setRawEvent(event);

        log(event.getClass().getName(), event.getGuild(), event.getUser(), null, action);
    }

    @SubscribeEvent
    public void guildJoinEvent(GuildJoinEvent event) {
        Action action = new Action();
        action.setType(ActionType.GUILD_GENERIC);
        action.setRawEvent(event);

        log(event.getClass().getName(), event.getGuild(), null, null, action);
    }

    @SubscribeEvent
    public void guildLeaveEvent(GuildLeaveEvent event) {
        Action action = new Action();
        action.setType(ActionType.GUILD_GENERIC);
        action.setRawEvent(event);

        log(event.getClass().getName(), event.getGuild(), null, null, action);
    }

    @SubscribeEvent
    public void guildUnbanEvent(GuildUnbanEvent event) {
        Action action = new Action();
        action.setType(ActionType.GUILD_GENERIC);
        action.setRawEvent(event);

        log(event.getClass().getName(), event.getGuild(), event.getUser(), null, action);
    }

    @SubscribeEvent
    public void guildMessageReceived(GuildMessageReceivedEvent event) {
        Action action = new Action();
        action.setType(ActionType.GUILD_MESSAGE);
        action.setId(event.getMessage().getId());
        action.setData(event.getMessage().getContentRaw());
        action.setRawEvent(event);

        log(event.getClass().getName(), event.getGuild(), null, event.getAuthor(), action);
    }

    @SubscribeEvent
    public void guildUpdateEvent(GenericGuildUpdateEvent event) {
        Action action = new Action();
        action.setType(ActionType.GUILD_UPDATE);
        action.setId(event.getPropertyIdentifier());
        action.setData(event.getOldValue().toString() + "\n\n{{TO}}\n\n" + event.getNewValue().toString());
        action.setRawEvent(event);

        log(event.getClass().getName(), event.getGuild(), null, null, action);
    }

    @SubscribeEvent
    public void guildMemberEvent(GenericGuildMemberEvent event) {
        Action action = new Action();
        action.setType(ActionType.GUILD_MEMBER_ACTION);
        action.setRawEvent(event);

        log(event.getClass().getName(), event.getGuild(), null, event.getUser(), action);
    }

    @SubscribeEvent
    public void guildVoiceEvent(GenericGuildVoiceEvent event) {
        Action action = new Action();
        action.setType(ActionType.GUILD_MEMBER_VOICE);
        action.setId(event.getVoiceState().getChannel().getId());
        action.setRawEvent(event);

        log(event.getClass().getName(), event.getGuild(), null, event.getMember().getUser(), action);
    }

    @SubscribeEvent
    public void guildRoleEvent(GenericRoleEvent event) {
        Action action = new Action();
        action.setType(ActionType.GUILD_MEMBER_VOICE);
        action.setId(event.getRole().getId());
        action.setData(event.getRole().getName());
        action.setRawEvent(event);

        log(event.getClass().getName(), event.getGuild(), null, null, action);
    }

    @SubscribeEvent
    public void commandExecutionEvent(GenericGuildMessageEvent event) {
        // does message start with generic command prefix? any bot.
        event.getChannel().getMessageById(event.getMessageId()).queue(message -> {
            if (message != null) {
                String prefix = Character.toString(message.getContentRaw().charAt(0));
                if (botPrefixes.contains(prefix)) {
                    Action action = new Action();
                    action.setType(ActionType.GUILD_COMMAND);
                    action.setId(event.getMessageId());
                    action.setData(message.getContentRaw());
                    action.setRawEvent(event);

                    log(event.getClass().getName(), event.getGuild(), null, null, action);
                }
            }
        });
    }

    private void log(String name, Guild guild, User affected, User initiator, Action action) {
        EventLog entry = new EventLog();
        entry.setEventName(name);
        entry.setGuildId(guild.getId());

        if (affected != null) {
            entry.setAffectedUserId(affected.getId());
            entry.setAffectedDiscrim(affected.getName() + "#" + affected.getDiscriminator());
        }

        if (initiator != null) {
            entry.setAffectedUserId(initiator.getId());
            entry.setAffectedDiscrim(initiator.getName() + "#" + initiator.getDiscriminator());
        }

        entry.setAction(action);
    }
}
