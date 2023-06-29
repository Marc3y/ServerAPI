package de.marcey.serverapi.events;

import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;
import de.marcey.serverapi.objects.Host;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;

public class HostJoinEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Host host;
    private Player player;
    private PlayerJoinEvent event;

    public HostJoinEvent(PlayerJoinEvent event){
        this.host = ServerAPI.getInstance().getHost();
        this.player = event.getPlayer();
        this.event = event;
        if(ServerAPI.getInstance().isShutdown()) {
            player.kickPlayer(ServerAPIMain.getInstance().getPrefix() + " §cDas Event wird gerade heruntergefahren.");
            event.setJoinMessage("");
        }
    }

    public String getJoinMessage(){
        return event.getJoinMessage();
    }

    public PlayerJoinEvent getEvent() {
        return event;
    }

    public Host getHost() {
        return host;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
