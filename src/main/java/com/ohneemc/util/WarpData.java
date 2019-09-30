package com.ohneemc.util;

import com.ohneemc.OhneeMC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class WarpData {

    private static FileConfiguration warps;
    private static File warpFile;

    //<editor-fold desc="Warp Section">
    //Warp Section
    public static boolean setWarp(Player player, String[] args) {
        if (args != null) {
            String name = args[0];
            warps = loadWarpFile();
            if (warps != null) {
                Location location = getPlayerLocation(player);

                String world = location.getWorld().getName();
                double x = location.getX();
                double y = location.getY();
                double z = location.getZ();
                float yaw = location.getYaw();
                float pitch = location.getPitch();

                if (warps.contains(name)) {
                    player.sendMessage(ChatColor.GREEN + "You already have a home with that name..");
                    return true;
                } else {
                    warps.set(name + ".x", x);
                    warps.set(name + ".y", y);
                    warps.set(name + ".z", z);
                    warps.set(name + ".yaw", yaw);
                    warps.set(name + ".pitch", pitch);
                    warps.set(name + ".world", world);

                    if (saveWarpFile()) {
                        player.sendMessage(ChatColor.GOLD + name + ChatColor.GREEN + " has now been set!");
                        return true;
                    } else {
                        player.sendMessage(ChatColor.RED + "Couldn't save your home..");
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            player.sendMessage(ChatColor.RED + "You must specify a warp name. /setwarp <name>");
            return true;
        }
    }

    public static boolean getWarps(Player player) {
        warps = loadWarpFile();
        if (warps != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(ChatColor.GREEN + "Warps: ");

            for (String i : warps.getKeys(false)) {
                sb.append(i);
                sb.append(" ");
            }

            player.sendMessage(sb.toString());
            return true;
        } else {
            return false;
        }
    }

    public static boolean warp(Player player, String[] args) {
        warps = loadWarpFile();
        if (warps != null) {
            String name = args[0].toLowerCase();

            if (!warps.contains(name)) {
                player.sendMessage(ChatColor.GREEN + "There are no warp named " + name);
                return true;
            } else {
                String world = warps.getString(name + ".world");
                double x = warps.getInt(name + ".x");
                double y = warps.getInt(name + ".y");
                double z = warps.getInt(name + ".z");
                float yaw = warps.getInt(name + ".yaw");
                float pitch = warps.getInt(name + ".pitch");

                Location tp = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                player.teleport(tp.add(0,0.2,0));
                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean delWarp(Player player, String[] args) {
        warps = loadWarpFile();
        if (warps != null) {
            String name = args[0].toLowerCase();

            if (warps.contains(name)) {
                warps.set(name, null);
                player.sendMessage(ChatColor.GOLD + name + ChatColor.GREEN + " deleted.");

                try {
                    warps.save(OhneeMC.instance.getDataFolder() + "/warps.yml");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            } else {
                player.sendMessage(ChatColor.GREEN + "Couldn't find the warp you want to delete.");
                return true;
            }
        } else {
            return false;
        }
    }
    //</editor-fold>

    //<editor-fold desc="File section">
    public static Location getPlayerLocation(Player player) {
        return player.getLocation();
    }

    public static FileConfiguration loadWarpFile() {
        String path = OhneeMC.instance.getDataFolder() + "/warps.yml";
        warpFile = new File(path);

        warps = YamlConfiguration.loadConfiguration(warpFile);

        try {
            warps.load(warpFile);
            return warps;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveWarpFile() {
        String path = OhneeMC.instance.getDataFolder() + "/warps.yml";
        warpFile = new File(path);

        try {
            warps.save(warpFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    //</editor-fold>
}
