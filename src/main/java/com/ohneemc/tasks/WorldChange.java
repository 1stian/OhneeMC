package com.ohneemc.tasks;

import com.ohneemc.helpers.MessageHelper;
import com.ohneemc.util.UserData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChange implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void worldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        GameMode gameMode = UserData.getGamemode(player);

        if (gameMode == null){
            MessageHelper.sendMessage(player, "Couldn't determine what gamemode you were in.. If this repeats itself, contact your server admin.");
        }

        if (gameMode == GameMode.CREATIVE){
            player.setGameMode(GameMode.CREATIVE);
            UserData.setFly(player, true);
        }
    }
}
