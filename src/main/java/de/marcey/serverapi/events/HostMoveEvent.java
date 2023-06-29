package de.marcey.serverapi.events;

import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;
import de.marcey.serverapi.objects.Host;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

public class HostMoveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Host host;
    private Player player;
    private Location from;
    private Location to;
    private PlayerMoveEvent event;

    public HostMoveEvent(PlayerMoveEvent event){
        this.host = ServerAPI.getInstance().getHost();
        this.player = event.getPlayer();
        this.from = event.getFrom();
        this.to = event.getTo();
        this.event = event;
    }

    public void setCancelled(boolean cancel){
        getPlayerMoveEvent().setCancelled(cancel);
    }

    public Host getHost() {
        return host;
    }

    public Location getFrom() {
        return from;
    }

    public PlayerMoveEvent getEvent() {
        return event;
    }

    public Location getTo() {
        return to;
    }

    public PlayerMoveEvent getPlayerMoveEvent() {
        return event;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
