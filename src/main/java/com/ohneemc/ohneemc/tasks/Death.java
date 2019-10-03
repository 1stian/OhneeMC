package com.ohneemc.ohneemc.tasks;

import com.ohneemc.ohneemc.util.Maps;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Death implements Listener {

    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        if (player == null){
            return;
        }
        Maps.updateLastPlayerLocation(player);
    }
}
