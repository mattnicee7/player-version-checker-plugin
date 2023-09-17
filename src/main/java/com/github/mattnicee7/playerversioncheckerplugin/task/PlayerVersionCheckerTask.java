package com.github.mattnicee7.playerversioncheckerplugin.task;

import com.github.mattnicee7.playerversioncheckerplugin.PlayerVersionCheckerPlugin;
import com.github.mattnicee7.playerversioncheckerplugin.util.chat.ChatUtils;
import lombok.val;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class PlayerVersionCheckerTask extends BukkitRunnable {

    private final PlayerVersionCheckerPlugin plugin;

    private final List<Integer> alertVersionAllowedProtocols;
    private final List<Integer> alertLayerVoidAllowedProtocols;

    private final NamespacedKey alertVersionKey;
    private final NamespacedKey alertLayerVoidKey;

    private final String[] alertVersionTitle;
    private final String[] alertLayerVoidTitle;

    private final List<String> alertVersionMessage;
    private final List<String> alertLayerVoidMessage;

    private final int checkAboveLayer;

    public PlayerVersionCheckerTask(PlayerVersionCheckerPlugin plugin) {
        this.plugin = plugin;
        val config = plugin.getConfig();

        this.alertVersionAllowedProtocols = config.getIntegerList("alert-version.allowed-protocols");
        this.alertLayerVoidAllowedProtocols = config.getIntegerList("alert-layer-void.allowed-protocols");

        this.alertVersionKey = plugin.getKeys().get("alert-version");
        this.alertLayerVoidKey = plugin.getKeys().get("alert-layer-void");

        this.alertVersionTitle = config.getString("alert-version.title").split("<nl>");
        this.alertLayerVoidTitle = config.getString("alert-layer-void.title").split("<nl>");

        this.alertVersionMessage = ChatUtils.colorizeMessages(config.getStringList("alert-version.message"));
        this.alertLayerVoidMessage = ChatUtils.colorizeMessages(config.getStringList("alert-layer-void.message"));

        this.checkAboveLayer = config.getInt("check-above-layer");
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (plugin.getFloodgateAPI().isFloodgatePlayer(player.getUniqueId())) continue;

            int playerProtocol = plugin.getViaAPI().getPlayerVersion(player.getUniqueId());

            if (shouldSendVersionAlert(player, playerProtocol)) {
                sendVersionAlert(player);
            } else if (shouldSendLayerVoidAlert(player, playerProtocol)) {
                sendLayerVoidAlert(player);
            }
        }
    }

    private boolean shouldSendVersionAlert(Player player, int playerProtocol) {
        return !player.getPersistentDataContainer().has(alertVersionKey, PersistentDataType.INTEGER) &&
                alertVersionAllowedProtocols.contains(playerProtocol);
    }

    private boolean shouldSendLayerVoidAlert(Player player, int playerProtocol) {
        return !alertVersionAllowedProtocols.contains(playerProtocol) &&
                !player.getPersistentDataContainer().has(alertLayerVoidKey, PersistentDataType.INTEGER) &&
                player.getLocation().getY() <= checkAboveLayer &&
                alertLayerVoidAllowedProtocols.contains(playerProtocol);
    }

    private void sendVersionAlert(Player player) {
        sendAlert(player, alertVersionTitle, alertVersionMessage, "/versioncheckconfirm");
    }

    private void sendLayerVoidAlert(Player player) {
        sendAlert(player, alertLayerVoidTitle, alertLayerVoidMessage, "/versionchecklayerconfirm");
    }

    private void sendAlert(Player player, String[] title, List<String> messages, String command) {
        player.sendTitle(
                ChatUtils.colorize(title[0]),
                ChatUtils.colorize(title[1])
        );

        messages.forEach(message -> {
            TextComponent textComponentMessage = new TextComponent(message);
            textComponentMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
            player.spigot().sendMessage(textComponentMessage);
        });
    }

}
