package xyz.srnyx.stopbeinganidiot;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import xyz.srnyx.annoyingapi.AnnoyingListener;
import xyz.srnyx.annoyingapi.AnnoyingMessage;


public class DeathListener implements AnnoyingListener {
    @NotNull private final StopBeingAnIdiot plugin;
    private boolean justDied = false;

    @Contract(pure = true)
    public DeathListener(@NotNull StopBeingAnIdiot plugin) {
        this.plugin = plugin;
    }

    @Override @NotNull
    public StopBeingAnIdiot getPlugin() {
        return plugin;
    }

    /**
     * Thrown whenever a {@link Player} dies
     */
    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent event) {
        final Player player = event.getEntity();
        if (!plugin.enabled || justDied || !player.hasPermission("sbai.trigger")) return;

        // Kill all other players that don't have the bypass permission
        justDied = true;
        Bukkit.getOnlinePlayers().stream()
                .filter(online -> {
                    final GameMode gameMode = online.getGameMode();
                    return !online.equals(player) && !gameMode.equals(GameMode.CREATIVE) && !gameMode.equals(GameMode.SPECTATOR) && !online.hasPermission("sbai.bypass");
                })
                .forEach(online -> online.setHealth(0));
        justDied = false;

        // Send message
        new AnnoyingMessage(plugin, "death.message")
                .replace("%player%", player.getName())
                .broadcast(AnnoyingMessage.BroadcastType.CHAT);
        new AnnoyingMessage(plugin, "death.title")
                .replace("%player%", player.getName())
                .broadcast(AnnoyingMessage.BroadcastType.FULL_TITLE);
    }
}
