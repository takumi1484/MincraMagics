package jp.mincra.mincramagics.managers;

import jp.mincra.mincramagics.container.MincraPlayer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private ConcurrentHashMap<UUID, MincraPlayer> MincraPlayerUUIDMap = new ConcurrentHashMap<>();

    public void putMincraPlayer(MincraPlayer mincraPlayer) {
        MincraPlayerUUIDMap.put(mincraPlayer.getPlayerUUID(),mincraPlayer);
    }
    public void removeMincraPlayer(UUID uuid) {
        MincraPlayerUUIDMap.remove(uuid);
    }

    //MP
    public int getPlayerMP(UUID uuid) {
        return MincraPlayerUUIDMap.get(uuid).getPlayerMP();
    }
    public void addPlayerMP(UUID uuid, int mp) {
        MincraPlayer mincraPlayer = MincraPlayerUUIDMap.get(uuid);
        mp = mp + mincraPlayer.getPlayerMP();
        mincraPlayer.setPlayerMP(mp);
        MincraPlayerUUIDMap.put(uuid,mincraPlayer);
    }

    //Cooltime
    public int getPlayerCooltime(UUID uuid) {
        return MincraPlayerUUIDMap.get(uuid).getPlayerCooltime();
    }
    public void addPlayerCooltime(UUID uuid, int cooltime) {
        MincraPlayer mincraPlayer = MincraPlayerUUIDMap.get(uuid);
        cooltime = cooltime + mincraPlayer.getPlayerCooltime();
        mincraPlayer.setPlayerMP(cooltime);
        MincraPlayerUUIDMap.put(uuid,mincraPlayer);
    }
}
