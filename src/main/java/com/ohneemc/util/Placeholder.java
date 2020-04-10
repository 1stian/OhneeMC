package com.ohneemc.util;

import com.ohneemc.OhneeMC;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

/**
 * <p>Placeholder class.</p>
 *
 * @author stian
 * @version $Id: $Id
 */
public class Placeholder extends PlaceholderExpansion {

    private OhneeMC plugin;

    /**
     * Since we register the expansion inside our own plugin, we
     * can simply use this method here to get an instance of our
     * plugin.
     *
     * @param plugin
     *        The instance of our plugin.
     */
    public Placeholder(OhneeMC plugin){
        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
     *
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     */
    @Override
    public boolean persist(){
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     */
    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * {@inheritDoc}
     *
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>This must be unique and can not contain % or _
     */
    @Override
    public String getIdentifier(){
        return "OhneeMC";
    }

    /**
     * {@inheritDoc}
     *
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * For convienience do we return the version from the plugin.yml
     */
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    /**
     * {@inheritDoc}
     *
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     */
    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "";
        }

        if(identifier.equals("afk")){
            return String.valueOf(UserData.getAfk(player));
        }

        if(identifier.equals("fly")){
            return String.valueOf(UserData.getFly(player));
        }

        if(identifier.equals("gamemode")){
            return String.valueOf(UserData.getGamemode(player));
        }

        if(identifier.equals("homes")){
            return String.valueOf(UserData.getHomes(player));
        }

        if(identifier.equals("warps")){
            return String.valueOf(WarpData.getWarps(player));
        }

        if (identifier.equals("vanished")){
            return String.valueOf(UserData.isVanished(player));
        }
        return null;
    }
}
