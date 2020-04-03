package com.ohneemc.ohneemc.commands;

import com.ohneemc.ohneemc.OhneeMC;
import com.ohneemc.ohneemc.helpers.MessageHelper;
import com.ohneemc.ohneemc.util.Config;
import com.ohneemc.ohneemc.util.UserData;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Admin {

    public static boolean reloadConfig(){
        return Config.reloadConfig();
    }

    public static String getVersion(){
        return OhneeMC.instance.getDescription().getVersion();
    }

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
}
