package com.ohneemc.helpers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Teleport {

    /**
     *
     * @param player The player you want to teleport to target.
     * @param target The target for player.
     * @return Tru if successful. Otherwise false.
     */
    public static boolean tpPlayerToPlayer(Player player, Player target){
        if (player != null && target != null){
            player.teleport(target);
            return true;
        }
        return false;
    }

    /**
     *
     * @param player The player to teleport.
     * @param location Destination of teleport.
     * @return Tru if successful. Otherwise false.
     */
    public static boolean tpPlayerToLocation(Player player, Location location){
        return player.teleport(location);
    }
}
