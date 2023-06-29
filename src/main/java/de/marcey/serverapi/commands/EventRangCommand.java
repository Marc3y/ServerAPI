package de.marcey.serverapi.commands;

import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;
import de.marcey.serverapi.objects.TabComplete;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EventRangCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command command, @NotNull String l, @NotNull String[] args) {

        if(!(s instanceof Player)) return false;
        Player p = (Player) s;
        if(!ServerAPI.getInstance().hasOpPermissions(p)) {
            p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §cDu hast keine Brechtigungen um diesen Command auszuführen.");
            return false;
        }
        if(!ServerAPI.getInstance().isOpPlayersEnabled()){
            p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §cDieses Feature ist auf diesem Event deaktiviert.");
            return false;
        }
        if(args.length == 0){
            p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Dein Rang auf diesem Event ist " + (ServerAPI.getInstance().hasOpPermissions(p) ? "§cOperator" : "§7Spieler") + "§7.");
            return false;
        } else if(args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Der angegebene Spieler §cexistiert nicht§7.");
                return false;
            }
            p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Der Rang von §e" + target.getName() + " §7auf diesem Event ist " + (ServerAPI.getInstance().hasOpPermissions(target) ? "§cOperator" : "§7Spieler") + "§7.");
        } else if(args.length == 2){
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Der angegebene Spieler §cexistiert nicht§7.");
                return false;
            }
            if(!args[1].equalsIgnoreCase("OP") && !args[1].equalsIgnoreCase("Spieler") && !args[1].equalsIgnoreCase("Player") && !args[1].equalsIgnoreCase("Operator")) {
                p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Der angegebene Rang §cexistiert nicht§7.");
                return false;
            }
            if(args[1].toLowerCase().contains("op")){
                if(ServerAPI.getInstance().hasOpPermissions(p)){
                    p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §cDer Spieler ist bereits ein Operator.");
                    return false;
                }
                ServerAPI.getInstance().addOp(target.getUniqueId());
                p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Du hast erfolgreich dem Spieler §e" + target.getName() + " §7den §4Operator§7-Rang gegeben.");
                target.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Du hast den §4Operator§7-Rang erhalten.");
            } else if(args[1].toLowerCase().contains("spieler") || args[1].toLowerCase().contains("player")){
                if(!ServerAPI.getInstance().hasOpPermissions(p)){
                    p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §cDer Spieler ist bereits ein Spieler.");
                    return false;
                }
                ServerAPI.getInstance().removeOp(target.getUniqueId());
                p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Du hast erfolgreich dem Spieler §e" + target.getName() + " §7den Spieler-Rang gegeben.");
                target.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Du hast den Spieler-Rang erhalten.");
            } else {
                p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Bitte nutze §c/eventrang§7, §c/eventrang <Name>§7, §c/eventrang <Name> <Rang>");
            }
        } else p.sendMessage(ServerAPIMain.getInstance().getPrefix() + " §7Bitte nutze §c/eventrang§7, §c/eventrang <Name>§7, §c/eventrang <Name> <Rang>");


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
            list.add("<Name>");
            for(Player current : Bukkit.getOnlinePlayers()){
                if(current.getName().equalsIgnoreCase(p.getName())) continue;
                list.add(current.getName());
            }
        } else if(args.length == 2){
            list.add("OP");
            list.add("Spieler");
        }

        return TabComplete.sort(input, list);
    }
}
