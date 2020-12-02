package com.nov0cx.sg.listener;

import com.nov0cx.sg.SG;
import com.nov0cx.sg.gamesystem.GameState;
import org.bukkit.GameMode;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(event.getEntity() == null)
            return;
        event.getEntity().setGameMode(GameMode.SPECTATOR);
        event.getEntity().spigot().respawn();
        SG.getInstance().getLiving().remove(event.getEntity());
        if(SG.getInstance().getLiving().size() == 1) {
            GameState.changeGameState(GameState.END);
            for(Player player : SG.getInstance().getLiving()) {
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}
