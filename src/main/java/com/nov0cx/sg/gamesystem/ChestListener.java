package com.nov0cx.sg.gamesystem;

import com.google.common.collect.Lists;
import com.nov0cx.sg.SG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ChestListener implements Listener {
    private final HashMap<Location, Inventory> chests = new HashMap<>();

    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (event.getClickedBlock().getType() == Material.CHEST) {
                if (!chests.containsKey(event.getClickedBlock().getLocation())) {

                    Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, "SG-Chest");
                    List<String> strings = SG.getInstance().getConfig().getStringList("items");
                    List<Material> items = Collections.synchronizedList(new ArrayList<>());
                    strings.parallelStream()
                            .forEach(k -> items.add(Material.valueOf(k)));

                    //I must use a index because the ItemStack is only a copy
                    int i = 0, index = 0;
                    for (ItemStack ignored : inventory.getContents()) {
                        ThreadLocalRandom r = ThreadLocalRandom.current();
                        if (r.nextInt(100) < 35 && i < SG.getInstance().getConfig().getInt("maxItemsInChest")) {
                            inventory.setItem(index, new ItemStack(items.get(r.nextInt(items.size()))));
                            i++;
                        }
                        index++;
                    }
                    chests.put(event.getClickedBlock().getLocation(), inventory);
                    player.openInventory(inventory);
                } else {
                    player.openInventory(chests.get(event.getClickedBlock().getLocation()));
                }
                event.setCancelled(true);
            }
        }
    }
}
