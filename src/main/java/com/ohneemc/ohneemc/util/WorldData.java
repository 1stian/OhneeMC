package com.ohneemc.ohneemc.util;

import com.ohneemc.ohneemc.OhneeMC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class WorldData  {

    public static FileConfiguration world;
    private static File worldFile;

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
    public static Location getPlayerLocation(Player player) {
        return player.getLocation();
    }

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
