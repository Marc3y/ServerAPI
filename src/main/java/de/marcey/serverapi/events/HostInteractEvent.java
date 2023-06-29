package de.marcey.serverapi.events;

import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;
import de.marcey.serverapi.objects.Host;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class HostInteractEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Host host;
    private Player player;
    private PlayerInteractEvent playerInteractEvent;

    public HostInteractEvent(PlayerInteractEvent e){
        this.host = ServerAPI.getInstance().getHost();
        this.player = e.getPlayer();
        this.playerInteractEvent = e;
    }

    public PlayerInteractEvent getPlayerInteractEvent() {
        return playerInteractEvent;
    }

    public Action getAction(){
        return getPlayerInteractEvent().getAction();
    }

    public BlockFace getBlockFace(){
        return getPlayerInteractEvent().getBlockFace();
    }

    public Block getClickedBlock(){
        return getPlayerInteractEvent().getClickedBlock();
    }

    public ItemStack getItem(){
        return getPlayerInteractEvent().getItem();
    }

    public EquipmentSlot getEquipmentSlot(){
        return getPlayerInteractEvent().getHand();
    }

    public Material getMaterial(){
        return getPlayerInteractEvent().getMaterial();
    }

    public void setCancelled(boolean cancel){
        getPlayerInteractEvent().setCancelled(cancel);
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
