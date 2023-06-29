package de.marcey.serverapi.objects;

import de.marcey.serverapi.events.HostJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Host {

    private UUID uniqueId;
    private Player player;

    public Host(Player p){
        this.player = p;
        this.uniqueId = p.getUniqueId();
    }
    public Player getPlayer() {
        return player;
    }
    public UUID getUniqueId() {
        return uniqueId;
    }
    public boolean isOnServer(){
        return player != null && Bukkit.getOnlinePlayers().contains(player);
    }

    public void setPlayer(Player p) {
        this.uniqueId = p.getUniqueId();
        this.player = p;
    }
}
