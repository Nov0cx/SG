package com.nov0cx.sg.commands;

import com.nov0cx.sg.gamesystem.GameState;
import com.nov0cx.sg.utils.DataConfig;
import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SGCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 2 && commandSender instanceof Player) {
            if (args[0].equalsIgnoreCase("setLoc")) {
                int index = Integer.parseInt(args[1]);
                Player player = ((Player) commandSender).getPlayer();
                Location loc = player.getLocation();
                if (!DataConfig.constains("loc." + index + ".x")) {
                    DataConfig.insert("loc." + index + ".world", loc.getWorld().getName());
                    DataConfig.insert("loc." + index + ".x", loc.getX());
                    DataConfig.insert("loc." + index + ".y", loc.getY());
                    DataConfig.insert("loc." + index + ".z", loc.getZ());
                } else {
                    DataConfig.remove("loc." + index);
                    DataConfig.insert("loc." + index + ".world", loc.getWorld().getName());
                    DataConfig.insert("loc." + index + ".x", loc.getX());
                    DataConfig.insert("loc." + index + ".y", loc.getY());
                    DataConfig.insert("loc." + index + ".z", loc.getZ());
                }
                player.sendMessage(ChatColor.GREEN + "Saved Location " + index);
            }
        } else if(args.length == 0) {
            if(commandSender instanceof Player) {
                runHelp((Player) commandSender);
            }
        } else if(args.length == 1) {
            if(args[0].equalsIgnoreCase("start")) {
                GameState.changeGameState(GameState.SENDING);
            }
        }
        return false;
    }

    private void runHelp(Player player) {
        TextComponent setLoc = new TextComponent("/sg setLoc <index>");
        setLoc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("sets the start loc of that index \nstart with index1").create()));
        TextComponent start = new TextComponent("/sg start");
        start.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("starts the game").create()));
        player.spigot().sendMessage(setLoc);
        player.spigot().sendMessage(start);
    }
}
