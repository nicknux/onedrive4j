package com.nickdsantos.onedrive4j;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * A deserializer which extracts the JSON object from the embedded 'value' value.
 *
 * @author Luke Quinane
 */
public class ResponseValueDeserializer<T> implements JsonDeserializer<T> {

    /**
     * The type of class to read the JSON into.
     */
    private Class<T> _clazz;

    public ResponseValueDeserializer(Class<T> clazz) {
        _clazz = clazz;
    }

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        JsonElement value = jsonElement.getAsJsonObject().get("value");
        return new Gson().fromJson(value, _clazz);
    }
}
