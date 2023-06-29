package de.marcey.serverapi.objects;

import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.marcey.serverapi.ServerAPIMain;

import java.util.List;

public class CloudHelper {

    private static IPlayerManager playerManager;

    public static void init(){
        playerManager = ServerAPIMain.getDriver().getServicesRegistry().getFirstService(IPlayerManager.class);
    }

    public static List<? extends ICloudPlayer> getOnlinePlayers(){
        List<? extends ICloudPlayer> cloudPlayers = playerManager.getOnlinePlayers();
        return cloudPlayers;
    }

    public static ICloudPlayer getOnlinePlayer(String name){
        List<? extends ICloudPlayer> cloudPlayers = playerManager.getOnlinePlayers(name);
        if(cloudPlayers.isEmpty()) return null;
        return cloudPlayers.get(0);
    }

    public static ServiceInfoSnapshot getService(String serviceName){
        return ServerAPIMain.getDriver().getCloudServiceProvider().getCloudServiceByName(serviceName);
    }


}
