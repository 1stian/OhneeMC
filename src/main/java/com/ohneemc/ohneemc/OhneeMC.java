package com.ohneemc.ohneemc;

import com.ohneemc.ohneemc.api.Api;
import com.ohneemc.ohneemc.tasks.*;
import com.ohneemc.ohneemc.util.Maps;
import com.ohneemc.ohneemc.util.Placeholder;
import com.ohneemc.ohneemc.util.Config;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class OhneeMC extends JavaPlugin {

    public static OhneeMC instance;
    public static com.ohneemc.ohneemc.api.Api Api = new Api();
    public static boolean debug = Config.getBoolean("debug");

    //Vault
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;

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
        //Initiate vault
        setupEconomy();
        setupChat();
        setupPermissions();
        //Load groups
        Maps.loadGroups();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
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
        getCommand("kill").setExecutor(new Commands());
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