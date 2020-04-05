package com.ohneemc.ohneemc.tasks;

import com.ohneemc.ohneemc.util.UserData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeListener implements Listener {

    @EventHandler
    public void worldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        GameMode gameMode = UserData.getGamemode(player);
        if (gameMode == GameMode.CREATIVE){
            player.setGameMode(GameMode.CREATIVE);
            UserData.setFly(player, true);
        }
    }
}
