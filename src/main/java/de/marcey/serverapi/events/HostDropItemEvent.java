package de.marcey.serverapi.events;

import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;
import de.marcey.serverapi.objects.Host;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDropItemEvent;

public class HostDropItemEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Host host;
    private Player player;
    private EntityDropItemEvent event;

    public HostDropItemEvent(Player p, EntityDropItemEvent event){
        this.host = ServerAPI.getInstance().getHost();
        this.player = p;
        this.event = event;
    }

    public Host getHost() {
        return host;
    }

    public Player getPlayer() {
        return player;
    }

    public Item getItemDrop(){
        return getEvent().getItemDrop();
    }

    public void setCancelled(boolean cancel){
        getEvent().setCancelled(cancel);
    }

    public EntityDropItemEvent getEvent() {
        return event;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
