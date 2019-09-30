package com.ohneemc.tasks;

import com.ohneemc.OhneeMC;
import com.ohneemc.util.Config;
import com.ohneemc.util.Maps;
import com.ohneemc.util.UserData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.ohneemc.util.UserData.users;

public class JoinQuit implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        //Putting players in their maps
        Maps.isAfk.put(event.getPlayer().getUniqueId(), false);
        Maps.lastAction.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());

        //Join message
        //ChatColor.translateAlternateColorCodes('§', joinMsg);
        String joinMsg = ChatColor.translateAlternateColorCodes('&', Config.getString("general.join"));
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
        users.set("vanished", false);
        users.set("imported", false);
        users.set("flyspeed", 0.1);
        users.set("name: ", player.getName());
        users.set("gamemode", GameMode.SURVIVAL.toString());
        users.set("Timestamps.firstJoined", System.currentTimeMillis());
        users.set("Timestamps.lastSeasonStarted", System.currentTimeMillis());

        giveStarterKit(player);

        player.sendMessage(ChatColor.GOLD + "------------" + ChatColor.BOLD + "Server guides" + ChatColor.GOLD + "-----------");
        player.sendMessage(ChatColor.GOLD + "----- " + ChatColor.GREEN + "https://ohneemc.com/get-started/" + ChatColor.GOLD + " -----");
        player.sendMessage(ChatColor.GOLD + "-------------------------------------");

        UserData.savePlayerFile(player);
    }

    private void giveStarterKit(Player player){
        boolean give = Config.getBoolean("kits.givestarter");
        List<String> items = Config.getList("kits.starter");
        if (give){
            for (String i : items){
                String[] split = i.split(":");
                String iName = split[0];
                int iAmount = Integer.valueOf(split[1]);
                Material item = Material.matchMaterial(iName);
                ItemStack stack;
                if (item != null){
                    stack = new ItemStack(item, iAmount);
                    player.getInventory().addItem(stack);
                }else{
                    Bukkit.getLogger().warning("[Ohnee] There was a problem with the starter kit... Unrecognized item..");
                }
            }
            Bukkit.getLogger().info(player.getName() + " received the starter kit.");
        }
    }

    private void normalJoin(Player player){
        users = UserData.loadPlayerFile(player);
        if (users == null){
            return;
        }

        boolean fly = users.getBoolean("fly");
        if (fly){
            UserData.setFly(player, fly);
        }else{
            UserData.setFly(player, false);
        }

        boolean vanished = users.getBoolean("vanished");
        if (vanished){
            UserData.setVanish(player, vanished);
        }else{
            UserData.setVanish(player, vanished);
        }

        GameMode gamemode = GameMode.valueOf(users.getString("gamemode"));
        if (gamemode == GameMode.CREATIVE){
            UserData.setFly(player, true);
            player.setFlying(true);
        }
        player.setGameMode(gamemode);

        float flySpeed = UserData.getFlySpeed(player);
        player.setFlySpeed(flySpeed);

        //Updating values
        users.set("Timestamps.lastSeasonStarted", System.currentTimeMillis());
        users.set("name: ", player.getName());

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
        //ChatColor.translateAlternateColorCodes('§', quitMsg);
        String quitMsg = ChatColor.translateAlternateColorCodes('&', Config.getString("general.quit"));
        String msg = quitMsg.replaceAll("\\{player}", event.getPlayer().getName());
        event.setQuitMessage(msg);

        UserData.savePlayerFile(event.getPlayer());
    }
}
