package stories.simpletab;

import java.util.UUID;
import VMPlugin.Ranks.Rank;

public class k {
    UUID uuid;
    Rank runk;
    Integer second;

    public k(UUID uuid, Rank runk , Integer second) {
        this.uuid = uuid;
        this.runk = runk;
        this.second = second;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Rank getRunk() {
        return runk;
    }

    public void setRunk(Rank runk) {
        this.runk = runk;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }
}
