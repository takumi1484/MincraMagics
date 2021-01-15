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
    public float getPlayerMP(UUID uuid) {
        return MincraPlayerMap.get(uuid).getPlayerMP_value();
    }
    public void addPlayerMP(UUID uuid, float mp) {
        MincraPlayer mincraPlayer = MincraPlayerMap.get(uuid);
        mp = mp + mincraPlayer.getPlayerMP_value();
        mincraPlayer.setPlayerMP_value(mp);
        MincraPlayerMap.put(uuid,mincraPlayer);
    }

    //Cooltime
    public float getPlayerCooltime(UUID uuid) {
        return MincraPlayerMap.get(uuid).getPlayerCooltime_value();
    }
    public void addPlayerCooltime(UUID uuid, float cooltime) {
        MincraPlayer mincraPlayer = MincraPlayerMap.get(uuid);
        cooltime = cooltime + mincraPlayer.getPlayerCooltime_value();
        mincraPlayer.setPlayerMP_value(cooltime);
        MincraPlayerMap.put(uuid,mincraPlayer);
    }
}
