package com.github.mattnicee7.playerversioncheckerplugin.command;

import com.github.mattnicee7.playerversioncheckerplugin.PlayerVersionCheckerPlugin;
import com.github.mattnicee7.playerversioncheckerplugin.util.chat.ChatUtils;
import lombok.val;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class VersionCheckerLayerConfirm implements CommandExecutor {

    private final PlayerVersionCheckerPlugin plugin;

    public VersionCheckerLayerConfirm(PlayerVersionCheckerPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("versionchecklayerconfirm").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender)
            return false;

        val player = (Player) sender;

        if (plugin.getFloodgateAPI().isFloodgatePlayer(player.getUniqueId()))
            return false;

        val persistentDataContainer = player.getPersistentDataContainer();

        val alertLayerVoidKey = plugin.getKeys().get("alert-layer-void");

        if (persistentDataContainer.has(alertLayerVoidKey, PersistentDataType.INTEGER))
            return false;

        persistentDataContainer.set(alertLayerVoidKey, PersistentDataType.INTEGER, 1);
        player.sendMessage(ChatUtils.colorize(plugin.getConfig().getString("alert-layer-void.confirm-message")));

        return false;
    }

}
