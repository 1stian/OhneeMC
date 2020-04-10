package com.ohneemc.commands;

import com.ohneemc.OhneeMC;
import com.ohneemc.helpers.MessageHelper;
import com.ohneemc.util.Config;
import com.ohneemc.util.UserData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * <p>Admin class.</p>
 *
 * @author stian
 * @version $Id: $Id
 */
public class Admin {

    /**
     * <p>reloadConfig.</p>
     *
     * @return a boolean.
     */
    public static boolean reloadConfig(){
        return Config.reloadConfig();
    }

    /**
     * <p>getVersion.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getVersion(){
        return OhneeMC.instance.getDescription().getVersion();
    }

    /**
     * <p>setWeather.</p>
     *
     * @param player Player to grab the current world.
     * @param args What weather to select. clear | storm | thunder
     * @return true if successful. Otherwise false.
     */
    public static boolean setWeather(Player player, String[] args){
        World world = UserData.getPlayerLocation(player).getWorld();

        if (world != null && args.length > 0){
            String weather = args[0];
            if (weather.equalsIgnoreCase("clear")){
                MessageHelper.sendMessage(player, "Weather set to clear.");
                world.setStorm(false);
                world.setThunderDuration(0);
                return true;
            }else if (weather.equalsIgnoreCase("storm")){
                MessageHelper.sendMessage(player, "Weather set to storm.");
                world.setStorm(true);
                return true;
            }else if(weather.equalsIgnoreCase("thunder") && args.length == 2) {
                int duration = Integer.parseInt(args[1]) * 60 * 1000;
                MessageHelper.sendMessage(player, "Weather set to storm.");
                world.setThunderDuration(duration);
                return true;
            }else{
                player.sendMessage(ChatColor.GREEN + "You must specify what weather you want..");
                player.sendMessage(ChatColor.GREEN + "/weather clear | storm | thunder <duration> <- in minutes");
                return true;
            }
        }else{
            player.sendMessage(ChatColor.GREEN + "You must specify what weather you want..");
            player.sendMessage(ChatColor.GREEN + "/weather clear | storm | thunder <duration> <- in minutes");
            return true;
        }
    }

    /**
     * <p>setTime.</p>
     *
     * @param player Player to grab the current world.
     * @param args What time to select. morning | day | night | midnight
     * @return true if successful. Otherwise false.
     */
    public static boolean setTime(Player player, String[] args){
        World world = UserData.getPlayerLocation(player).getWorld();

        if (world != null && args.length == 1){
            String time = args[0];
            if (time.equalsIgnoreCase("morning")){
                world.setTime(0);
                return true;
            }else if (time.equalsIgnoreCase("day")){
                world.setTime(1000);
                return true;
            }else if(time.equalsIgnoreCase("night")){
                world.setTime(13000);
                return true;
            }else if(time.equalsIgnoreCase("midnight")){
                world.setTime(18000);
                return true;
            }else{
                player.sendMessage(ChatColor.GREEN + "You must specify what time you want..");
                player.sendMessage(ChatColor.GREEN + "/time morning | day | night | midnight");
                return true;
            }
        }else{
            player.sendMessage(ChatColor.GREEN + "You must specify what time you want..");
            player.sendMessage(ChatColor.GREEN + "/time morning | day | night | midnight");
            return true;
        }
    }

    /**
     * <p>getPlayerHomes.</p>
     *
     * @param player Player to list homes to.
     * @param args  Target player to grab homes from.
     * @return Returns a string of homes.
     */
    public static String getPlayerHomes(Player player, String[] args){
        if (player == null && args.length == 0){
            return "Couldn't find the player you wanted...";
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null){
            return "Couldn't find player.";
        }
        return target.getDisplayName() + " homes: " + UserData.getHomes(target);
    }
}
