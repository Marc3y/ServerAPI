package de.marcey.serverapi.objects;

import de.marcey.serverapi.ServerAPI;
import de.marcey.serverapi.ServerAPIMain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventServer {

    private UUID host;
    private String serviceName;
    private String game;
    private String version;
    private boolean voiceChatRequired;
    private String whitelistState;
    private List<UUID> playerInvites;

    public EventServer(UUID host, String serviceName, String game, String version, boolean voiceChatRequired, String whitelistState, String playerInvites){
        this.host = host;
        this.serviceName = serviceName;
        this.game = game;
        this.version = version;
        this.voiceChatRequired = voiceChatRequired;
        this.whitelistState = whitelistState;
        this.playerInvites = new ArrayList<>();
        if(playerInvites != null) {
            for (String a : playerInvites.split("\\.")) {
                this.playerInvites.add(UUID.fromString(a));
            }
        }
    }

    public UUID getHost() {
        return host;
    }

    public void setHost(UUID host) {
        this.host = host;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isWhitelistOpen(){
        return getWhitelistState().equalsIgnoreCase("OPEN");
    }

    public void setWhitelistState(boolean state){
        setWhitelistState(state ? "OPEN" : "CLOSED");
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isVoiceChatRequired() {
        return voiceChatRequired;
    }

    public void setVoiceChatRequired(boolean voiceChatRequired) {
        this.voiceChatRequired = voiceChatRequired;
    }

    public String getWhitelistState() {
        return whitelistState;
    }

    public void setWhitelistState(String whitelistState) {
        this.whitelistState = whitelistState;
    }

    public List<UUID> getPlayerInvites() {
        return playerInvites;
    }

    public void setPlayerInvites(List<UUID> playerInvites) {
        this.playerInvites = playerInvites;
    }

    public void addPlayerInvite(UUID uuidToAdd){
        this.playerInvites.add(uuidToAdd);
    }

    public String getPlayerInvitesAsString(){
        String value = null;
        for(UUID u : getPlayerInvites()){
            if(value == null){
                value = u.toString();
            } else value = value + "." + u.toString();
        }
        return value;
    }

    public void delete(){
        ServerAPIMain.getInstance().dataProvider.getData().delete(this);
    }

    public void update(){
        ServerAPIMain.getInstance().dataProvider.getData().set(this);
    }
}
