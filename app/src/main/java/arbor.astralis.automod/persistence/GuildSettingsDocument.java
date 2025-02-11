package arbor.astralis.automod.persistence;

import javax.annotation.Nullable;

public final class GuildSettingsDocument {
    
    private @Nullable Long modChannelId;
    private @Nullable Long modRoleId;

    @Nullable
    public Long getModChannelId() {
        return modChannelId;
    }

    public void setModChannelId(@Nullable Long modChannelId) {
        this.modChannelId = modChannelId;
    }

    @Nullable
    public Long getModRoleId() {
        return modRoleId;
    }

    public void setModRoleId(@Nullable Long modRoleId) {
        this.modRoleId = modRoleId;
    }
}
