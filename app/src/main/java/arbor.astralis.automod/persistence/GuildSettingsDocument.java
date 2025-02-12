package arbor.astralis.automod.persistence;

import javax.annotation.Nullable;

public final class GuildSettingsDocument {
    
    private @Nullable Long staffRoleId;
    private @Nullable Long modRoleId;

    @Nullable
    public Long getStaffRoleId() {
        return staffRoleId;
    }

    public void setStaffRoleId(@Nullable Long staffRoleId) {
        this.staffRoleId = staffRoleId;
    }

    @Nullable
    public Long getModRoleId() {
        return modRoleId;
    }

    public void setModRoleId(@Nullable Long modRoleId) {
        this.modRoleId = modRoleId;
    }
}
