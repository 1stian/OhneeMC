package com.ohneemc.commands;

import com.ohneemc.helpers.MessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class General {

    /**
     *
     * @param player who's sending the command.
     * @param args name of the target player.
     * @return true if success, otherwise false.
     */
    public static boolean setGlowing(Player player, String[] args){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        final Scoreboard board = manager.getNewScoreboard();
        board.registerNewTeam("white");
        Team team = board.getTeam("white");

        if (team == null)
            return false;

        team.setColor(ChatColor.WHITE);

        if (args.length < 1 && player != null){
            if (player.isGlowing()){
                player.setGlowing(false);
                MessageHelper.sendMessage(player, "you're no longer glowing!");
                return true;
            }else{
                player.setScoreboard(board);
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
                    target.setScoreboard(board);
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
