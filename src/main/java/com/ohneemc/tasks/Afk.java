package com.ohneemc.tasks;

import com.ohneemc.OhneeMC;
import com.ohneemc.util.Config;
import com.ohneemc.util.Maps;
import com.ohneemc.util.UserData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Afk implements Listener {

    private static boolean chatCancel = Config.getBoolean("afk.chat");

    @EventHandler
    private static void onMove(PlayerMoveEvent event) {
        Maps.lastAction.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
        if (UserData.getAfk(event.getPlayer())){
            UserData.setAfk(event.getPlayer(), false);
            OhneeMC.instance.getServer().broadcastMessage(ChatColor.GOLD + event.getPlayer().getName() +
                    ChatColor.GREEN + " is no longer afk");
        }
    }

    @EventHandler
    private static void onChat(AsyncPlayerChatEvent event) {
        if (chatCancel){
            Maps.lastAction.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
            if (UserData.getAfk(event.getPlayer())){
                UserData.setAfk(event.getPlayer(), false);
                OhneeMC.instance.getServer().broadcastMessage(ChatColor.GOLD + event.getPlayer().getName() +
                        ChatColor.GREEN + " is no longer afk");
            }
        }
    }
}
