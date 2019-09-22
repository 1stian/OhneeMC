package com.ohneemc.helpers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Teleport {

    public static boolean tpPlayerToPlayer(Player player, Player target){
        if (player != null && target != null){
            player.teleport(target);
            return true;
        }
        return false;
    }

    public static boolean tpPlayerToLocation(Player player, Location location){
        return player.teleport(location);
    }
}
