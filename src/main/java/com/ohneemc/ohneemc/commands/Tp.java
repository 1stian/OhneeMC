package com.ohneemc.ohneemc.commands;

import com.ohneemc.ohneemc.helpers.Teleport;
import com.ohneemc.ohneemc.util.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Tp {

    //Teleport to a player or someone to a player.
    public static boolean doTp(Player player, String[] args) {
        Player target;

        //Teleports first named player to second.
        if (args.length == 2) {
            Player target2;
            target = Bukkit.getPlayer(args[0]);
            target2 = Bukkit.getPlayer(args[1]);
            if (Teleport.tpPlayerToPlayer(target, target2))
            {
                player.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.GREEN
                        + " has been teleported to " + target2.getName());
                return true;
            }
            return false;
        }

        //Teleports sender to target.
        if (args.length < 2) {
            target = Bukkit.getPlayer(args[0]);
            if (target != null)
                return Teleport.tpPlayerToPlayer(player, target);
        }
        return false;
    }

    //Teleports a player to you.
    public static boolean doTpHere(Player player, String[] args){
        Player target;

        if (args.length == 1){
            target = Bukkit.getPlayer(args[0]);
            if (target != null)
                return Teleport.tpPlayerToPlayer(target, player);
        }
        return false;
    }

    /*Sends a request to another player
     * to be teleported to sender.*/
    public static boolean doTpa(Player player, String[] args){
        Player target;

        if (args.length == 1){
            target = Bukkit.getPlayer(args[0]);
            if (target != null){
                if (Maps.setRequest(player, target, false)){
                    player.sendMessage(ChatColor.GREEN + "You've sent a TPA request to: " + ChatColor.GOLD
                            + target.getName());
                    target.sendMessage(ChatColor.GREEN + "You've received a teleport request from "
                            + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " - Use /tpaccept or /tpdeny");
                    return true;
                }else{
                    player.sendMessage(ChatColor.GREEN
                            + "We couldn't find the player you specified... Please try again.");
                    return true;
                }
            }else{
                return false;
            }
        }
        return false;
    }

    /*Sends a request to another player
     * to be teleported to sender.*/
    public static boolean doTpahere(Player player, String[] args){
        Player target;

        if (args.length == 1){
            target = Bukkit.getPlayer(args[0]);
            if (target != null){
                if (Maps.setRequest(player, target, true)){
                    player.sendMessage(ChatColor.GREEN + "You've sent a TPA here request to: " + ChatColor.GOLD
                            + target.getName());
                    target.sendMessage(ChatColor.GREEN + "You've been asked to be teleported to "
                            + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " - Use /tpaccept or /tpdeny");
                    return true;
                }else{
                    player.sendMessage(ChatColor.GREEN
                            + "We couldn't find the player you specified... Please try again.");
                    return true;
                }
            }else {
                return false;
            }
        }
        return false;
    }

    public static boolean doAccept(Player player){
        if(Maps.getTpRequestTarget().containsKey(player.getUniqueId())){
            Player target = Bukkit.getPlayer(Maps.getTpRequestTarget().get(player.getUniqueId()));
            if (target != null){
                if (Maps.getTpaType().get(target.getUniqueId())){
                    Teleport.tpPlayerToPlayer(player, target);
                    target.sendMessage(ChatColor.GOLD + player.getName()
                            + ChatColor.GREEN + " has accepted your request.");
                }else{
                    Teleport.tpPlayerToPlayer(target, player);
                    target.sendMessage(ChatColor.GOLD + player.getName()
                            + ChatColor.GREEN + " has accepted your request.");
                }
                Maps.removeRequest(player);
                return true;
            }
        }else{
            player.sendMessage(ChatColor.GREEN + "There are no requests to accept.");
            return true;
        }
        return false;
    }

    public static boolean doDeny(Player player){
        if (Maps.getTpRequestTarget().containsKey(player.getUniqueId())){
            Player target = Bukkit.getPlayer(Maps.getTpRequestTarget().get(player.getUniqueId()));
            if (target != null){
                Maps.removeRequest(player);
                player.sendMessage(ChatColor.GREEN + "You've denied the request.");
                target.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " has denied your request.");
                return true;
            }
        }else{
            player.sendMessage(ChatColor.GREEN + "There are no requests to accept.");
            return true;
        }
        return false;
    }

    public static boolean doCancel(Player player){
        if (Maps.getTpRequestTarget().containsKey(player.getUniqueId())){
            Player target = Bukkit.getPlayer(Maps.getTpRequestTarget().get(player.getUniqueId()));
            if (target != null){
                Maps.removeRequest(player);
                player.sendMessage(ChatColor.GREEN+"Teleport request to: " + target.getName() + ChatColor.GREEN+
                        " has been canceled.");
                target.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " has canceled the" +
                        " teleport request.");
                return true;
            }
        }else{
            player.sendMessage(ChatColor.GREEN + "You have not sent any request to a player... Nothing to cancel.");
            return true;
        }
        return false;
    }
}
