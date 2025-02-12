package arbor.astralis.automod;

import javax.annotation.Nullable;
import java.util.Optional;

public final class GuildSettings {
    
    private final long guildId;
    
    private @Nullable Long modRoleId;
    private @Nullable Long staffRoleId;
    
    
    public GuildSettings(long guildId) {
        this.guildId = guildId;
    }

    public synchronized Optional<Long> getStaffRoleId() {
        return Optional.ofNullable(staffRoleId);
    }
    
    public synchronized void setStaffRoleId(@Nullable Long roleId) {
        this.staffRoleId = roleId;
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
