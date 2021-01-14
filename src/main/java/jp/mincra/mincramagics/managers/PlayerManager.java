package jp.mincra.mincramagics.managers;

import jp.mincra.mincramagics.container.MincraPlayer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private ConcurrentHashMap<UUID, MincraPlayer> MincraPlayerMap = new ConcurrentHashMap<>();

    public ConcurrentHashMap<UUID, MincraPlayer> getMincraPlayerMap() {
        return MincraPlayerMap;
    }
    public void putMincraPlayer(MincraPlayer mincraPlayer) {
        MincraPlayerMap.put(mincraPlayer.getPlayerUUID(),mincraPlayer);
    }
    public void removeMincraPlayer(UUID uuid) {
        MincraPlayerMap.remove(uuid);
    }

    //MP
    public int getPlayerMP(UUID uuid) {
        return MincraPlayerMap.get(uuid).getPlayerMP();
    }
    public void addPlayerMP(UUID uuid, int mp) {
        MincraPlayer mincraPlayer = MincraPlayerMap.get(uuid);
        mp = mp + mincraPlayer.getPlayerMP();
        mincraPlayer.setPlayerMP(mp);
        MincraPlayerMap.put(uuid,mincraPlayer);
    }

    //Cooltime
    public int getPlayerCooltime(UUID uuid) {
        return MincraPlayerMap.get(uuid).getPlayerCooltime();
    }
    public void addPlayerCooltime(UUID uuid, int cooltime) {
        MincraPlayer mincraPlayer = MincraPlayerMap.get(uuid);
        cooltime = cooltime + mincraPlayer.getPlayerCooltime();
        mincraPlayer.setPlayerMP(cooltime);
        MincraPlayerMap.put(uuid,mincraPlayer);
    }
}
