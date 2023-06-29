package de.marcey.serverapi.mysql.impl;

import de.marcey.serverapi.mysql.MySQL;
import de.marcey.serverapi.objects.EventServer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLData {

    public void createTable(){
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS server-data (HOST VARCHAR(500), SERVICE TEXT, GAME TEXT, VERSION TEXT, VOICECHAT_REQUIRED TEXT, WHITELIST_STATE TEXT, PLAYER_INVITES TEXT, PRIMARY KEY(HOST))");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isHostExists(UUID uuid){
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT UUID FROM server-data WHERE HOST = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void delete(EventServer eventServer){
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM server-data WHERE HOST = ?");
            ps.setString(1, eventServer.getHost().toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public EventServer set(EventServer server){
        if(server == null || server.getHost() == null) return server;
        if(isHostExists(server.getHost())){
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE server-data SET SERVICE = ? WHERE HOST = ?");
                ps.setString(1, server.getServiceName());
                ps.setString(2, server.getHost().toString());
                ps.executeUpdate();

                ps = MySQL.getConnection().prepareStatement("UPDATE server-data SET GAME = ? WHERE HOST = ?");
                ps.setString(1, server.getGame());
                ps.setString(2, server.getHost().toString());
                ps.executeUpdate();

                ps = MySQL.getConnection().prepareStatement("UPDATE server-data SET VERSION = ? WHERE HOST = ?");
                ps.setString(1, server.getVersion());
                ps.setString(2, server.getHost().toString());
                ps.executeUpdate();

                ps = MySQL.getConnection().prepareStatement("UPDATE server-data SET VOICECHAT_REQUIRED = ? WHERE HOST = ?");
                ps.setBoolean(1, server.isVoiceChatRequired());
                ps.setString(2, server.getHost().toString());
                ps.executeUpdate();

                ps = MySQL.getConnection().prepareStatement("UPDATE server-data SET WHITELIST_STATE = ? WHERE HOST = ?");
                ps.setString(1, server.getWhitelistState());
                ps.setString(2, server.getHost().toString());
                ps.executeUpdate();

                ps = MySQL.getConnection().prepareStatement("UPDATE server-data SET PLAYER_INVITES = ? WHERE HOST = ?");
                ps.setString(1, server.getPlayerInvitesAsString());
                ps.setString(2, server.getHost().toString());
                ps.executeUpdate();

            } catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO server-data (HOST, SERVICE, GAME, VERSION, VOICECHAT_REQUIRED, WHITELIST_STATE, PLAYER_INVITES) VALUES (?,?,?,?,?,?,?)");
                ps.setString(1, server.getHost().toString());
                ps.setString(2, server.getServiceName());
                ps.setString(3, server.getGame());
                ps.setString(4, server.getVersion());
                ps.setBoolean(5, server.isVoiceChatRequired());
                ps.setString(6, server.getWhitelistState());
                ps.setString(7, server.getPlayerInvitesAsString());
                ps.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return server;
    }

    public EventServer getServerByHost(UUID host){
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM server-data WHERE HOST = ?");
            ps.setString(1, host.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return new EventServer(UUID.fromString(rs.getString("HOST")), rs.getString("SERVICE"), rs.getString("GAME"), rs.getString("VERSION"), rs.getBoolean("VOICECHAT_REQUIRED"), rs.getString("WHITELIST_STATE"), rs.getString("PLAYER_INVITES"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public EventServer getServerByServiceName(String serviceName){
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM server-data WHERE SERVICE = ?");
            ps.setString(1, serviceName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return new EventServer(UUID.fromString(rs.getString("HOST")), rs.getString("SERVICE"), rs.getString("GAME"), rs.getString("VERSION"), rs.getBoolean("VOICECHAT_REQUIRED"), rs.getString("WHITELIST_STATE"), rs.getString("PLAYER_INVITES"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<EventServer> getAllEventServers(){
        List<EventServer> list = new ArrayList<>();
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM server-data");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(new EventServer(UUID.fromString(rs.getString("HOST")), rs.getString("SERVICE"), rs.getString("GAME"), rs.getString("VERSION"), rs.getBoolean("VOICECHAT_REQUIRED"), rs.getString("WHITELIST_STATE"), rs.getString("PLAYER_INVITES")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}
