package de.marcey.serverapi.events;

import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;
import de.marcey.serverapi.objects.Host;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerQuitEvent;

public class HostQuitEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Host host;
    private Player player;
    private PlayerQuitEvent event;

    public HostQuitEvent(PlayerQuitEvent event){
        this.host = ServerAPI.getInstance().getHost();
        this.player = event.getPlayer();
        this.event = event;
    }

    public PlayerQuitEvent getEvent() {
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
