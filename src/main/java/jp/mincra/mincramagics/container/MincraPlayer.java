package jp.mincra.mincramagics.container;

import java.util.UUID;

public class MincraPlayer {

    private String playerName = "Unknown";
    private UUID playerUUID;

    private float playerMP = 100;
    //1tick毎に減算
    private float playerCooltime = 0;


    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public String getPlayerName(){
        return playerName;
    }

    public void setPlayerUUID(UUID playerUUID){
        this.playerUUID = playerUUID;
    }
    public UUID getPlayerUUID(){
        return playerUUID;
    }

    public void setPlayerMP(float playerMP){
        this.playerMP = playerMP;
    }
    public float getPlayerMP(){
        return playerMP;
    }

    public void setPlayerCooltime(float playerCooltime) {
        this.playerCooltime = playerCooltime;
    }
    public float getPlayerCooltime() {
        return playerCooltime;
    }

}
