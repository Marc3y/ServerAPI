# ServerAPI
Die ServerAPI für die Event-Server auf Kenjih.de

Inizialisierung:
Es darf nur ein ServerAPI Objekt in dem jeweiligen Projekt existieren. Die Inizialisierung benötigt am meisten Leistung also wird empfohlen, die API direkt beim Server-Start zu inizialisieren.
```java
 ServerAPI serverAPI = new ServerAPI(null, "")
                //Optional:
                .withSecondsTillServerStops(60)
                .withOpPlayers(true)
                .withWhitelistCommand(true);
```
Die Methode `withSecondsTillServerStops` ist die Sekunden-Anzahl, bis der Server stoppt nachdem der Host das Event verlassen hat.
Die Methode `withOpPlayers` ist die Boolean ob der Command `/eventrang` aktiviert sein soll.
Die Methode `withWhitelistCommand` ist die Boolean ob der Command `/whitelist` aktiviert sein soll.
Nachdem das Objekt einmal inizialisiert wurde, kann man dies mit `ServerAPI.getInstance()` abrufen.

Event-Server bekommen:
```java
ServerAPI.getInstance().getEventServer();
```

Host bekommen:
```java
ServerAPI.getInstance().getHost();
```

Alle Events:
```java
    @EventHandler
    public void onHostJoin(HostJoinEvent e){}

    @EventHandler
    public void onHostQuit(HostQuitEvent e){}
    
    @EventHandler
    public void onHostMove(HostMoveEvent e){}
    
    @EventHandler
    public void onHostInventoryClick(HostInventoryClickEvent e){}
    
    @EventHandler
    public void onHostInteract(HostInteractEvent e){}
    
    @EventHandler
    public void onHostDropItem(HostDropItemEvent e){}
    
    @EventHandler
    public void onHostBlockPlace(HostBlockPlaceEvent e){}
    
    @EventHandler
    public void onHostBlockBreak(HostBlockBreakEvent e){}
```

Um etwas auf dem EventServer zu updaten:
```java
getEventServer().setWhitelistState(true);
getEventServer().update();
```

Falls der Cloudnet-Driver benötigt wird kann man `ServerAPIMain.getDriver();` benutzen.
