package xyz.srnyx.stopbeinganidiot;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import xyz.srnyx.annoyingapi.AnnoyingMessage;
import xyz.srnyx.annoyingapi.command.AnnoyingCommand;
import xyz.srnyx.annoyingapi.command.AnnoyingSender;

import java.util.Arrays;
import java.util.Collection;


public class SbaiCommand implements AnnoyingCommand {
    @NotNull private final StopBeingAnIdiot plugin;

    @Contract(pure = true)
    public SbaiCommand(@NotNull StopBeingAnIdiot plugin) {
        this.plugin = plugin;
    }

    @Override @NotNull
    public StopBeingAnIdiot getPlugin() {
        return plugin;
    }

    @Override @NotNull
    public String getPermission() {
        return "sbai.command";
    }

    @Override
    public void onCommand(@NotNull AnnoyingSender sender) {
        final String[] args = sender.getArgs();

        // No arguments
        if (args.length == 0) {
            plugin.toggle(!plugin.enabled, sender);
            return;
        }

        // <on|off|reload>
        if (args.length == 1) {
            // <on|off>
            if (sender.argEquals(0, "on", "off")) {
                plugin.toggle(sender.argEquals(0, "on"), sender);
                return;
            }

            // reload
            if (sender.argEquals(0, "reload")) {
                plugin.reloadPlugin();
                new AnnoyingMessage(plugin, "command.reload").send(sender);
            }
        }
    }

    @Override @NotNull
    public Collection<String> onTabComplete(@NotNull AnnoyingSender sender) {
        return Arrays.asList("on", "off", "reload");
    }
}
