package xyz.srnyx.stopbeinganidiot.commands;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.annoyingapi.command.AnnoyingCommand;
import xyz.srnyx.annoyingapi.command.AnnoyingSender;
import xyz.srnyx.annoyingapi.message.AnnoyingMessage;

import xyz.srnyx.stopbeinganidiot.StopBeingAnIdiot;

import java.util.Arrays;
import java.util.List;


public class SbaiCommand extends AnnoyingCommand {
    @NotNull private final StopBeingAnIdiot plugin;

    public SbaiCommand(@NotNull StopBeingAnIdiot plugin) {
        this.plugin = plugin;
    }

    @Override @NotNull
    public StopBeingAnIdiot getAnnoyingPlugin() {
        return plugin;
    }

    @Override @NotNull
    public String getPermission() {
        return "sbai.command";
    }

    @Override
    public void onCommand(@NotNull AnnoyingSender sender) {
        // No arguments (toggle)
        if (sender.args.length == 0) {
            plugin.toggle(!plugin.data.has(StopBeingAnIdiot.COL_ENABLED), sender);
            return;
        }

        // reload
        if (sender.argEquals(0, "reload")) {
            plugin.reloadPlugin();
            new AnnoyingMessage(plugin, "command.reload").send(sender);
            return;
        }

        // <on|...>
        plugin.toggle(sender.argEquals(0, "on"), sender);
    }

    @Override @NotNull
    public List<String> onTabComplete(@NotNull AnnoyingSender sender) {
        return Arrays.asList("reload", plugin.data.has(StopBeingAnIdiot.COL_ENABLED) ? "off" : "on");
    }
}
