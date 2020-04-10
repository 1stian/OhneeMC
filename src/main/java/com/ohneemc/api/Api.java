package com.ohneemc.api;

import com.ohneemc.OhneeMC;
import com.ohneemc.util.UserData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * <p>Api class.</p>
 *
 * @author stian
 * @version $Id: $Id
 */
public class Api {

    /**
     * <p>getAfk.</p>
     *
     * @param player Gets player afk status.
     * @return boolean
     */
    public boolean getAfk(Player player){
        return UserData.getAfk(player);
    }

    /**
     * Get plugin name
     *
     * @return String
     */
    public String getName(){
        return OhneeMC.instance.getDescription().getName();
    }

    /**
     *  Returns plugin authors.
     *
     * @return StringList
     */
    public List<String> getAuthor(){
        return OhneeMC.instance.getDescription().getAuthors();
    }

    /**
     * Returns plugin version.
     *
     * @return String
     */
    public String getVersion(){
        return OhneeMC.instance.getDescription().getVersion();
    }

    /**
     * <p>isImported.</p>
     *
     * @param player Check for who.
     * @return True if imported otherwise false.
     */
    public boolean isImported(Player player){
        return UserData.getImported(player);
    }

    /**
     * <p>setImported.</p>
     *
     * @param player Set imported for player.
     */
    public void setImported(Player player){
        UserData.setImported(player, true);
    }

    /**
     * <p>setHome.</p>
     *
     * @param player Who to set it for
     * @param name Home name
     * @param x a double.
     * @param y a double.
     * @param z a double.
     * @param yaw a float.
     * @param pitch a float.
     * @param worldName a {@link java.lang.String} object.
     */
    public void setHome(Player player, String name, double x, double y, double z, float yaw, float pitch, String worldName) {
        UserData.loadPlayerFile(player);
        UserData.users.set("homes." + name + ".x", x);
        UserData.users.set("homes." + name + ".y", y);
        UserData.users.set("homes." + name + ".z", z);
        UserData.users.set("homes." + name + ".yaw", yaw);
        UserData.users.set("homes." + name + ".pitch", pitch);
        UserData.users.set("homes." + name + ".world", worldName);
        Bukkit.getLogger().info("Saved home from JsonToYaml parse " + name);
        UserData.savePlayerFile(player);
    }
}
