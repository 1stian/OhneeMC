package com.ohneemc.helpers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * <p>MessageHelper class.</p>
 *
 * @author stian
 * @version $Id: $Id
 */
public class MessageHelper {

    /**
     * <p>sendMessage.</p>
     *
     * @param player Player to send message to.
     * @param message The message.
     */
    public static void sendMessage(Player player, String message) {
        if (player != null && message != null){
            player.sendMessage(ChatColor.GOLD + "[" + ChatColor.GREEN + "Ohnee" + ChatColor.GOLD + "] " + ChatColor.WHITE + message);
        }
    }
}
