package com.mihaia.ecamin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by mihaia on 7/3/2017.
 */

public class CalendarSerializer  implements JsonSerializer<GregorianCalendar> {


    @Override
    public JsonElement serialize(GregorianCalendar src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject obj = new JsonObject();
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        obj.addProperty("Data_Ora", sf.format(src));

        return obj;
    }
}