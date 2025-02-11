package arbor.astralis.automod;

import javax.annotation.Nullable;
import java.util.Optional;

public final class GuildSettings {
    
    private final long guildId;
    
    private @Nullable Long modRoleId;
    private @Nullable Long modChannelId;
    
    
    public GuildSettings(long guildId) {
        this.guildId = guildId;
    }

    public synchronized Optional<Long> getModChannelId() {
        return Optional.ofNullable(modChannelId);
    }
    
    public synchronized void setQuestionModChannel(@Nullable Long channelId) {
        this.modChannelId = channelId;
        Settings.persistForGuild(this);
    }
    
    public synchronized Optional<Long> getModRoleId() {
        return Optional.ofNullable(modRoleId);
    }
    
    public synchronized void setModRoleId(@Nullable Long roleId) {
        this.modRoleId = roleId;
        Settings.persistForGuild(this);
    }

    public synchronized long getGuildId() {
        return guildId;
    }
}
