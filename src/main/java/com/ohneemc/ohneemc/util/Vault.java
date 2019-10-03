package com.ohneemc.ohneemc.util;

import com.ohneemc.ohneemc.OhneeMC;
import org.bukkit.entity.Player;

public class Vault {

    public static String getGroup(Player player) {
        return OhneeMC.perms.getPrimaryGroup(player);
    }

    public static double getMoney(Player player) {
        return OhneeMC.econ.getBalance(player);
    }
}
