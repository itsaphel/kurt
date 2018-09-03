package io.indices.discordbots.kurt.db.model;

import io.indices.discordbots.kurt.db.Action;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "EventLog")
@Getter
@Setter
public class EventLog {

    @Id
    private Long id;

    private String eventName;

    private String guildId;

    private String initiatingUserId;
    private String initiatingDiscrim;
    private String affectedUserId;
    private String affectedDiscrim;

    private Action action;

}
