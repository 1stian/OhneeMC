package com.ohneemc.util;

import com.ohneemc.OhneeMC;
import org.bukkit.entity.Player;

/**
 * <p>Vault class.</p>
 *
 * @author stian
 * @version $Id: $Id
 */
public class Vault {

    /**
     * <p>getGroup.</p>
     *
     * @param player a {@link org.bukkit.entity.Player} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getGroup(Player player) {
        return OhneeMC.perms.getPrimaryGroup(player);
    }

    /**
     * <p>getMoney.</p>
     *
     * @param player a {@link org.bukkit.entity.Player} object.
     * @return a double.
     */
    public static double getMoney(Player player) {
        return OhneeMC.econ.getBalance(player);
    }
}
