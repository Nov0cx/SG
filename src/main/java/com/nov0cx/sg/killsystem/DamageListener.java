package com.nov0cx.sg.killsystem;

import com.nov0cx.sg.SG;
import com.nov0cx.sg.gamesystem.GameState;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class DamageListener implements Listener {
    @Getter
    private final HashMap<Player, Player> lastDamagerMap = new HashMap<>();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(SG.getInstance().getState() != GameState.PLAYING) {
            event.setCancelled(true);
            return;
        }
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player target = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            lastDamagerMap.putIfAbsent(target, damager);
            lastDamagerMap.replace(target, damager);
            if(SG.getInstance().getLiving().size() == 1) {
                damager.setGameMode(GameMode.SPECTATOR);
            }
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player dead = event.getEntity();
        Player lastDamager = lastDamagerMap.getOrDefault(dead, null);
        if(lastDamager == null) {
            event.setDeathMessage("The player " + dead.getName() + " died.");
        } else {
            event.setDeathMessage("The player " + dead.getName() + " got killed by " + lastDamager.getName() + ".");
            lastDamager.sendMessage(ChatColor.GREEN + "You killed " + dead.getName() + ".");
        }
    }
}
