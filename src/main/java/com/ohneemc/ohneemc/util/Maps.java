package com.ohneemc.ohneemc.util;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Maps {

    //<editor-fold desc="Last location">
    private static HashMap<UUID, Location> lastPlayerLocation = new HashMap<>();
    private static HashMap<UUID, Location> getLastPlayerLocation() { return lastPlayerLocation; }

    public static void updateLastPlayerLocation(Player player) {
        lastPlayerLocation.put(player.getUniqueId(), player.getLocation());
    }

    public static Location getLastPlayerLocation(Player player) {
        Location last = getLastPlayerLocation().get(player.getUniqueId());
        lastPlayerLocation.remove(player.getUniqueId());
        return last;
    }
    //</editor-fold>

    private static HashMap<String, Integer> maxHomes = new HashMap<>();
    public static HashMap<String, Integer> getMaxHomes() { return maxHomes; }

    public static void loadGroups(){
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

    public static HashMap<UUID, Boolean> getTpaType() { return tpaType; }
    public static HashMap<UUID, Long> getTpRequestTime() { return tpRequestTime; }
    public static HashMap<UUID, UUID> getTpRequestTarget() { return tpRequestTarget; }


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

    public static void removeRequest(Player player){
        tpRequestTarget.remove(player.getUniqueId());
        tpRequestTime.remove(player.getUniqueId());
        tpaType.remove(player.getUniqueId());
    }
    //</editor-fold>s

    //<editor-fold desc="Afk maps">
    public static HashMap<UUID, Long> lastAction = new HashMap<>();
    public static HashMap<UUID, Boolean> isAfk = new HashMap<>();
    //</editor-fold>
}
