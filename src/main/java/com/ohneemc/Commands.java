package com.ohneemc;

import com.ohneemc.commands.*;
import com.ohneemc.util.UserData;
import com.ohneemc.util.WarpData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //<editor-fold desc="Admin commands">
        if (command.getName().equalsIgnoreCase("ohnee") && sender instanceof Player){
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (Admin.reloadConfig()) {
                        sender.sendMessage(ChatColor.GREEN + "Config successfully reloaded.");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("version")) {
                    sender.sendMessage(ChatColor.GREEN + "OhneeMC version: " + ChatColor.GOLD + Admin.getVersion());
                    return true;
                }
            }
            return false;
        }

        if (command.getName().equalsIgnoreCase("weather") && sender instanceof Player){
            if (Admin.setWeather(((Player) sender).getPlayer(), args)){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing weather.");
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("time") && sender instanceof Player){
            if (Admin.setTime(((Player) sender).getPlayer(), args)){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing time.");
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("fly") && sender instanceof Player){
            if (args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null){
                    if (UserData.getFly((target))){
                        UserData.setFly((target), false);
                        return true;
                    }else{
                        UserData.setFly((target), true);
                        return true;
                    }
                }
            }else if (args.length == 0){
                if (UserData.getFly((((Player) sender).getPlayer()))){
                    UserData.setFly((((Player) sender).getPlayer()), false);
                    return true;
                }else{
                    UserData.setFly((((Player) sender).getPlayer()), true);
                    return true;
                }
            }
        }
        //</editor-fold>

        //<editor-fold desc="Gamemode">
        //GameMode
        if (command.getName().equalsIgnoreCase("gamemode") && sender instanceof Player){
            if (args.length == 2){
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null){
                    if (args[0].equalsIgnoreCase("get")){
                        sender.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.GREEN + " is in " +
                                ChatColor.GOLD + UserData.getGamemode(target).toString());
                    }else if (args[0].equalsIgnoreCase("survival") || args[1].equalsIgnoreCase("0")){
                        UserData.setGamemode(target, GameMode.SURVIVAL);
                    }else if (args[0].equalsIgnoreCase("creative") || args[1].equalsIgnoreCase("1")){
                        UserData.setGamemode(target, GameMode.CREATIVE);
                    }else if (args[0].equalsIgnoreCase("spectator") || args[1].equalsIgnoreCase("3")){
                        UserData.setGamemode(target, GameMode.SPECTATOR);
                    }else {
                        sender.sendMessage(ChatColor.RED + "Couldn't recognize any gamemode...");
                        sender.sendMessage(ChatColor.RED + "Try survival (0) | creative (1)");
                    }
                    return true;
                }else{
                    sender.sendMessage(ChatColor.RED + "Couldn't find the player specified..");
                    return true;
                }
            }else if (args.length == 1){
                if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")){
                    UserData.setGamemode(((Player) sender).getPlayer(), GameMode.SURVIVAL);
                }else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")){
                    UserData.setGamemode(((Player) sender).getPlayer(), GameMode.CREATIVE);
                }else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")){
                    UserData.setGamemode(((Player) sender).getPlayer(), GameMode.SPECTATOR);
                }else {
                    sender.sendMessage(ChatColor.RED + "Couldn't recognize any gamemode...");
                    sender.sendMessage(ChatColor.RED + "Try survival (0) | creative (1)");
                }
                return true;
            }
        }
        //</editor-fold>

        //<editor-fold desc="Home commands">
        //Home commands
        if (command.getName().equalsIgnoreCase("sethome") && sender instanceof Player){
            if (UserData.setHome(((Player) sender).getPlayer(), args)){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing sethome.");
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("home") && sender instanceof Player){
            if (args.length == 1 && UserData.tpHome(((Player) sender).getPlayer(), args)){
                return true;
            }

            if (args.length < 1 && UserData.getHomes(((Player) sender).getPlayer())){
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("delhome") && sender instanceof Player){
            if (UserData.delHome(((Player) sender).getPlayer(), args)){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing delhome.");
                return true;
            }
        }
        //</editor-fold>

        //<editor-fold desc="Warp commands">
        //Warp commands
        if (command.getName().equalsIgnoreCase("setwarp") && sender instanceof Player){
            if (WarpData.setWarp(((Player) sender).getPlayer(), args)){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing setwarp.");
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("warp") && sender instanceof Player){
            if (args.length == 1 && WarpData.warp(((Player) sender).getPlayer(), args)){
                return true;
            }

            if (args.length < 1 && WarpData.getWarps(((Player) sender).getPlayer())){
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("delwarp") && sender instanceof Player){
            if (WarpData.delWarp(((Player) sender).getPlayer(), args)){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing delwarp.");
                return true;
            }
        }
        //</editor-fold>

        //<editor-fold desc="Teleport commands">
        /*Getting random location in a world
        and teleports the player there.
         */
        if (command.getName().equalsIgnoreCase("wild") && sender instanceof Player){
            if (Wild.runWild(((Player) sender).getPlayer())){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing wild.");
                return true;
            }
        }

        /*Teleport to a player
        Or another player to someone else.
         */
        if (command.getName().equalsIgnoreCase("tp") && sender instanceof Player){
            if (Tp.doTp(((Player) sender).getPlayer(), args)){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing tp.");
                return true;
            }
        }

        /*Teleport a player to you*/
        if (command.getName().equalsIgnoreCase("tphere") && sender instanceof Player){
            if (Tp.doTpHere(((Player) sender).getPlayer(), args)){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing tphere.");
                return true;
            }
        }

        /*Sends a request to another player
        * to be teleported to sender.*/
        if (command.getName().equalsIgnoreCase("tpa") && sender instanceof Player){
            if (Tp.doTpa(((Player) sender).getPlayer(), args)){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing tpa.");
                return true;
            }
        }

        /*Sends a request to another player
         * to be teleported to sender.*/
        if (command.getName().equalsIgnoreCase("tpahere") && sender instanceof Player){
            if (Tp.doTpahere(((Player) sender).getPlayer(), args)){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing tpa.");
                return true;
            }
        }

        /*Accepts the incoming
         * teleport request.*/
        if (command.getName().equalsIgnoreCase("tpaccept") && sender instanceof Player){
            if (Tp.doAccept(((Player) sender).getPlayer())){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing tpaccept.");
                return true;
            }
        }

        /*Denies the incoming
         * teleport request.*/
        if (command.getName().equalsIgnoreCase("tpdeny") && sender instanceof Player){
            if (Tp.doDeny(((Player) sender).getPlayer())){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing tpdeny.");
                return true;
            }
        }

        /*Cancels the outgoing
         * teleport request.*/
        if (command.getName().equalsIgnoreCase("tpcancel") && sender instanceof Player){
            if (Tp.doCancel(((Player) sender).getPlayer())){
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Something went wrong while executing tpcancel.");
                return true;
            }
        }
        //</editor-fold>
        return false;
    }
}
