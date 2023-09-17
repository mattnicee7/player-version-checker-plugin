package com.github.mattnicee7.playerversioncheckerplugin;

import com.github.mattnicee7.playerversioncheckerplugin.command.VersionCheckerConfirmCommand;
import com.github.mattnicee7.playerversioncheckerplugin.command.VersionCheckerLayerConfirm;
import com.github.mattnicee7.playerversioncheckerplugin.task.PlayerVersionCheckerTask;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.Map;

@Getter
public final class PlayerVersionCheckerPlugin extends JavaPlugin {

    private final Map<String, NamespacedKey> keys = Map.of(
            "alert-version", new NamespacedKey(this, "player-version-checker-plugin-alert-version"),
            "alert-layer-void", new NamespacedKey(this, "player-version-checker-plugin-alert-layer-void")
    );

    private FloodgateApi floodgateAPI;
    private ViaAPI viaAPI;

    @Override
    public void onEnable() {
        this.viaAPI = Via.getPlatform().getApi();
        this.floodgateAPI = FloodgateApi.getInstance();

        this.saveDefaultConfig();
        this.reloadConfig();

        this.registerCommands();

        new PlayerVersionCheckerTask(this).runTaskTimerAsynchronously(
                this,
                0,
                getConfig().getLong("checker-task-delay")
        );
    }

    private void registerCommands() {
        new VersionCheckerConfirmCommand(this);
        new VersionCheckerLayerConfirm(this);
    }

}
