package com.ohneemc.ohneemc.tasks;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;

import java.util.ArrayList;
import java.util.List;

public class Inventory implements Listener {

    public static List<String> openInvNames = new ArrayList<>();

    @EventHandler
    public static void invListener(InventoryClickEvent event){
        org.bukkit.inventory.Inventory open = event.getClickedInventory();
        InventoryView title = event.getView();
        String invTitle = title.getTitle();
        String[] split = invTitle.split(" ");
        String playerName = split[1];

        if (open == null) {
            return;
        }

        if (openInvNames.contains(ChatColor.DARK_GREEN + "Invsee " + ChatColor.GOLD + playerName)){
            if (invTitle.equalsIgnoreCase(ChatColor.DARK_GREEN + "Invsee " + ChatColor.GOLD + playerName)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public static void invClose(InventoryCloseEvent event) {
        org.bukkit.inventory.Inventory close = event.getInventory();
        InventoryView title = event.getView();
        String invTitle = title.getTitle();
        String playerName = title.getPlayer().getName();

        if (invTitle == ChatColor.DARK_GREEN + "Invsee "
                + ChatColor.GOLD + playerName){
            openInvNames.remove(ChatColor.DARK_GREEN + "Invsee "
                    + ChatColor.GOLD + playerName);
        }
    }
}
