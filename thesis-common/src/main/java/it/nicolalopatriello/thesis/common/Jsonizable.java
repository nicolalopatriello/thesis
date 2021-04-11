package it.nicolalopatriello.thesis.common;

/**
 * Created by Greta Sasso <greta.sasso@gfmintegration.it> on 12/13/19.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.nicolalopatriello.thesis.common.exception.JsonizableException;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

@Log4j
public class Jsonizable {
    public static final String dateFormat = "dd-MM-yyyy HH:mm:ss:SSS 'Z'";
    private static Gson gson = (new GsonBuilder().setDateFormat(dateFormat)).serializeSpecialFloatingPointValues().create();


//    private static Gson gson = (new GsonBuilder()).serializeSpecialFloatingPointValues().create();


    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <E> E fromJson(String json, Class<E> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static Object fromJson(String json) {
        return gson.fromJson(json, Object.class);
    }

    public static <E> E fromFile(File file, Class<E> clazz) throws JsonizableException {
        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, clazz);
        } catch (Exception e) {
            throw new JsonizableException(e);
        }
    }

    public String toJson() {
        return gson.toJson(this);
    }
}
