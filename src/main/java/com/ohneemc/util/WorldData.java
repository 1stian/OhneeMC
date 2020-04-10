package com.ohneemc.util;

import com.ohneemc.OhneeMC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * <p>WorldData class.</p>
 *
 * @author stian
 * @version $Id: $Id
 */
public class WorldData  {

    /** Constant <code>world</code> */
    public static FileConfiguration world;
    private static File worldFile;

    /**
     * <p>setSpawn.</p>
     *
     * @param player a {@link org.bukkit.entity.Player} object.
     * @return a boolean.
     */
    public static boolean setSpawn(Player player){
        world = loadWorldFile();
        if (world != null){
            Location spawn = getPlayerLocation(player);
            World playerWorld = spawn.getWorld();
            if (playerWorld != null){
                playerWorld.setSpawnLocation(spawn);
                double x = spawn.getX();
                double y = spawn.getY();
                double z = spawn.getZ();
                float yaw = spawn.getYaw();
                float pitch = spawn.getPitch();
                String worldSpawn = playerWorld.getName();

                world.set(worldSpawn + ".name", worldSpawn);
                world.set(worldSpawn + ".x", x);
                world.set(worldSpawn + ".y", y);
                world.set(worldSpawn + ".z", z);
                world.set(worldSpawn + ".yaw", yaw);
                world.set(worldSpawn + ".pitch", pitch);

                saveWorldFile();
                return true;
            }
        }
        return false;
    }

    /**
     * <p>getSpawn.</p>
     *
     * @param player a {@link org.bukkit.entity.Player} object.
     * @return a {@link org.bukkit.Location} object.
     */
    public static Location getSpawn(Player player){
        world = loadWorldFile();
        if (world != null){
            Location spawn = getPlayerLocation(player);
            World playerWorld = spawn.getWorld();
            if (playerWorld == null){
                return null;
            }
            String worldName = playerWorld.getName();

            double x = world.getDouble(worldName + ".x");
            double y = world.getDouble(worldName + ".y");
            double z = world.getDouble(worldName + ".z");
            float yaw = (float)world.getDouble(worldName + ".yaw");
            float pitch = (float)world.getDouble(worldName + ".pitch");
            String worldSpawn = playerWorld.getName();

            return new Location(Bukkit.getWorld(worldName), x,y,z,yaw,pitch);
        }
        return null;
    }

    //<editor-fold desc="File section">
    /**
     * <p>getPlayerLocation.</p>
     *
     * @param player a {@link org.bukkit.entity.Player} object.
     * @return a {@link org.bukkit.Location} object.
     */
    public static Location getPlayerLocation(Player player) {
        return player.getLocation();
    }

    /**
     * <p>loadWorldFile.</p>
     *
     * @return a {@link org.bukkit.configuration.file.FileConfiguration} object.
     */
    public static FileConfiguration loadWorldFile() {
        String path = OhneeMC.instance.getDataFolder() + "/worlds.yml";
        worldFile = new File(path);

        world = YamlConfiguration.loadConfiguration(worldFile);

        try {
            world.load(worldFile);
            return world;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>saveWorldFile.</p>
     *
     * @return a boolean.
     */
    public static boolean saveWorldFile() {
        String path = OhneeMC.instance.getDataFolder() + "/worlds.yml";
        worldFile = new File(path);

        //users = YamlConfiguration.loadConfiguration(userFile);

        try {
            world.save(worldFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    //</editor-fold>
}
