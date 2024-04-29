package de.keksuccino.fmsia.optin;

import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public class OptInUser {

    private UUID uuid;
    private boolean enable_addon;

    protected OptInUser() {
    }

    protected OptInUser(@NotNull UUID uuid, boolean enableAddon) {
        this.uuid = uuid;
        this.enable_addon = enableAddon;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public boolean isAddonEnabled() {
        return this.enable_addon;
    }

}
