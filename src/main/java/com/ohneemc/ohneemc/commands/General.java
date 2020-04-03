package com.ohneemc.ohneemc.commands;

import com.ohneemc.ohneemc.helpers.MessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class General {

    /**
     *
     * @param player who's sending the command.
     * @param args name of the target player.
     * @return true if success, otherwise false.
     */
    public static boolean setGlowing(Player player, String[] args){
        if (args.length < 1 && player != null){
            if (player.isGlowing()){
                player.setGlowing(false);
                MessageHelper.sendMessage(player, "you're no longer glowing!");
                return true;
            }else{
                player.setGlowing(true);
                MessageHelper.sendMessage(player, "you're now glowing!");
                return true;
            }
        }

        if (args.length > 1){
            Player target = Bukkit.getPlayer(args[0]);
            if (player != null && target != null){
                if (target.isGlowing()){
                    target.setGlowing(false);
                    MessageHelper.sendMessage(player, "You ended " + target.getDisplayName() + " glow prettiness!");
                    MessageHelper.sendMessage(target, "you're no longer glowing!");
                    return true;
                }else{
                    target.setGlowing(true);
                    MessageHelper.sendMessage(player, "You made " + target.getDisplayName() + " glow!");
                    MessageHelper.sendMessage(target, "you're now glowing!");
                    return true;
                }
            }
        }

        return false;
    }
}
