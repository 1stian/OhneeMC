package com.ohneemc.util;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * <p>Maps class.</p>
 *
 * @author stian
 * @version $Id: $Id
 */
public class Maps {

    //<editor-fold desc="Last location">
    private static HashMap<UUID, Location> lastPlayerLocation = new HashMap<>();
    private static HashMap<UUID, Location> getLastPlayerLocation() { return lastPlayerLocation; }

    /**
     * <p>updateLastPlayerLocation.</p>
     *
     * @param player a {@link org.bukkit.entity.Player} object.
     */
    public static void updateLastPlayerLocation(Player player) {
        lastPlayerLocation.put(player.getUniqueId(), player.getLocation());
    }

    /**
     * <p>Getter for the field <code>lastPlayerLocation</code>.</p>
     *
     * @param player a {@link org.bukkit.entity.Player} object.
     * @return a {@link org.bukkit.Location} object.
     */
    public static Location getLastPlayerLocation(Player player) {
        Location last = getLastPlayerLocation().get(player.getUniqueId());
        lastPlayerLocation.remove(player.getUniqueId());
        return last;
    }
    //</editor-fold>

    private static HashMap<String, Integer> maxHomes = new HashMap<>();
    /**
     * <p>Getter for the field <code>maxHomes</code>.</p>
     *
     * @return a {@link java.util.HashMap} object.
     */
    public static HashMap<String, Integer> getMaxHomes() { return maxHomes; }

    /**
     * <p>loadGroups.</p>
     */
    public static void loadGroups(){
        maxHomes.clear();
        ConfigurationSection homes = Config.getSection("homes");
        if (homes != null) {
            for (String i : homes.getKeys(false)){
                maxHomes.put(i, Config.getInteger("homes." + i));
            }
        }
    }

    //<editor-fold desc="Teleport">
    //Teleport request maps
    private static HashMap<UUID, Boolean> tpaType = new HashMap<>();
    private static HashMap<UUID, Long> tpRequestTime = new HashMap<>();
    private static HashMap<UUID, UUID> tpRequestTarget = new HashMap<>();

    /**
     * <p>Getter for the field <code>tpaType</code>.</p>
     *
     * @return a {@link java.util.HashMap} object.
     */
    public static HashMap<UUID, Boolean> getTpaType() { return tpaType; }
    /**
     * <p>Getter for the field <code>tpRequestTime</code>.</p>
     *
     * @return a {@link java.util.HashMap} object.
     */
    public static HashMap<UUID, Long> getTpRequestTime() { return tpRequestTime; }
    /**
     * <p>Getter for the field <code>tpRequestTarget</code>.</p>
     *
     * @return a {@link java.util.HashMap} object.
     */
    public static HashMap<UUID, UUID> getTpRequestTarget() { return tpRequestTarget; }


    /**
     * <p>setRequest.</p>
     *
     * @param player a {@link org.bukkit.entity.Player} object.
     * @param target a {@link org.bukkit.entity.Player} object.
     * @param type a {@link java.lang.Boolean} object.
     * @return a boolean.
     */
    public static boolean setRequest(Player player, Player target, Boolean type){
        try {
            tpRequestTime.put(target.getUniqueId(), System.currentTimeMillis());
            tpRequestTarget.put(target.getUniqueId(), player.getUniqueId());
            tpaType.put(player.getUniqueId(), type);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * <p>removeRequest.</p>
     *
     * @param player a {@link org.bukkit.entity.Player} object.
     */
    public static void removeRequest(Player player){
        tpRequestTarget.remove(player.getUniqueId());
        tpRequestTime.remove(player.getUniqueId());
        tpaType.remove(player.getUniqueId());
    }
    //</editor-fold>s

    //<editor-fold desc="Afk maps">
    /** Constant <code>lastAction</code> */
    public static HashMap<UUID, Long> lastAction = new HashMap<>();
    /** Constant <code>isAfk</code> */
    public static HashMap<UUID, Boolean> isAfk = new HashMap<>();
    //</editor-fold>
}
