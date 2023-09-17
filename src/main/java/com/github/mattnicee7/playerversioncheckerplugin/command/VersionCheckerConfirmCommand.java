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

public class VersionCheckerConfirmCommand implements CommandExecutor {

    private final PlayerVersionCheckerPlugin plugin;

    public VersionCheckerConfirmCommand(PlayerVersionCheckerPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("versioncheckconfirm").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender)
            return false;

        val player = (Player) sender;

        if (plugin.getFloodgateAPI().isFloodgatePlayer(player.getUniqueId()))
            return false;

        val persistentDataContainer = player.getPersistentDataContainer();

        val alertVersionKey = plugin.getKeys().get("alert-version");

        if (persistentDataContainer.has(alertVersionKey, PersistentDataType.INTEGER))
            return false;

        persistentDataContainer.set(alertVersionKey, PersistentDataType.INTEGER, 1);
        player.sendMessage(ChatUtils.colorize(plugin.getConfig().getString("alert-version.confirm-message")));

        return false;
    }

}
