package jp.mincra.mincramagics.entity.mob;

import jp.mincra.mincramagics.util.ChatUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class MobManager {

    final private List<String> FRIENDLYMOBS = new ArrayList<>(Arrays.asList("PLAYER","HORSE","OCELOT","WOLF","SHEEP","CHICKEN","COW","ITEMFRAME","VILLAGER"));

    private Map<String, JSONObject> entityJsonMap;
    private Map<String, Integer> chanceMap;
    private List<Map.Entry<String, Integer>> sortedChanceMap;

    public List<String> getFriendlyMobs() {
        return FRIENDLYMOBS;
    }
    public Map<String, JSONObject> getEntityJsonMap() {
        return entityJsonMap;
    }
    public Map<String, Integer> getChanceMap() {
        return chanceMap;
    }
    public List<Map.Entry<String, Integer>> getSortedChanceMap() {
        return sortedChanceMap;
    }

    public void register(Map<String, JSONArray> jsonArrayMap) {
        entityJsonMap = new HashMap<>();
        chanceMap = new HashMap<>();

        jsonArrayMap.forEach((k, v) -> {
            registerMob(k,v);
        });

        //chanceMapを昇順に
        sortedChanceMap = new ArrayList<>(chanceMap.entrySet());

        sortedChanceMap.sort(Map.Entry.comparingByValue());
    }

    public void registerMob(String path, JSONArray jsonArray) {

        for (int i=0, len=jsonArray.length(); i<len; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            //id
            if (jsonObject.has("mcr_id") && jsonObject.get("mcr_id") instanceof String) {
                String mcr_id = jsonObject.getString("mcr_id");
                entityJsonMap.put(mcr_id, jsonObject);

                if (jsonObject.has("chance")) {
                    int chance = jsonObject.getInt("chance");
                    chanceMap.put(mcr_id, chance);
                }

            } else {
                ChatUtil.sendConsoleMessage("エラー: " + path + "の" + i + "番目のmcr_idが不正です。");
            }
        }
    }

    public JSONObject getMobNBT(String mcr_id) {
        if (entityJsonMap.get(mcr_id).has("nbt")) {
            return entityJsonMap.get(mcr_id).getJSONObject("nbt");
        }
        return null;
    }
}
