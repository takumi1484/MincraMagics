package jp.mincra.mincramagics.property;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonManager {
    private static JsonNode itemNode;

    public static void readItemNode() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            itemNode = mapper.readTree(new File("./plugins/MincraMagics/data/items.json"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonNode getItemNode() {
        return itemNode;
    }
}
