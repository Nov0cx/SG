package com.nov0cx.sg.gamesystem;

import com.nov0cx.sg.SG;
import com.nov0cx.sg.events.GameStateChangeEvent;
import org.bukkit.Bukkit;

public enum GameState {
    WAITING, STARTING, PLAYING, END, PREPLAYING, SENDING;

    public static void changeGameState(GameState state) {
        SG.getInstance().setState(state);
        Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(state));
    }
}
