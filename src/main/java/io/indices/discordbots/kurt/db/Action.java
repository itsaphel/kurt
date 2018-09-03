package io.indices.discordbots.kurt.db;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.core.events.Event;

@Getter
@Setter
public class Action {

    private ActionType type;
    private String id;
    private String data;
    private Event rawEvent;
}
