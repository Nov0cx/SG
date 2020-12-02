package com.nov0cx.sg.events;

import com.nov0cx.sg.gamesystem.GameState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@RequiredArgsConstructor
public class GameStateChangeEvent extends Event implements Cancellable {

    private final GameState state;

    private static final HandlerList handlerList = new HandlerList();

    @Override
    public boolean isCancelled() {
        return false;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
