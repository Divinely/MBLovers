package com.hispanogamers.bomblovers.build;

import com.hispanogamers.bomblovers.utils.Chat;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Players {
    private Player player = null;
    private Teams team = null;
    private GameState state;
    
    public Players(Player player) {
        this.player = player;
    }
    
    public Players(Teams team) {
        this.team = team;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Teams getTeam() {
        return team;
    }
    
    public String getName() {
        return player.getDisplayName();
    }
    
    public String getTeamName() {
        return "";
    }
    
    public void sendMessage(String message) {
        getPlayer().sendMessage(Chat.format(message));
    }
    
    public void SendTeamMessage(String message) {
        getTeam().broadcastMessage(Chat.format(message));
    }
    
    public void SafeTP(Location loc) {
        getTeam().SafeTP(loc);
    }
    
    public enum GameState {
        
    }
}
