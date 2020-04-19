package com.ohneemc;

import com.ohneemc.api.Api;
import com.ohneemc.util.Maps;
import com.ohneemc.util.Placeholder;
import com.ohneemc.util.Config;
import com.ohneemc.tasks.*;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * <p>OhneeMC class.</p>
 *
 * @author stian
 * @version $Id: $Id
 */
public class OhneeMC extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private boolean USE_MYSQL;

    /** Constant <code>instance</code> */
    public static OhneeMC instance;
    public com.ohneemc.api.Api api = new Api();
    /** Constant <code>debug=Config.getBoolean("debug")</code> */
    public static boolean debug = Config.getBoolean("debug");

    //Vault
    /** Constant <code>econ</code> */
    public static Economy econ = null;
    /** Constant <code>perms</code> */
    public static Permission perms = null;
    /** Constant <code>chat</code> */
    public static Chat chat = null;

    //DataBase vars.
    String username = null; //Enter in your db username
    String password=null; //Enter your password for the db
    String prefix=null;
    String url = null; //Enter URL w/db name

    //Connection vars
    static Connection connection; //This is the variable we will use to connect to database

    /**
     * <p>onDisable.</p>
     */
    public void onDisable(){
        if (USE_MYSQL)
            disableMysql();
    }

    /**
     * <p>onEnable.</p>
     */
    public void onEnable(){
        instance = this;
        USE_MYSQL = Config.getBoolean("");

        if (USE_MYSQL){
            username=Config.getString("MySql.username");
            password=Config.getString("MySql.password");
            prefix=Config.getString("MySql.prefix");
            url = "jdbc:mysql://"+Config.getString("MySql.host"+"/"+Config.getString("MySql.database"));

            setupMysqlCon();
        }


        //Initiate PlaceholderAPI
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new Placeholder(this).register();
        }else{
            log.severe(String.format("[%s] - Disabled due to no PlaceholderAPI dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
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
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - No Vault economy dependency found!", getDescription().getName()));
            //getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!setupPermissions()) {
            log.severe(String.format("[%s] - No Vault permission dependency found!", getDescription().getName()));
            //getServer().getPluginManager().disablePlugin(this);
            return;
        }
        //Load groups
        Maps.loadGroups();
    }

    private void setupMysqlCon() {
        try { //We use a try catch to avoid errors, hopefully we don't get any.
            Class.forName("com.mysql.jdbc.Driver"); //this accesses Driver in jdbc.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("jdbc driver unavailable!");
            return;
        }
        try { //Another try catch to get any SQL errors (for example connections errors)
            connection = DriverManager.getConnection(url,username,password);
            //with the method getConnection() from DriverManager, we're trying to set
            //the connection's url, username, password to the variables we made earlier and
            //trying to get a connection at the same time. JDBC allows us to do this.
        } catch (SQLException e) { //catching errors)
            e.printStackTrace(); //prints out SQLException errors to the console (if any)
        }
    }

    private void disableMysql(){
        // invoke on disable.
        try { //using a try catch to catch connection errors (like wrong sql password...)
            if (connection!=null && !connection.isClosed()){ //checking if connection isn't null to
                //avoid receiving a nullpointer
                connection.close(); //closing the connection field variable.
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
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
        getCommand("spectate").setExecutor(new Commands());
        getCommand("back").setExecutor(new Commands());
        getCommand("invsee").setExecutor(new Commands());
        getCommand("afk").setExecutor(new Commands());
        getCommand("kill").setExecutor(new Commands());
        getCommand("glow").setExecutor(new Commands());
        getCommand("listhome").setExecutor(new Commands());
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new JoinQuit(), this);
        getServer().getPluginManager().registerEvents(new Afk(), this);
        getServer().getPluginManager().registerEvents(new Death(), this);
        //getServer().getPluginManager().registerEvents(new Inventory(), this);
        getServer().getPluginManager().registerEvents(new WorldChange(), this);
    }

    private void startChecker(){
        //Tpa clock.
        Checker.tpRequest();
        //Afk clock.
        Checker.checkAfk();
    }
}
