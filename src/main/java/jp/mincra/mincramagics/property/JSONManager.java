package jp.mincra.mincramagics.property;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.IOException;

public class JSONManager {
    private JsonNode itemNode;

    /**
     * ディレクトリ内の全てのjsonを読み込み、一つの配列で返す
     * @param path 読み込むjsonが配置されているパス。 (e.g. "./plugins/MincraMagics/data/items/")
     */
    public void getAllJSONArray(String path) {

//        StringBuffer directoryBuffer =new StringBuffer("./plugins/MincraMagics");
//        directoryBuffer.append(path);
//        String finalPath = directoryBuffer.toString();
//
//        File directory = new File(finalPath);
        File directory = new File(path);

        /**
         * @param mergedJSONArray 出力するJSON配列
         * @param tempJSONArray それぞれのJSON配列の一時保管
         */

        //ディレクトリ内にファイルが存在するか否か
        if (directory.list().length > 0) {

            JSONArray mergedJSONArray = new JSONArray();
            JSONArray tempJSONArray;

            StringBuffer messageBuffer =new StringBuffer(path);
            messageBuffer.append(" 内のファイルの読み込みを開始します...");
            String message = messageBuffer.toString();

            ChatUtil.sendConsoleMessage(message);

            for (File file : directory.listFiles()) {
                if (isJSONFile(file)) {

                    tempJSONArray = getJSONArray(file.getPath());

//                    MincraMagics.getItemManager().loadItem(tempJSONArray,path);

//                    for (int i=0, len=tempJSONArray.length(); i<len; i++) {
//                        mergedJSONArray.put(tempJSONArray.get(i));
//                    }
                }
            }

//            return mergedJSONArray;

        } else {
            StringBuffer messageBuffer =new StringBuffer(path);
            messageBuffer.append(" 内にファイルが存在しません。");
            String message = messageBuffer.toString();

            ChatUtil.sendConsoleMessage(message);

        }
    }

    public JSONArray getJSONArray(String path) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode jsonNode = mapper.readTree(new File(path));
            JSONArray jsonArray = new JSONArray(jsonNode.toString());

            return jsonArray;

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

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

    /**
     * fileがJsonファイルであるかどうかを調べる
     * @param file
     * @return
     */
    public boolean isJSONFile(File file) {
        return file.isFile() && file.canRead() && file.getPath().endsWith(".json");
    }
}
