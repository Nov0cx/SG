package com.nov0cx.sg.listener;

import com.nov0cx.sg.SG;
import com.nov0cx.sg.gamesystem.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if(SG.getInstance().getState() != GameState.WAITING
                && SG.getInstance().getState() != GameState.STARTING) {
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, ChatColor.YELLOW + "The game is already started.");
            System.out.println(SG.getInstance().getState());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(Bukkit.getOnlinePlayers().size() >= SG.getInstance().getConfig().getInt("minPlayers")) {
            GameState.changeGameState(GameState.STARTING);
        }
    }
}
