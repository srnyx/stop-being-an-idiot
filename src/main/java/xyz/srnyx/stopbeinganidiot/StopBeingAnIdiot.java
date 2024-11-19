package xyz.srnyx.stopbeinganidiot;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.annoyingapi.AnnoyingPlugin;
import xyz.srnyx.annoyingapi.PluginPlatform;
import xyz.srnyx.annoyingapi.command.AnnoyingSender;
import xyz.srnyx.annoyingapi.data.StringData;
import xyz.srnyx.annoyingapi.file.AnnoyingData;
import xyz.srnyx.annoyingapi.file.AnnoyingFile;
import xyz.srnyx.annoyingapi.message.AnnoyingMessage;
import xyz.srnyx.annoyingapi.message.DefaultReplaceType;

import java.io.File;
import java.util.logging.Level;


public class StopBeingAnIdiot extends AnnoyingPlugin {
    @NotNull public static final String TABLE = "data";
    @NotNull public static final String COL_ENABLED = "enabled";

    @NotNull public StringData data;

    public StopBeingAnIdiot() {
        options
                .pluginOptions(pluginOptions -> pluginOptions.updatePlatforms(
                        PluginPlatform.modrinth("CyRCy5Yw"),
                        PluginPlatform.hangar(this),
                        PluginPlatform.spigot("107313")))
                .bStatsOptions(bStatsOptions -> bStatsOptions.id(19588))
                .dataOptions(dataOptions -> dataOptions
                        .enabled(true)
                        .table(TABLE, COL_ENABLED))
                .registrationOptions
                .automaticRegistration(automaticRegistration -> automaticRegistration.packages(
                        "xyz.srnyx.stopbeinganidiot.commands",
                        "xyz.srnyx.stopbeinganidiot.listeners"))
                .papiExpansionToRegister(() -> new SbaiPlaceholders(this));
    }

    @Override
    public void enable() {
        reload();
        convertOldData();
    }

    @Override
    public void reload() {
        data = new StringData(this, TABLE, "server");
    }

    public void toggle(boolean enable, @NotNull AnnoyingSender sender) {
        data.set(COL_ENABLED, enable ? true : null);
        new AnnoyingMessage(this, "command.toggle")
                .replace("%status%", enable, DefaultReplaceType.BOOLEAN)
                .send(sender);
    }

    //TODO remove in future
    private void convertOldData() {
        final AnnoyingData oldData = new AnnoyingData(this, "data.yml", new AnnoyingFile.Options<>().canBeEmpty(false));
        if (!oldData.file.exists()) return;
        if (!oldData.contains("converted_now-stored-elsewhere") && oldData.getBoolean("enabled")) data.set(COL_ENABLED, true);
        oldData.setSave("converted_now-stored-elsewhere", true);
        // Rename data file to old-data.yml
        if (!oldData.file.renameTo(new File(oldData.file.getParent(), "data-old.yml"))) log(Level.WARNING, "&cFailed to rename old data file: &4" + oldData.file.getPath());
    }
}
