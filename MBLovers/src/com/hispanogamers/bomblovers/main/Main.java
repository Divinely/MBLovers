package com.hispanogamers.bomblovers.main;

import com.hispanogamers.bomblovers.build.Game;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main instance;
    private final Set<Game> GameList = new HashSet<>();
    private int GamesPerServer = 0;
    
    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveDefaultConfig();
        
        Bukkit.getServer().getConsoleSender().sendMessage("\n"+
"§d  ____                        _       _                                         \n" +
"§d | __ )    ___    _ __ ___   | |__   | |       ___   __   __   ___   _ __   ___ \n" +
"§d |  _ \\   / _ \\  | '_ ` _ \\  | '_ \\  | |      / _ \\  \\ \\ / /  / _ \\ | '__| / __|\n" +
"§d | |_) | | (_) | | | | | | | | |_) | | |___  | (_) |  \\ V /  |  __/ | |    \\__ \\\n" +
"§d |____/   \\___/  |_| |_| |_| |_.__/  |_____|  \\___/    \\_/    \\___| |_|    |___/\n" +
"§d                                                                                ");
        
        if (getConfig().getString("mode").equalsIgnoreCase("multiarena")) {
            GamesPerServer = 1;
        }
        else if (getConfig().getString("mode").equalsIgnoreCase("bungee")) {
            GamesPerServer = getConfig().getInt("maxGames");
        }
        if (Handler.getInstance().getGame().getConfigurationSection("Games") != null) {
            for (String GameInfo : Handler.getInstance().getGame().getConfigurationSection("Games").getKeys(false)) {
                Game game = new Game(GameInfo);
                this.NewGame(game);
            }
        }
        else {
            Bukkit.getServer().getLogger().warning("No se encontraron juegos para cargar!");
        }
    }
    
    @Override
    public void onDisable() {
        instance = null;
    }
    
    public static Main getInstance() {
        return instance;
    }
    
    public boolean NewGame(Game game) {
        if (GameList.size() == GamesPerServer && GamesPerServer != -1) {
            return false;
        }
        
        GameList.add(game);
        return true;
    }
}
