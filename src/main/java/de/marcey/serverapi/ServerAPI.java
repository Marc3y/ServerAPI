package de.marcey.serverapi;

import de.dytanic.cloudnet.wrapper.Wrapper;
import de.marcey.serverapi.commands.EventRangCommand;
import de.marcey.serverapi.commands.WhitelistCommand;
import de.marcey.serverapi.events.impl.HandleEvents;
import de.marcey.serverapi.mysql.DataProvider;
import de.marcey.serverapi.mysql.MySQL;
import de.marcey.serverapi.mysql.impl.SQLData;
import de.marcey.serverapi.objects.CloudHelper;
import de.marcey.serverapi.objects.EventServer;
import de.marcey.serverapi.objects.Host;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerAPI {

    private JavaPlugin plugin;
    private boolean whitelistCommand = true;
    private boolean opPlayers = true;
    private int secondsTillServerStops = 60;
    private EventServer eventServer;
    private Host host;
    private String prefix = "§d§lEvents §r§8>>§r";
    private static ServerAPI instance;
    private static boolean isShutdown = false;
    private static List<UUID> ops = new ArrayList<>();

    public ServerAPI(JavaPlugin plugin, String prefix){
        instance = this;
        this.plugin = plugin;
        this.prefix = prefix;
        CloudHelper.init();
        ServerAPIMain.getInstance().dataProvider = new DataProvider();
        ServerAPIMain.getInstance().dataProvider.setSQL(new MySQL());
        ServerAPIMain.getInstance().dataProvider.getSQL().connect();
        if(!ServerAPIMain.getInstance().dataProvider.getSQL().isConnected()){
            Bukkit.getLogger().warning("ERROR: MySQL-Connection konnte nicht hergestellt werden");
        } else {
            ServerAPIMain.getInstance().dataProvider.setData(new SQLData());
            ServerAPIMain.getInstance().dataProvider.getData().createTable();
            eventServer = ServerAPIMain.getInstance().dataProvider.getData().getServerByServiceName(Wrapper.getInstance().getServiceId().getName());
            plugin.getServer().getPluginManager().registerEvents(new HandleEvents(), plugin);
            plugin.getCommand("whitelist").setExecutor(new WhitelistCommand());
            plugin.getCommand("eventrang").setExecutor(new EventRangCommand());
        }
        if(eventServer == null){
            System.out.println("Server shutdown because eventServer is null");
            Bukkit.shutdown();
        }
    }

    public ServerAPI withWhitelistCommand(boolean with){
        whitelistCommand = with;
        return this;
    }

    public ServerAPI withOpPlayers(boolean with){
        opPlayers = with;
        return this;
    }

    public ServerAPI withSecondsTillServerStops(int seconds){
        secondsTillServerStops = seconds;
        return this;
    }

    public ServerAPI withPrefix(String prefix){
        this.prefix = prefix;
        return this;
    }

    public boolean isShutdown() {
        return isShutdown;
    }

    public boolean isOpPlayersEnabled() {
        return opPlayers;
    }

    public boolean isWhitelistCommandEnabled() {
        return whitelistCommand;
    }

    public int getSecondsTillServerStops() {
        return secondsTillServerStops;
    }

    public static ServerAPI getInstance() {
        return instance;
    }

    public EventServer getEventServer() {
        return eventServer;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public void addOp(UUID uuid){
        if(!ops.contains(uuid)) ops.add(uuid);
    }
    public void removeOp(UUID uuid){
        ops.remove(uuid);
    }
    public boolean isOp(UUID uuid){
        return ops.contains(uuid);
    }
    public boolean isOp(Player player){
        return ops.contains(player.getUniqueId());
    }
    public boolean hasOpPermissions(Player p){
        if(ops.contains(p.getUniqueId())) return true;
        return host.getUniqueId().equals(p.getUniqueId());
    }
    public void shutdown(boolean hardShutdown){
        if(hardShutdown){
            isShutdown = true;
            Bukkit.shutdown();
            return;
        }
        if(isShutdown()) return;
        isShutdown = true;
        new BukkitRunnable(){
            int i = 5;
            @Override
            public void run() {
                if(i == 5) {
                    for (Player current : Bukkit.getOnlinePlayers()) {
                        if (current.getUniqueId().equals(ServerAPI.getInstance().getHost().getUniqueId())) continue;
                        current.kickPlayer(ServerAPIMain.getInstance().getPrefix() + " §cDas Event wird nun heruntergefahren.");
                    }
                    if(ServerAPI.getInstance().getHost().getPlayer() != null) {
                        ServerAPI.getInstance().getHost().getPlayer().kickPlayer(ServerAPIMain.getInstance().getPrefix() + " §cDas Event wird nun heruntergefahren.");
                    }
                }
                if(i == 0){
                    Bukkit.shutdown();
                    this.cancel();
                }
                i--;
            }
        }.runTaskTimer(ServerAPIMain.getInstance(), 0, 20);
    }
}
