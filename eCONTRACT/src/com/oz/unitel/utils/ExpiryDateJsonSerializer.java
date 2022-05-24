package com.oz.unitel.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class ExpiryDateJsonSerializer extends JsonSerializer<Date> {  
    

    @Override
    public void serialize(Date date, JsonGenerator json, SerializerProvider provider) throws IOException {
        // The customer side will handle presentation, we just want it accurate
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String out = df.format(date);
        json.writeString(out);
    }
}