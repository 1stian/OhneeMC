package com.ohneemc;

import com.ohneemc.commands.*;
import com.ohneemc.helpers.InventoryCreator;
import com.ohneemc.helpers.Teleport;
import com.ohneemc.util.Maps;
import com.ohneemc.util.UserData;
import com.ohneemc.util.WarpData;
import com.ohneemc.util.WorldData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = Bukkit.getPlayer(sender.getName());

        if (player != null){
            //<editor-fold desc="Admin commands">
            if (command.getName().equalsIgnoreCase("ohnee") && sender instanceof Player) {
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

            if (command.getName().equalsIgnoreCase("weather") && sender instanceof Player) {
                if (Admin.setWeather(((Player) sender).getPlayer(), args)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing weather.");
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("time") && sender instanceof Player) {
                if (Admin.setTime(((Player) sender).getPlayer(), args)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing time.");
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("fly") && sender instanceof Player) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (UserData.getFly((target))) {
                            UserData.setFly((target), false);
                            return true;
                        } else {
                            UserData.setFly((target), true);
                            return true;
                        }
                    }
                } else if (args.length == 0) {
                    if (UserData.getFly((((Player) sender).getPlayer()))) {
                        UserData.setFly((((Player) sender).getPlayer()), false);
                        return true;
                    } else {
                        UserData.setFly((((Player) sender).getPlayer()), true);
                        return true;
                    }
                }
            }

            if (command.getName().equalsIgnoreCase("flyspeed") && sender instanceof Player) {
                if (args.length == 1) {
                    int speed;
                    try {
                        speed = Integer.parseInt(args[0]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(ChatColor.RED + "Not a number.. /flyspeed 1-3");
                        return true;
                    }
                    float speedFloat = 0.1f;

                    switch (speed) {
                        case 1:
                            speedFloat = +0.1f;
                            break;
                        case 2:
                            speedFloat = +0.2f;
                            break;
                        case 3:
                            speedFloat = +0.3f;
                            break;
                    }

                    UserData.setFlySpeed(((Player) sender).getPlayer(), speedFloat);
                }
            }

            if (command.getName().equalsIgnoreCase("setspawn") && sender instanceof Player) {
                if (WorldData.setSpawn(((Player) sender).getPlayer())){
                    sender.sendMessage(ChatColor.GREEN + "World spawn has been set!");
                    return true;
                }else{
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing setspawn.");
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("vanish") && sender instanceof Player) {
                if (UserData.setVanish(player, null)){
                    return true;
                }else{
                    player.sendMessage(ChatColor.RED + "Something went wrong while executing vanish");
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("invsee") && sender instanceof Player) {
                if (args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null){
                        player.openInventory(InventoryCreator.createInvseeInventory(UserData.getPlayerInventory(target), target.getName()));
                        player.sendMessage(ChatColor.GREEN + "Opened " + ChatColor.GOLD + target.getName() + " " +
                                ChatColor.GREEN + "inventory.");
                        return true;
                    }
                }else{
                    player.sendMessage(ChatColor.GREEN + "Missing target name... /invsee <name>");
                    return true;
                }
            }
            //</editor-fold>

            //<editor-fold desc="General commands">
            if (command.getName().equalsIgnoreCase("playtime") && sender instanceof Player) {
                return false;
            }

            if (command.getName().equalsIgnoreCase("spawn") && sender instanceof Player) {
                Location spawn = WorldData.getSpawn(((Player) sender).getPlayer());
                Maps.updateLastPlayerLocation(player);
                if (spawn != null) {
                    if (Teleport.tpPlayerToLocation(player, spawn.add(0,0.3,0))){
                        return true;
                    }else{
                        sender.sendMessage(ChatColor.RED + "Something went wrong while executing spawn.");
                        return true;
                    }
                }
            }

            if (command.getName().equalsIgnoreCase("back") && sender instanceof Player) {
                Location lastLocation = Maps.getLastPlayerLocation(player);
                if (lastLocation == null){
                    player.sendMessage(ChatColor.GREEN +
                            "You've already used back, there are no location for you to go back to.");
                    return true;
                }
                Teleport.tpPlayerToLocation(player, lastLocation);
                player.sendMessage(ChatColor.GREEN + "You've been teleported to your last location.");
                return true;
            }

            if (command.getName().equalsIgnoreCase("afk") && sender instanceof Player) {
                if (Maps.isAfk.get(player.getUniqueId())){
                    Maps.isAfk.put(player.getUniqueId(), false);
                    OhneeMC.instance.getServer().broadcastMessage(ChatColor.GOLD + player.getName() +
                            ChatColor.GREEN + " is no longer afk");
                    player.setPlayerListName(player.getName());
                    return true;
                }else{
                    Maps.isAfk.put(player.getUniqueId(), true);
                    OhneeMC.instance.getServer().broadcastMessage(ChatColor.GOLD + player.getName() +
                            ChatColor.GREEN + " is now afk.");
                    player.setPlayerListName(player.getName() + ChatColor.GRAY + " | " + ChatColor.ITALIC + "away");
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("kill") && sender instanceof Player) {
                player.setHealth(0);
                player.sendMessage(ChatColor.GREEN + "You killed yourself..");
                Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " chose the easy way out....");
            }
            //</editor-fold>

            //<editor-fold desc="Gamemode">
            //GameMode
            if (command.getName().equalsIgnoreCase("gamemode") && sender instanceof Player) {
                if (args.length == 2) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        if (args[0].equalsIgnoreCase("get")) {
                            sender.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.GREEN + " is in " +
                                    ChatColor.GOLD + UserData.getGamemode(target).toString());
                        } else if (args[0].equalsIgnoreCase("survival") || args[1].equalsIgnoreCase("0")) {
                            UserData.setGamemode(target, GameMode.SURVIVAL);
                        } else if (args[0].equalsIgnoreCase("creative") || args[1].equalsIgnoreCase("1")) {
                            UserData.setGamemode(target, GameMode.CREATIVE);
                        } else if (args[0].equalsIgnoreCase("spectator") || args[1].equalsIgnoreCase("3")) {
                            UserData.setGamemode(target, GameMode.SPECTATOR);
                        } else {
                            sender.sendMessage(ChatColor.RED + "Couldn't recognize any gamemode...");
                            sender.sendMessage(ChatColor.RED + "Try survival (0) | creative (1)");
                        }
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "Couldn't find the player specified..");
                        return true;
                    }
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
                        UserData.setGamemode(((Player) sender).getPlayer(), GameMode.SURVIVAL);
                    } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
                        UserData.setGamemode(((Player) sender).getPlayer(), GameMode.CREATIVE);
                    } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                        UserData.setGamemode(((Player) sender).getPlayer(), GameMode.SPECTATOR);
                    } else {
                        sender.sendMessage(ChatColor.RED + "Couldn't recognize any gamemode...");
                        sender.sendMessage(ChatColor.RED + "Try survival (0) | creative (1)");
                    }
                    return true;
                }
            }
            //</editor-fold>

            //<editor-fold desc="Home commands">
            //Home commands
            if (command.getName().equalsIgnoreCase("sethome") && sender instanceof Player) {
                if (UserData.setHome(((Player) sender).getPlayer(), args)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing sethome.");
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("home") && sender instanceof Player) {
                Maps.updateLastPlayerLocation(player);
                if (args.length == 1 && UserData.tpHome(((Player) sender).getPlayer(), args)) {
                    return true;
                }

                if (args.length < 1 && UserData.getHomes(((Player) sender).getPlayer())) {
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("delhome") && sender instanceof Player) {
                if (UserData.delHome(((Player) sender).getPlayer(), args)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing delhome.");
                    return true;
                }
            }
            //</editor-fold>

            //<editor-fold desc="Warp commands">
            //Warp commands
            if (command.getName().equalsIgnoreCase("setwarp") && sender instanceof Player) {
                if (WarpData.setWarp(((Player) sender).getPlayer(), args)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing setwarp.");
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("warp") && sender instanceof Player) {
                Maps.updateLastPlayerLocation(player);
                if (args.length == 1 && WarpData.warp(((Player) sender).getPlayer(), args)) {
                    return true;
                }

                if (args.length < 1 && WarpData.getWarps(((Player) sender).getPlayer())) {
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("delwarp") && sender instanceof Player) {
                if (WarpData.delWarp(((Player) sender).getPlayer(), args)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing delwarp.");
                    return true;
                }
            }
            //</editor-fold>

            //<editor-fold desc="Teleport commands">
        /*Teleport to a player
        Or another player to someone else.
         */
            if (command.getName().equalsIgnoreCase("tp") && sender instanceof Player) {
                Maps.updateLastPlayerLocation(player);
                if (Tp.doTp(((Player) sender).getPlayer(), args)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing tp.");
                    return true;
                }
            }

            /*Teleport a player to you*/
            if (command.getName().equalsIgnoreCase("tphere") && sender instanceof Player) {
                Maps.updateLastPlayerLocation(player);
                if (Tp.doTpHere(((Player) sender).getPlayer(), args)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing tphere.");
                    return true;
                }
            }

            /*Sends a request to another player
             * to be teleported to sender.*/
            if (command.getName().equalsIgnoreCase("tpa") && sender instanceof Player) {
                Maps.updateLastPlayerLocation(player);
                if (Tp.doTpa(((Player) sender).getPlayer(), args)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing tpa.");
                    return true;
                }
            }

            /*Sends a request to another player
             * to be teleported to sender.*/
            if (command.getName().equalsIgnoreCase("tpahere") && sender instanceof Player) {
                if (Tp.doTpahere(((Player) sender).getPlayer(), args)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing tpa.");
                    return true;
                }
            }

            /*Accepts the incoming
             * teleport request.*/
            if (command.getName().equalsIgnoreCase("tpaccept") && sender instanceof Player) {
                Maps.updateLastPlayerLocation(player);
                if (Tp.doAccept(player)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing tpaccept.");
                    return true;
                }
            }

            /*Denies the incoming
             * teleport request.*/
            if (command.getName().equalsIgnoreCase("tpdeny") && sender instanceof Player) {
                if (Tp.doDeny(player)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing tpdeny.");
                    return true;
                }
            }

            /*Cancels the outgoing
             * teleport request.*/
            if (command.getName().equalsIgnoreCase("tpcancel") && sender instanceof Player) {
                if (Tp.doCancel(player)) {
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Something went wrong while executing tpcancel.");
                    return true;
                }
            }
            //</editor-fold>
        }
        return false;
    }
}
