package jp.mincra.mincramagics.property;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyManager {

    private Map<String, String> PropertyMap = new HashMap<>();

    public void loadProperty() {
        setProperty();

        Properties properties = new Properties();

        //プロパティファイルのパスを指定する
        String strpass = "./plugins/MincraMagics/mincra.properties";

        try {
            InputStream inputStream = new FileInputStream(strpass);
            properties.load(inputStream);
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        // Mapに格納
        for (Map.Entry<Object, Object> e : properties.entrySet()) {
            PropertyMap.put(e.getKey().toString(), e.getValue().toString());
        }
    }

    public void setProperty() {
        //Property
        File mainDir = new File("./plugins/MincraMagics");
        File properties = new File("./plugins/MincraMagics/mincra.properties");

        //ディレクトリ作成
        if (!mainDir.exists()) {
            mainDir.mkdir();
        }

        if (!properties.exists()){
            try {
                FileWriter fileWriter;
                fileWriter = new FileWriter(properties);
                fileWriter.write("MySQL_url=jdbc:mysql://localhost/mincra\n" +
                        "MySQL_user=root\n" +
                        "MySQL_password=suken314");
                fileWriter.close();
                properties.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Json
        File jsonItemsDir = new File("./plugins/MincraMagics/data/items");
        File jsonItemFile = new File("./plugins/MincraMagics/data/items/example.json");

        if (!jsonItemsDir.exists()) {
            jsonItemsDir.mkdir();
        }

        if (!jsonItemFile.exists()) {
            try {
                FileWriter fileWriter;
                fileWriter = new FileWriter(jsonItemFile);
                fileWriter.write("[\n" +
                        "  {\n" +
                        "    \"id\": \"apple\",\n" +
                        "    \"mcr_id\": \"example\",\n" +
                        "    \"nbt\": {\n" +
                        "      \"display\":{\n" +
                        "        \"Name\": \"{\\\"text\\\":\\\"Example Item\\\",\\\"color\\\":\\\"white\\\",\\\"bold\\\":true,\\\"italic\\\":false}\",\n" +
                        "        \"Lore\":[\n" +
                        "          \"{\\\"text\\\":\\\"This is a example item.\\\",\\\"color\\\":\\\"gray\\\",\\\"italic\\\":false}\"]},\n" +
                        "      \"CustomModelData\":114514,\n" +
                        "      \"MincraMagics\":{\n" +
                        "        \"id\":\"example\"}\n" +
                        "    },\n" +
                        "    \"recipe\": {\n" +
                        "      \"shape\": [\n" +
                        "        \" a \",\n" +
                        "        \"aba\",\n" +
                        "        \" a \"],\n" +
                        "      \"ingredient\": {\n" +
                        "        \"a\": \"example\",\n" +
                        "        \"b\": \"apple\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "]");
                fileWriter.close();
                jsonItemFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public Map<String ,String> getPropertyMap(){
//        return PropertyMap;
//    }

    public String getProperty(String key) {
        return PropertyMap.get(key);
    }
}
