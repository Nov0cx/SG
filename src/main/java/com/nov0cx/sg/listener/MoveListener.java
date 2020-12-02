package com.nov0cx.sg.listener;

import com.nov0cx.sg.SG;
import com.nov0cx.sg.gamesystem.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(SG.getInstance().getState() == GameState.PREPLAYING) {
            event.setTo(event.getFrom());
        }
    }
}
