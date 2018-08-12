package com.hispanogamers.bomblovers.build;

import static com.hispanogamers.bomblovers.build.Game.gameState.*;
import com.hispanogamers.bomblovers.main.*;
import com.hispanogamers.bomblovers.utils.Chat;
//import com.hispanogamers.bomblovers.utils.*;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

public class Game {
    private String gamename;
    private int maxPlayers;
    private int minPlayers;
    private World world;
    private World lobbyworld;
    private Set<Location> spawnpoints;
    private int maxTNT;
    private int TNTRegen;
    private Location lobby;
    
    private Set<Players> players;
    private Set<Players> spects;
    private gameState GameState = gameState.Esperando;
    private boolean TG;
    
    public Game(String GameInfo) {
        FileConfiguration GameConfig = Handler.getInstance().getGame();
        this.gamename = GameConfig.getString("games."+GameInfo+".name");
        this.maxPlayers = GameConfig.getInt("games."+GameInfo+".maxPlayers");
        this.minPlayers = GameConfig.getInt("games."+GameInfo+".minPlayers");
        this.world = Bukkit.createWorld(new WorldCreator(GameConfig.getString("games."+GameInfo+".world")));
        this.lobbyworld = Bukkit.getWorld(GameConfig.getString("games."+GameInfo+".lobbyworld"));
        try {
            String[] values = GameConfig.getString("games."+GameInfo+".lobby").split(",");
            double X = Double.parseDouble(values[0].split(":")[1]);
            double Y = Double.parseDouble(values[1].split(":")[1]);
            double Z = Double.parseDouble(values[2].split(":")[1]);
            Location spawnpoint = new Location(lobbyworld, X, Y, Z);
            spawnpoints.add(spawnpoint);
        }
        catch (Exception e) {
            Bukkit.getServer().getLogger().log(Level.SEVERE, "Lobby was not able to set correctly in map {1}\nError: {2}", new Object[]{GameInfo, e});
        }
        for (String point : GameConfig.getStringList("games."+GameInfo+".spawnpoints")) {
            try {
                String[] values = point.split(",");
                double X = Double.parseDouble(values[0].split(":")[1]);
                double Y = Double.parseDouble(values[1].split(":")[1]);
                double Z = Double.parseDouble(values[2].split(":")[1]);
                Location spawnpoint = new Location(world, X, Y, Z);
                spawnpoints.add(spawnpoint);
            }
            catch (Exception e) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "{0} was not able to set correctly in map {1}\nError: {2}", new Object[]{point, GameInfo, e});
            }
        }
    }
    
    public boolean Join(Players player) {
        if (isStated(Esperando) || isStated(Empezando)) {
            if (getPlayers().size() == getMaxPlayers()) {
                player.sendMessage("El juego "+gamename+" está lleno "+players.size()+"/"+getMaxPlayers());
                return false;
            }
            getPlayers().add(player);
            Teams.SafeTP(isStated(Esperando) ? lobby : null);
            GameMessage(player.getName()+" ha entrado a la partida "+players.size()+"/"+getMaxPlayers());
            
            if (getPlayers().size() >= getMinPlayers() && !isStated(Empezando)) {
                setGameState(Empezando);
                GameMessage("El juego empezará en 20 segundos");
                StartCD();
            }
            
            return true;
        }
        else {
            getSpects().add(player);
            return true;
        }
    }
    
    public void StartCD() {
        
    }
    
    public boolean isStated(gameState state) {
        return getGameState() == state;
    }
    
    public void setGameState(gameState state) {
        this.GameState = state;
    }
    
    public gameState getGameState() {
        return GameState;
    }
    
    public String getGameName() {
        return gamename;
    }
    
    public int getMaxPlayers() {
        return maxPlayers;
    }
    
    public int getMinPlayers() {
        return minPlayers;
    }
    
    public Set<Players> getPlayers() {
        return players;
    }
    
    public Set<Players> getSpects() {
        return spects;
    }
    
    public int getTNTregen() {
        return TNTRegen;
    }
    
    public int getMaxTNT() {
        return maxTNT;
    }
    
    public void GameMessage(String message) {
        for (Players player : getPlayers()) {
            player.sendMessage(Chat.format(message));
        }
    }
    
    public enum gameState {
        Esperando, Empezando, Jugando, Finalizando
    }
}
