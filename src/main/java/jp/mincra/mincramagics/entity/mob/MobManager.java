package jp.mincra.mincramagics.entity.mob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MobManager {

    final private List<String> FRIENDLYMOBS = new ArrayList<>(Arrays.asList("PLAYER","HORSE","OCELOT","WOLF","SHEEP","CHICKEN","COW","ITEMFRAME","VILLAGER"));

    public List<String> getFriendlyMobs() {
        return FRIENDLYMOBS;
    }
}
