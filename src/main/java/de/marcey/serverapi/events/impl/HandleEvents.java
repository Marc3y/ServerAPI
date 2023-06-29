package de.marcey.serverapi.events.impl;

import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;
import de.marcey.serverapi.events.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class HandleEvents implements Listener {

    private static BukkitTask bukkitRunnable;

    public void onJoin(PlayerJoinEvent e){
        if(!ServerAPI.getInstance().getEventServer().getHost().equals(e.getPlayer().getUniqueId())) return;
        ServerAPI.getInstance().getHost().setPlayer(e.getPlayer());
        HostJoinEvent host = new HostJoinEvent(e);
    }

    public void onQuit(PlayerQuitEvent e){
        if(!ServerAPI.getInstance().getEventServer().getHost().equals(e.getPlayer().getUniqueId())) return;
        HostQuitEvent host = new HostQuitEvent(e);
    }

    public void onMove(PlayerMoveEvent e){
        if(!ServerAPI.getInstance().getEventServer().getHost().equals(e.getPlayer().getUniqueId())) return;
        HostMoveEvent host = new HostMoveEvent(e);
    }

    public void onBlockBreak(BlockBreakEvent e){
        if(!ServerAPI.getInstance().getEventServer().getHost().equals(e.getPlayer().getUniqueId())) return;
        HostBlockBreakEvent host = new HostBlockBreakEvent(e);
    }

    public void onBlockPlace(BlockPlaceEvent e){
        if(!ServerAPI.getInstance().getEventServer().getHost().equals(e.getPlayer().getUniqueId())) return;
        HostBlockPlaceEvent host = new HostBlockPlaceEvent(e);
    }

    public void onItemDrop(EntityDropItemEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if(!ServerAPI.getInstance().getEventServer().getHost().equals(p.getUniqueId())) return;
        HostDropItemEvent host = new HostDropItemEvent(p, e);
    }

    public void onInteract(PlayerInteractEvent e){
        if(!ServerAPI.getInstance().getEventServer().getHost().equals(e.getPlayer().getUniqueId())) return;
        HostInteractEvent host = new HostInteractEvent(e);
    }

    public void onInventoryClick(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        if(!ServerAPI.getInstance().getEventServer().getHost().equals(p.getUniqueId())) return;
        HostInventoryClickEvent host = new HostInventoryClickEvent(p, e);
    }

    public void quitTimer(){
        if(bukkitRunnable != null) bukkitRunnable.cancel();
        bukkitRunnable = new BukkitRunnable(){
            @Override
            public void run() {

            }
        }.runTaskTimer(ServerAPIMain.getInstance(), ServerAPI.getInstance().getSecondsTillServerStops()*20, 60);
    }


}
