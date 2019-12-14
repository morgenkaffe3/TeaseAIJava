package me.goddragon.teaseai.api.statistics;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class CustomDeserializer implements JsonDeserializer<List<StatisticsBase>> {

    protected static Map<String, Class<? extends StatisticsBase>> map = new TreeMap<>();

    static {
        map.put("Edge", JavaEdge.class);
        map.put("Response", JavaResponse.class);
        map.put("Module", JavaModule.class);
        map.put("Stroke", JavaStroke.class);
        map.put("StatisticsBase", StatisticsBase.class);
        map.put("EdgeHold", JavaEdgeHold.class);
    }

    public List<StatisticsBase> deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        List<StatisticsBase> list = new ArrayList<>();
        JsonArray ja = json.getAsJsonArray();

        for (JsonElement je : ja) {
            String type = je.getAsJsonObject().get("isA").getAsString();
            Class<? extends StatisticsBase> c = map.get(type);
            if (c == null)
                throw new RuntimeException("Unknow class: " + type);
            list.add(context.deserialize(je, c));
        }

        return list;

    }

}