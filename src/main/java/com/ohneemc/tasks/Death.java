package com.ohneemc.tasks;

import com.ohneemc.util.Maps;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * <p>Death class.</p>
 *
 * @author stian
 * @version $Id: $Id
 */
public class Death implements Listener {

    /**
     * <p>onPlayerDeath.</p>
     *
     * @param event a {@link org.bukkit.event.entity.PlayerDeathEvent} object.
     */
    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        if (player == null){
            return;
        }
        Maps.updateLastPlayerLocation(player);
    }
}
