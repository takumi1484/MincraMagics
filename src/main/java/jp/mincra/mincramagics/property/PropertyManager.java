package jp.mincra.mincramagics.property;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyManager {

    private Map<String, String> PropertyMap = new HashMap<>();

    public void loadProperty() {
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
        File dir = new File("./plugins/MincraMagics");
        File properties = new File("./plugins/MincraMagics/mincra.properties");

        //ディレクトリ作成
        if (dir.exists() == false) {
            dir.mkdir();
        }

        if (properties.exists() == false){
            FileWriter fileWriter = null;
            try {
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
    }

//    public Map<String ,String> getPropertyMap(){
//        return PropertyMap;
//    }

    public String getProperty(String key) {
        return PropertyMap.get(key);
    }
}
