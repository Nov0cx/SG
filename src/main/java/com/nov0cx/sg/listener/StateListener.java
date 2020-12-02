package com.nov0cx.sg.listener;

import com.nov0cx.sg.SG;
import com.nov0cx.sg.events.GameStateChangeEvent;
import com.nov0cx.sg.gamesystem.GameState;
import com.nov0cx.sg.utils.DataConfig;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class StateListener implements Listener {
    private final SG sg;
    private BukkitTask bt;

    public StateListener(SG sg) {
        this.sg = sg;
    }

    @EventHandler
    public void onStateChange(GameStateChangeEvent event) {
        switch (event.getState()) {
            case WAITING: {
                break;
            }
            case STARTING: {
                bt = new BukkitRunnable() {
                    int secs = 60;

                    @Override
                    public void run() {
                        switch (secs) {
                            case 60:
                            case 50:
                            case 40:
                            case 30:
                            case 20: {
                                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Game starting. " + secs + " until the start.");
                                break;
                            }
                            case 15: {
                                Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "Game starting. " + secs + " until the start.");
                                break;
                            }
                            case 10:
                            case 5:
                            case 4:
                            case 3:
                            case 2:
                            case 1: {
                                Bukkit.getServer().broadcastMessage(ChatColor.RED + "Game starting. " + secs + " until the start.");
                                break;
                            }
                            case 0: {
                                Bukkit.getServer().broadcastMessage(ChatColor.RED + "Game started!");
                                GameState.changeGameState(GameState.SENDING);
                                break;
                            }
                        }
                        secs--;
                    }
                }.runTaskTimer(sg, 0, 20);
                break;
            }
            case SENDING: {
                if(bt != null) {
                    bt.cancel();
                }
                int i = 1;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Location location = new Location(Bukkit.getWorld((String) DataConfig.get("loc." + i + ".world")), (double) DataConfig.get("loc." + i + ".x"),
                            (double) DataConfig.get("loc." + i + ".y"), (double) DataConfig.get("loc." + i + ".z"));
                    player.teleport(location);
                    player.sendMessage(ChatColor.GREEN + "You got teleported to you spawn");
                    i++;
                }
                GameState.changeGameState(GameState.PREPLAYING);
                break;
            }
            case PREPLAYING: {
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Game starting in 5 seconds.");
                Bukkit.getScheduler().runTaskLater(sg, () -> {
                    Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "GO! GO! GO!");
                    GameState.changeGameState(GameState.PLAYING);
                }, 60 * 5);
                break;
            }
            case END: {
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "The player " + ((Player)SG.getInstance().getLiving().toArray()[0]).getName() + " won!");
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "Server gets shutdown in 15 secs...");
                Bukkit.getScheduler().runTaskLater(SG.getInstance(), () -> Bukkit.getServer().shutdown(), 60 * 15);
                break;
            }
        }
    }
}
