package com.ohneemc.ohneemc.helpers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageHelper {

    public static void sendMessage(Player player, String message) {
        if (player != null && message != null){
            player.sendMessage(ChatColor.GOLD + "[" + ChatColor.GREEN + "Ohnee" + ChatColor.GOLD + "] " + ChatColor.WHITE + message);
        }
    }
}
