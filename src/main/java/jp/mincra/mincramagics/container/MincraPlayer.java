package jp.mincra.mincramagics.container;

import java.util.UUID;

public class MincraPlayer {

    private String playerName = "Unknown";
    private UUID playerUUID;

    private int playerMP;
    //1tick毎に減算
    private int playerCooltime = 0;


    public void setUserName(String playerName) {
        this.playerName = playerName;
    }
    public String getUserName(){
        return playerName;
    }

    public void setPlayerUUID(UUID playerUUID){
        this.playerUUID = playerUUID;
    }
    public UUID getPlayerUUID(){
        return playerUUID;
    }

    public void setPlayerMP(int playerMP){
        this.playerMP = playerMP;
    }
    public int getPlayerMP(){
        return playerMP;
    }

    public void setPlayerCooltime(int playerCooltime) {
        this.playerCooltime = playerCooltime;
    }
    public int getPlayerCooltime() {
        return playerCooltime;
    }
}
