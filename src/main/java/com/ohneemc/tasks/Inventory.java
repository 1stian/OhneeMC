package com.ohneemc.tasks;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Inventory class.</p>
 *
 * @author stian
 * @version $Id: $Id
 */
public class Inventory implements Listener {

    /** Constant <code>openInvNames</code> */
    public static List<String> openInvNames = new ArrayList<>();

    /**
     * <p>invListener.</p>
     *
     * @param event a {@link org.bukkit.event.inventory.InventoryClickEvent} object.
     */
    @EventHandler
    public static void invListener(InventoryClickEvent event){
        org.bukkit.inventory.Inventory open = event.getClickedInventory();
        InventoryView title = event.getView();
        String invTitle = title.getTitle();
        String[] split = title.getTitle().split(" ");

        if (open == null) {
            return;
        }

        if (split.length < 2){
            return;
        }

        String playerName = split[1].trim();

        if (openInvNames.contains(ChatColor.DARK_GREEN + "Invsee " + ChatColor.GOLD + playerName)){
            if (invTitle.equalsIgnoreCase(ChatColor.DARK_GREEN + "Invsee " + ChatColor.GOLD + playerName)) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * <p>invClose.</p>
     *
     * @param event a {@link org.bukkit.event.inventory.InventoryCloseEvent} object.
     */
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
