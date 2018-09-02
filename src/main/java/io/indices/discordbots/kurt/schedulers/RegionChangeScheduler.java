package io.indices.discordbots.kurt.schedulers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Region;
import net.dv8tion.jda.core.entities.Guild;

public class RegionChangeScheduler {

    private Map<String, Map<Guild, String>> customGuildSchedules = new HashMap<>();

    public void scheduleRegionChanger(Map<String, String> defaults, JDA jda) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // todo run scheduler every 5 mins, round values
        // so this runs 288 times per day

        defaults.forEach((time, regionKey) -> {
            ZonedDateTime zonedDateTime = ZonedDateTime
              .of(LocalDateTime.now(), ZoneId.of("UTC"))
              .withHour(Integer.parseInt(time.substring(0, 2)))
              .withMinute(Integer.parseInt(time.substring(2, 4)))
              .withSecond(0);

            long startTime = LocalDateTime.now().until(zonedDateTime, ChronoUnit.MINUTES);

            scheduler.scheduleAtFixedRate(() -> {
                jda.getGuilds().forEach(guild -> {
                    Region region = Region.fromKey(regionKey);
                    guild.getManager().setRegion(region).queue();
                });
            }, startTime, TimeUnit.DAYS.toMinutes(1), TimeUnit.MINUTES);
        });
    }

    public void addCustomGuildSchedule(Guild guild, Map<String, String> schedule) {
        // todo organise
    }
}
