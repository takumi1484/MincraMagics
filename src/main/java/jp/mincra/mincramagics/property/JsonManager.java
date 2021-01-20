package jp.mincra.mincramagics.property;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.IOException;

public class JsonManager {
    private JsonNode itemNode;

    public void loadItemNode() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            itemNode = mapper.readTree(new File("./plugins/MincraMagics/data/items.json"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonNode getItemNode() {
        return itemNode;
    }
}
