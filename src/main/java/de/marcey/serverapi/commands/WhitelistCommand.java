package de.marcey.serverapi.commands;

import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;
import de.marcey.serverapi.objects.CloudHelper;
import de.marcey.serverapi.objects.EventServer;
import de.marcey.serverapi.objects.TabComplete;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WhitelistCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command command, @NotNull String l, @NotNull String[] args) {

        if(!(s instanceof Player)) return false;
        Player p = (Player) s;
        if(!ServerAPI.getInstance().hasOpPermissions(p)) {
            p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §cDu hast keine Brechtigungen um diesen Command auszuführen.");
            return false;
        }
        if(!ServerAPI.getInstance().isWhitelistCommandEnabled()){
            p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §cDieses Feature ist auf diesem Event deaktiviert.");
            return false;
        }
        if(args.length == 0){

            ServerAPI.getInstance().getEventServer().setWhitelistState(!ServerAPI.getInstance().getEventServer().isWhitelistOpen());
            ServerAPI.getInstance().getEventServer().update();
            boolean whitelistOpen = ServerAPI.getInstance().getEventServer().isWhitelistOpen();

            for(Player current : Bukkit.getOnlinePlayers()){
                current.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Es können nun " + (whitelistOpen ? "wieder Spieler joinen." : "keine Spieler mehr joinen."));
            }

            return false;
        } else if(args.length == 2){
            if(args[0].equalsIgnoreCase("add")){
                String name = args[1];
                ICloudPlayer target = CloudHelper.getOnlinePlayer(name);
                if(target == null) {
                    p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Dieser Spieler ist §coffline§7.");
                    return false;
                }
                EventServer server = ServerAPI.getInstance().getEventServer();
                if(server.getPlayerInvites().contains(target.getUniqueId())){
                    p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Dieser Spieler befindet sich bereits auf diesem Event.");
                    return false;
                }
                server.addPlayerInvite(target.getUniqueId());
                server.update();
                p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Du hast den Spieler §e" + target.getName() + " §7erfolgreich zur Whitelist §ahinzugefügt§7.");
            } else if(args[0].equalsIgnoreCase("remove")){
                String name = args[1];
                ICloudPlayer target = CloudHelper.getOnlinePlayer(name);
                if(target == null){
                    p.sendMessage(ServerAPIMain.getDriver() + " §7Der Spieler ist §coffline§7 und kann somit nicht von der Whitelist entfernt werden.");
                    return false;
                }
                EventServer server = ServerAPI.getInstance().getEventServer();
                if(!server.getPlayerInvites().contains(target.getUniqueId())){
                    p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Der Spieler befindet sich §cnicht §7auf der Whitelist.");
                    return false;
                }
                server.getPlayerInvites().remove(target.getUniqueId());
                server.update();
                p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Du hast den Spieler §e" + target.getName() + " §7erfolgreich von der Whitelist §centfernt§7.");
            }
        } else {
            p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Bitten nutze §c/whitelist§7, §c/whitelist <add/remove> <Name>");
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command command, @NotNull String l, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        String input = "";
        if(!(s instanceof Player)) return list;
        Player p = (Player) s;
        if(!ServerAPI.getInstance().hasOpPermissions(p)) {
            p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §cDu hast keine Brechtigungen um diesen Command auszuführen.");
            return list;
        }
        input = args[args.length-1];

        if(args.length == 1){
            list.add("add");
            list.add("remove");
        } else if(args.length == 2){
            if(CloudHelper.getOnlinePlayers().size() <= 80){
                for(ICloudPlayer current : CloudHelper.getOnlinePlayers()){
                    if(current.getName().equalsIgnoreCase(p.getName())) continue;
                    list.add(current.getName());
                }
            }
        }

        return TabComplete.sort(input, list);
    }
}
