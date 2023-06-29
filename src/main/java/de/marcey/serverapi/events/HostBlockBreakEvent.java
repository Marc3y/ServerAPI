package de.marcey.serverapi.events;

import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;
import de.marcey.serverapi.objects.Host;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;

public class HostBlockBreakEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Host host;
    private Player player;
    private Block block;
    private BlockBreakEvent event;

    public HostBlockBreakEvent(BlockBreakEvent event){
        this.host = ServerAPI.getInstance().getHost();
        this.player = event.getPlayer();
        this.block = event.getBlock();
        this.event = event;
    }

    public void setCancelled(boolean cancel){
        event.setCancelled(cancel);
    }
    public void setDropItems(boolean drop){
        event.setDropItems(drop);
    }
    public void setExpToDrop(int exp){
        event.setExpToDrop(exp);
    }
    public int getExpToDrop(){
        return event.getExpToDrop();
    }

    public BlockBreakEvent getEvent() {
        return event;
    }

    public Block getBlock() {
        return block;
    }

    public Host getHost() {
        return host;
    }

    public BlockBreakEvent getBlockBreakEvent() {
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
