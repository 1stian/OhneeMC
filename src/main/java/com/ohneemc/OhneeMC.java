package com.ohneemc;

import com.ohneemc.api.Api;
import com.ohneemc.tasks.*;
import com.ohneemc.util.Config;
import com.ohneemc.util.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class OhneeMC extends JavaPlugin {

    public static OhneeMC instance;
    public static Api Api = new Api();
    public static boolean debug = Config.getBoolean("debug");

    public void onDisable(){

    }

    public void onEnable(){
        instance = this;

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new Placeholder(this).register();
        }

        //Save config to disk.
        saveResource("config.yml", false);
        saveResource("warps.yml", false);
        saveResource("worlds.yml", false);
        //Load config
        reloadConfig();
        //Register commands.
        registerCommands();
        //Register listeners.
        registerListeners();
        //Start the checkers.
        startChecker();
    }

    @SuppressWarnings("ConstantConditions")
    private void registerCommands(){
        getCommand("ohnee").setExecutor(new Commands());
        getCommand("tp").setExecutor(new Commands());
        getCommand("tphere").setExecutor(new Commands());
        getCommand("tpa").setExecutor(new Commands());
        getCommand("tpahere").setExecutor(new Commands());
        getCommand("tpaccept").setExecutor(new Commands());
        getCommand("tpdeny").setExecutor(new Commands());
        getCommand("tpcancel").setExecutor(new Commands());
        getCommand("sethome").setExecutor(new Commands());
        getCommand("delhome").setExecutor(new Commands());
        getCommand("home").setExecutor(new Commands());
        getCommand("setwarp").setExecutor(new Commands());
        getCommand("delwarp").setExecutor(new Commands());
        getCommand("warp").setExecutor(new Commands());
        getCommand("setspawn").setExecutor(new Commands());
        getCommand("spawn").setExecutor(new Commands());
        getCommand("gamemode").setExecutor(new Commands());
        getCommand("time").setExecutor(new Commands());
        getCommand("weather").setExecutor(new Commands());
        getCommand("fly").setExecutor(new Commands());
        getCommand("flyspeed").setExecutor(new Commands());
        getCommand("vanish").setExecutor(new Commands());
        getCommand("back").setExecutor(new Commands());
        getCommand("invsee").setExecutor(new Commands());
        getCommand("afk").setExecutor(new Commands());
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new JoinQuit(), this);
        getServer().getPluginManager().registerEvents(new Afk(), this);
        getServer().getPluginManager().registerEvents(new Death(), this);
        getServer().getPluginManager().registerEvents(new Inventory(), this);
    }

    private void startChecker(){
        //Tpa clock.
        Checker.tpRequest();
        //Afk clock.
        Checker.checkAfk();
    }
}
