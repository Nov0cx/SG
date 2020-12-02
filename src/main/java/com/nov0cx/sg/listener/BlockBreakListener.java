package com.nov0cx.sg.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getPlayer().getGameMode() == GameMode.SURVIVAL
                || event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            event.setCancelled(true);
        }
    }
}
