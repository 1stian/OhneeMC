package com.ohneemc.tasks;

import com.ohneemc.OhneeMC;
import com.ohneemc.util.Config;
import com.ohneemc.util.Maps;
import com.ohneemc.util.UserData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * <p>Checker class.</p>
 *
 * @author stian
 * @version $Id: $Id
 */
public class Checker {

    private static int tpRequestTimeout = Config.getInteger("teleport.timeout") * 1000;
    private static int afkTime = Config.getInteger("afk.time") * 60 * 1000;

    //Check for invalid tpa requests.
    /**
     * <p>tpRequest.</p>
     */
    public static void tpRequest() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(OhneeMC.instance, () -> {
            for (UUID id : Maps.getTpRequestTarget().keySet()) {
                if (Maps.getTpRequestTarget().containsKey(id) && Maps.getTpRequestTime().containsKey(id)) {
                    UUID targetId = Maps.getTpRequestTarget().get(id);
                    Long timeLeft = Maps.getTpRequestTime().get(id);

                    Player player = Bukkit.getPlayer(id);
                    Player target = Bukkit.getPlayer(targetId);

                    long passed = System.currentTimeMillis() - tpRequestTimeout;

                    if (timeLeft < passed) {
                        if (player != null && target != null) {
                            target.sendMessage(ChatColor.GREEN + "Request to " + ChatColor.GOLD + player.getName()
                                    + ChatColor.GREEN + " has timed out.");
                            player.sendMessage(ChatColor.GREEN + "Request from " + ChatColor.GOLD + target.getName()
                                    + ChatColor.GREEN + " has timed out.");
                            Maps.removeRequest(player);
                        }
                    }
                }
            }
        }, 40L, 20L);
    }

    //Afk timer.
    /**
     * <p>checkAfk.</p>
     */
    public static void checkAfk(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(OhneeMC.instance, () -> {
            for (Player player : Bukkit.getOnlinePlayers()){
                UUID id = player.getUniqueId();
                if (Maps.lastAction.containsKey(id)){
                    long oldTime = Maps.lastAction.get(id);
                    long currentTime = System.currentTimeMillis() - afkTime;
                    if (oldTime < currentTime){
                        if (!Maps.isAfk.get(id)){
                            UserData.setAfk(player, true);
                            player.setPlayerListName(player.getName() + ChatColor.GRAY + " | " + ChatColor.ITALIC + "away");
                            OhneeMC.instance.getServer().broadcastMessage(ChatColor.GOLD + player.getName() +
                                    ChatColor.GREEN + " is now afk.");
                        }
                    }
                }
            }
        },40L, 100L);
    }
}
