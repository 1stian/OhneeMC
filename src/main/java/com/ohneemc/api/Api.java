package com.ohneemc.api;

import com.ohneemc.OhneeMC;
import com.ohneemc.util.UserData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class Api {

    /**
     *
     * @param player Gets player afk status.
     * @return boolean
     */
    public boolean getAfk(Player player){
        return UserData.getAfk(player);
    }

    /**
     * Get plugin name
     * @return String
     */
    public String getName(){
        return OhneeMC.instance.getDescription().getName();
    }

    /**
     *  Returns plugin authors.
     * @return StringList
     */
    public List<String> getAuthor(){
        return OhneeMC.instance.getDescription().getAuthors();
    }

    /**
     * Returns plugin version.
     * @return String
     */
    public String getVersion(){
        return OhneeMC.instance.getDescription().getVersion();
    }

    /**
     *
     * @param player Who to set it for
     * @param name Home name
     * @param location Location for home
     */
    public void setHome(Player player, String name, Location location) {
        UserData.loadPlayerFile(player);
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        String world = player.getWorld().getName();

        UserData.users.set("homes." + name + ".x", x);
        UserData.users.set("homes." + name + ".y", y);
        UserData.users.set("homes." + name + ".z", z);
        UserData.users.set("homes." + name + ".yaw", yaw);
        UserData.users.set("homes." + name + ".pitch", pitch);
        UserData.users.set("homes." + name + ".world", world);

        Bukkit.getLogger().info("Saved home from JsonToYaml parse " + name);

        UserData.savePlayerFile(player);
    }
}
