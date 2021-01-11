package jp.mincra.mincramagics.container;

import java.util.UUID;

public class MincraPlayer {

    private String userName = "Unknown";
    private UUID playerUUID;

    private int playerMP;


    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
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
}
