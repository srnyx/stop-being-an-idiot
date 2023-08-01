package xyz.srnyx.stopbeinganidiot;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xyz.srnyx.annoyingapi.AnnoyingPAPIExpansion;


public class SbaiPlaceholders extends AnnoyingPAPIExpansion {
    @NotNull private final StopBeingAnIdiot plugin;

    public SbaiPlaceholders(@NotNull StopBeingAnIdiot plugin) {
        this.plugin = plugin;
    }

    @Override @NotNull
    public StopBeingAnIdiot getAnnoyingPlugin() {
        return plugin;
    }

    @Override @NotNull
    public String getIdentifier() {
        return "sbai";
    }

    @Override @Nullable
    public String onPlaceholderRequest(@Nullable Player player, @NotNull String identifier) {
        return identifier.equals("status") ? String.valueOf(plugin.enabled) : null;
    }
}
