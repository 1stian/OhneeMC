package com.ohneemc.helpers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryCreator {

    public static Inventory createInvseeInventory(Inventory playerInventory, String playerName) {
        com.ohneemc.tasks.Inventory.openInvNames.add(ChatColor.DARK_GREEN + "Invsee "
                + ChatColor.GOLD + playerName);
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.DARK_GREEN + "Invsee "
                + ChatColor.GOLD + playerName);
        ItemStack[] items = playerInventory.getContents();
        inv.setContents(items);
        return inv;
    }
}
