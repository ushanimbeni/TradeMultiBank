package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Map;

public class JsonReader {

    public static List<String> readTopMenuItems() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Use relative path from project root
            File file = new File("src/test/resources/menuitems.json");
            if (!file.exists()) {
                throw new RuntimeException("menuitems.json not found at " + file.getAbsolutePath());
            }

            Map<String, List<String>> map = mapper.readValue(file, Map.class);
            return map.get("topMenuItems");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
