package de.marcey.serverapi.events;

import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;
import de.marcey.serverapi.objects.Host;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class HostInventoryClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Host host;
    private Player player;
    private InventoryClickEvent event;

    public HostInventoryClickEvent(Player p, InventoryClickEvent event){
        this.host = ServerAPI.getInstance().getHost();
        this.player = p;
        this.event = event;
    }

    public Inventory getClickedInventory(){
        return event.getClickedInventory();
    }

    public InventoryView getView(){
        return event.getView();
    }

    public int getSlot(){
        return event.getSlot();
    }

    public ItemStack getCursor(){
        return event.getCursor();
    }

    public void setCancelled(boolean cancel){
        getEvent().setCancelled(cancel);
    }

    public ClickType getClickType(){
        return event.getClick();
    }

    public ItemStack getCurrentItem(){
        return getEvent().getCurrentItem();
    }

    public Host getHost() {
        return host;
    }


    public InventoryClickEvent getEvent() {
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
