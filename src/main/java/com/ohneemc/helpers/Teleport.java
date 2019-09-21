package com.ohneemc.helpers;

import org.bukkit.entity.Player;

public class Teleport {

    public static boolean tpPlayerToPlayer(Player player, Player target){
        if (player != null && target != null){
            player.teleport(target);
            return true;
        }
        return false;
    }
}
