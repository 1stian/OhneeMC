package com.ohneemc.api;

import com.ohneemc.OhneeMC;
import com.ohneemc.util.UserData;
import org.bukkit.entity.Player;

import java.util.List;

public class Api {

    /**
     *
     * @param player Gets player afk status.
     * @return boolean
     */
    public boolean getAfk(Player player){
        return UserData.getAfk(player);
    }

    /**
     * Get plugin name
     * @return String
     */
    public String getName(){
        return OhneeMC.instance.getDescription().getName();
    }

    /**
     *  Returns plugin authors.
     * @return StringList
     */
    public List<String> getAuthor(){
        return OhneeMC.instance.getDescription().getAuthors();
    }

    /**
     * Returns plugin version.
     * @return String
     */
    public String getVersion(){
        return OhneeMC.instance.getDescription().getVersion();
    }
}
