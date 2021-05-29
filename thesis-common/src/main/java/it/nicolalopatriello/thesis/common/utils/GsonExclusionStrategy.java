package it.nicolalopatriello.thesis.common.utils;



import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GsonExclusionStrategy implements ExclusionStrategy {

    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(GsonExcludeField.class) != null;
    }


    public boolean shouldSkipClass(Class clazz) {
        return false;
    }

}