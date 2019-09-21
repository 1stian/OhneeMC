package com.ohneemc.util;

import com.ohneemc.OhneeMC;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class UserData {

    public static FileConfiguration users;
    public static File userFile;

    //<editor-fold desc="Home Section">

    /**
     * Is player afk or not
     *
     * @param player Player who sets their home.
     * @param name   Home name
     */
    public static boolean setHome(Player player, String[] name) {
        if (name.length == 1) {
            users = loadPlayerFile(player);
            if (users != null) {
                Location location = getPlayerLocation(player);

                World world = location.getWorld();
                if (world == null) {
                    return false;
                }
                String worldName = world.getName();

                double x = location.getX();
                double y = location.getY();
                double z = location.getZ();
                float yaw = location.getYaw();
                float pitch = location.getPitch();

                String homeName = name[0].toLowerCase();

                if (users.contains("homes." + homeName)) {
                    player.sendMessage(ChatColor.GREEN + "You already have a home with that name..");
                    return true;
                } else {
                    users.set("homes." + homeName + ".x", x);
                    users.set("homes." + homeName + ".y", y);
                    users.set("homes." + homeName + ".z", z);
                    users.set("homes." + homeName + ".yaw", yaw);
                    users.set("homes." + homeName + ".pitch", pitch);
                    users.set("homes." + homeName + ".world", worldName);

                    if (savePlayerFile(player)) {
                        player.sendMessage(ChatColor.GOLD + homeName + ChatColor.GREEN + " has now been set!");
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
            player.sendMessage(ChatColor.GREEN + "You need to give a name for your new home!");
            return true;
        }
    }

    /**
     * Getting users
     *
     * @param player prints player users.
     */
    public static boolean getHomes(Player player) {
        users = loadPlayerFile(player);
        if (users != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(ChatColor.GREEN + "Homes: ");

            ConfigurationSection section = users.getConfigurationSection("homes");
            if (section != null) {
                for (String i : section.getKeys(false)) {
                    sb.append(i);
                    sb.append(" ");
                }
            } else {
                player.sendMessage(ChatColor.GREEN + "You've no users yet. Use /sethome <name>");
                return true;
            }

            sb.append(" count: " + getHomeCount(player));
            player.sendMessage(sb.toString());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Is player afk or not
     *
     * @param player Player to teleport.
     * @param args   Home name
     */
    public static boolean tpHome(Player player, String[] args) {
        users = loadPlayerFile(player);
        if (users != null) {
            String name = args[0].toLowerCase();

            if (!users.contains("homes." + name)) {
                player.sendMessage(ChatColor.GREEN + "You don't have a home named " + name);
                return true;
            } else {
                String world = users.getString("homes." + name + ".world");
                double x = users.getInt("homes." + name + ".x");
                double y = users.getInt("homes." + name + ".y");
                double z = users.getInt("homes." + name + ".z");
                float yaw = users.getInt("homes." + name + ".yaw");
                float pitch = users.getInt("homes." + name + ".pitch");

                if (world == null) {
                    player.sendMessage(ChatColor.RED + "Something went wrong while executing home.");
                    return false;
                }

                Location tp = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                player.teleport(tp);
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Is player afk or not
     *
     * @param player Player who wants to delete their home.
     * @param args   Home name
     */
    public static boolean delHome(Player player, String[] args) {
        users = loadPlayerFile(player);
        if (users != null) {
            String name = args[0].toLowerCase();

            ConfigurationSection section = users.getConfigurationSection("homes");
            if (section != null) {
                if (section.contains(name)) {
                    users.set("homes." + name, null);
                    player.sendMessage(ChatColor.GOLD + name + ChatColor.GREEN + " deleted.");

                    try {
                        users.save(OhneeMC.instance.getDataFolder() + "/userdata/" + player.getUniqueId() + ".yml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                } else {
                    player.sendMessage(ChatColor.GREEN + "Couldn't find the home you want to delete.");
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Is player afk or not
     *
     * @param player return players home count.
     */
    private static Integer getHomeCount(Player player) {
        users = loadPlayerFile(player);
        if (users != null) {
            int count = 0;

            ConfigurationSection section = users.getConfigurationSection("homes");
            if (section == null) {
                return 0;
            }
            for (String i : section.getKeys(false)) {
                count++;
            }

            return count;
        } else {
            return 0;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Gamemode">
    //GameMode

    /**
     * Is player afk or not
     *
     * @param player Returns their gamemode
     */
    public static GameMode getGamemode(Player player) {
        return player.getGameMode();
    }

    /**
     * Is player afk or not
     *
     * @param player   Which player to set gamemode for.
     * @param gameMode What gamemode you want to set.
     */
    public static void setGamemode(Player player, GameMode gameMode) {
        users = loadPlayerFile(player);
        player.setGameMode(gameMode);
        player.sendMessage(ChatColor.GREEN + "Your gamemode changed to " + ChatColor.GOLD + gameMode.toString());
        if (users == null){
            return;
        }
        users.set("gamemode", gameMode.toString());
        savePlayerFile(player);
    }
    //</editor-fold>

    //<editor-fold desc="Afk">

    /**
     * Is player afk or not
     *
     * @param player returns true or false if player is afk or not
     */
    public static boolean getAfk(Player player) {
        return Maps.isAfk.get(player.getUniqueId());
    }

    /**
     * Is player afk or not
     *
     * @param player Player you want to put afk.
     * @param afk    Set afk, true or false.
     */
    public static void setAfk(Player player, Boolean afk) {
        Maps.isAfk.put(player.getUniqueId(), afk);
    }
    //</editor-fold>

    //<editor-fold desc="Fly">
    /**
     * Is player afk or not
     *@param player Gets if player have fly enabled or not.
     */
    public static boolean getFly(Player player) {
        users = loadPlayerFile(player);
        if (users == null){
            return false;
        }
        return users.getBoolean("fly");
    }

    /**
     * Is player afk or not
     *
     * @param player Player to set fly for.
     * @param fly   Set fly true or false.
     */
    public static void setFly(Player player, boolean fly) {
        users = loadPlayerFile(player);
        if (users == null){
            return;
        }
        player.setAllowFlight(fly);
        player.setFlying(fly);
        users.set("fly", fly);
        savePlayerFile(player);
    }
    //</editor-fold>

    //<editor-fold desc="File section">
    public static Location getPlayerLocation(Player player) {
        return player.getLocation();
    }

    public static FileConfiguration loadPlayerFile(Player player) {
        String path = OhneeMC.instance.getDataFolder() + "/userdata/" + player.getUniqueId() + ".yml";
        userFile = new File(path);

        users = YamlConfiguration.loadConfiguration(userFile);

        try {
            users.load(userFile);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean savePlayerFile(Player player) {
        String path = OhneeMC.instance.getDataFolder() + "/userdata/" + player.getUniqueId() + ".yml";
        userFile = new File(path);

        //users = YamlConfiguration.loadConfiguration(userFile);

        try {
            users.save(userFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    //</editor-fold>
}
