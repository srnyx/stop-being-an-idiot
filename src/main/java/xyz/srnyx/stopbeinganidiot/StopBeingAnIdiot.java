package xyz.srnyx.stopbeinganidiot;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.annoyingapi.AnnoyingMessage;
import xyz.srnyx.annoyingapi.AnnoyingPlugin;
import xyz.srnyx.annoyingapi.command.AnnoyingSender;
import xyz.srnyx.annoyingapi.file.AnnoyingData;


public class StopBeingAnIdiot extends AnnoyingPlugin {
    private AnnoyingData data;
    public boolean enabled = false;

    public StopBeingAnIdiot() {
        super();
        reload();

        // Options
        options.commands.add(new SbaiCommand(this));
        options.listeners.add(new DeathListener(this));
    }

    @Override
    public void reload() {
        data = new AnnoyingData(this, "data.yml", false);
        enabled = data.getBoolean("enabled");
    }

    public void toggle(boolean status, @NotNull AnnoyingSender sender) {
        enabled = status;
        data.set("enabled", status, true);
        new AnnoyingMessage(this, "command.toggle")
                .replace("%status%", status, AnnoyingMessage.DefaultReplaceType.BOOLEAN)
                .send(sender);
    }
}
