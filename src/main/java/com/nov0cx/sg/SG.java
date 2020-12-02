package com.nov0cx.sg;

import com.nov0cx.sg.commands.SGCommand;
import com.nov0cx.sg.gamesystem.ChestListener;
import com.nov0cx.sg.gamesystem.GameState;
import com.nov0cx.sg.killsystem.DamageListener;
import com.nov0cx.sg.listener.*;
import com.nov0cx.sg.utils.DataConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class SG extends JavaPlugin {
    @Getter
    private static SG instance;

    @Setter
    private GameState state = GameState.WAITING;

    private final List<Player> living = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        new DataConfig();
        living.addAll(Bukkit.getOnlinePlayers());
        Bukkit.getPluginManager().registerEvents(new ChestListener(), instance);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), instance);
        Bukkit.getPluginManager().registerEvents(new MoveListener(), instance);
        getCommand("sg").setExecutor(new SGCommand());
        Bukkit.getPluginManager().registerEvents(new StateListener(this), instance);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), instance);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), instance);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), instance);
    }

    @Override
    public void onDisable() {

    }

    public void onReload() {

    }
}
