package com.hispanogamers.bomblovers.main;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Handler {
    private final File GameFile;
    private final FileConfiguration GameConfig;
    private static final Handler thisinstance = new Handler();
    
    public static Handler getInstance() {
        return thisinstance;
    }
    
    private Handler() {
        this.GameFile = new File(Main.getInstance().getDataFolder(), "games.yml");
        if (!this.GameFile.exists()) {
            try {
                this.GameFile.createNewFile();
            }
            catch (IOException e) {}
        }
        this.GameConfig = YamlConfiguration.loadConfiguration(this.GameFile);
    }
    
    public FileConfiguration getGame() {
        return GameConfig;
    }
    
    public void saveGameFile() {
        try {
            this.GameConfig.save(this.GameFile);
        }
        catch (IOException e) {}
    }
}
