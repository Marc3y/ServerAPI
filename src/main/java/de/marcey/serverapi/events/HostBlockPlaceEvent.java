package de.marcey.serverapi.events;

import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;
import de.marcey.serverapi.objects.Host;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockPlaceEvent;

public class HostBlockPlaceEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Host host;
    private Player player;
    private Block block;
    private BlockPlaceEvent event;

    public HostBlockPlaceEvent(BlockPlaceEvent event){
        this.host = ServerAPI.getInstance().getHost();
        this.player = event.getPlayer();
        this.block = event.getBlock();
        this.event = event;
    }

    public void setCancelled(boolean cancel){
        event.setCancelled(cancel);
    }
    public void setBuild(boolean build){
        event.setBuild(build);
    }
    public Block getBlockAgainst(){
        return event.getBlockAgainst();
    }
    public Block getBlockPlaced(){
        return event.getBlockPlaced();
    }

    public Block getBlock() {
        return block;
    }

    public Host getHost() {
        return host;
    }

    public BlockPlaceEvent getEvent() {
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
