package de.marcey.serverapi;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.wrapper.Wrapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ServerAPIMain extends JavaPlugin {

    private static ServerAPIMain instance;
    private static String prefix;
    private static final CloudNetDriver DRIVER = CloudNetDriver.getInstance();
    public DataProvider dataProvider;

    @Override
    public void onDisable() {
        disable();
    }

    public static ServerAPIMain getInstance() {
        return instance;
    }

    private void disable(){
        if(dataProvider.getSQL().isConnected()) {
            dataProvider.getData().delete(ServerAPI.getInstance().getEventServer());
            dataProvider.getSQL().disconnect();
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public void setHost(Host host){
        ServerAPI.getInstance().setHost(host);
    }

    public static CloudNetDriver getDriver() {
        return DRIVER;
    }


}
