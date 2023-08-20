package xyz.srnyx.stopbeinganidiot;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.annoyingapi.AnnoyingPlugin;
import xyz.srnyx.annoyingapi.PluginPlatform;
import xyz.srnyx.annoyingapi.command.AnnoyingSender;
import xyz.srnyx.annoyingapi.file.AnnoyingData;
import xyz.srnyx.annoyingapi.file.AnnoyingFile;
import xyz.srnyx.annoyingapi.message.AnnoyingMessage;
import xyz.srnyx.annoyingapi.message.DefaultReplaceType;


public class StopBeingAnIdiot extends AnnoyingPlugin {
    private AnnoyingData data;
    public boolean enabled = false;

    public StopBeingAnIdiot() {
        options
                .pluginOptions(pluginOptions -> pluginOptions.updatePlatforms(
                        PluginPlatform.modrinth("sbai"),
                        PluginPlatform.hangar(this, "srnyx"),
                        PluginPlatform.spigot("107313")))
                .bStatsOptions(bStatsOptions -> bStatsOptions.id(19588))
                .registrationOptions
                .automaticRegistration(automaticRegistration -> automaticRegistration.packages(
                        "xyz.srnyx.stopbeinganidiot.commands",
                        "xyz.srnyx.stopbeinganidiot.listeners"))
                .papiExpansionToRegister(() -> new SbaiPlaceholders(this));

        reload();
    }

    @Override
    public void reload() {
        data = new AnnoyingData(this, "data.yml", new AnnoyingFile.Options<>().canBeEmpty(false));
        enabled = data.getBoolean("enabled");
    }

    public void toggle(boolean status, @NotNull AnnoyingSender sender) {
        enabled = status;
        data.setSave("enabled", status);
        new AnnoyingMessage(this, "command.toggle")
                .replace("%status%", status, DefaultReplaceType.BOOLEAN)
                .send(sender);
    }
}
