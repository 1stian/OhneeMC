package com.ohneemc.tasks;

import com.ohneemc.OhneeMC;
import com.ohneemc.util.Config;
import com.ohneemc.util.Maps;
import com.ohneemc.util.UserData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;

import static com.ohneemc.util.UserData.users;

public class JoinQuit implements Listener {

    private String joinMsg = Config.getString("general.join");
    private String quitMsg = Config.getString("general.quit");

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        //Putting players in their maps
        Maps.isAfk.put(event.getPlayer().getUniqueId(), false);
        Maps.lastAction.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());

        //Join message
        ChatColor.translateAlternateColorCodes('§', joinMsg);
        String msg = joinMsg.replaceAll("\\{player}", event.getPlayer().getName());
        event.setJoinMessage(msg);

        //Create UserData file
        String uuid = event.getPlayer().getUniqueId().toString();
        File UserFile = new File(OhneeMC.instance.getDataFolder() +"/userdata/"+uuid+".yml");
        if (!UserFile.exists()){
            try {
                if (UserFile.createNewFile()){
                    Bukkit.getLogger().info("UserFile created for "
                            + event.getPlayer().getName() + "(" + uuid + ")");
                    firstJoin(event.getPlayer());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        normalJoin(event.getPlayer());
    }

    private void firstJoin(Player player){
        users = UserData.loadPlayerFile(player);
        if (users == null){
            return;
        }
        users.set("fly", false);
        users.set("banned", false);
        users.set("muted", false);
        users.set("gamemode", GameMode.SURVIVAL.toString());
        users.set("Timestamps.firstJoined", System.currentTimeMillis());
        users.set("Timestamps.lastSeasonStarted", System.currentTimeMillis());

        UserData.savePlayerFile(player);
    }

    private void normalJoin(Player player){
        users = UserData.loadPlayerFile(player);
        if (users == null){
            return;
        }
        GameMode gamemode = GameMode.valueOf(users.getString("gamemode"));
        if (gamemode == GameMode.CREATIVE){
            player.setFlying(true);
        }
        player.setGameMode(gamemode);

        boolean fly = users.getBoolean("fly");
        if (fly && player.hasPermission("ohnee.safe.fly")){
            UserData.setFly(player, fly);
        }else{
            UserData.setFly(player, false);
        }

        //Updating values
        users.set("Timestamps.lastSeasonStarted", System.currentTimeMillis());

        UserData.savePlayerFile(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        //Removing players from their maps.
        Maps.lastAction.remove(event.getPlayer().getUniqueId());
        Maps.isAfk.remove(event.getPlayer().getUniqueId());

        users = UserData.loadPlayerFile(event.getPlayer());
        if (users == null){
            return;
        }
        users.set("Timestamps.lastSeen", System.currentTimeMillis());

        //Quit message
        ChatColor.translateAlternateColorCodes('§', quitMsg);
        String msg = quitMsg.replaceAll("\\{player}", event.getPlayer().getName());
        event.setQuitMessage(msg);

        UserData.savePlayerFile(event.getPlayer());
    }
}
