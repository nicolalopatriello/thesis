package it.nicolalopatriello.thesis.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.nicolalopatriello.thesis.common.utils.GsonExcludeField;
import it.nicolalopatriello.thesis.common.utils.GsonExclusionStrategy;
import lombok.extern.log4j.Log4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

@Log4j
public abstract class Jsonizable implements Serializable {

    @GsonExcludeField
    protected static final Gson gson = new GsonBuilder()
            .setExclusionStrategies(new GsonExclusionStrategy())
            .serializeSpecialFloatingPointValues()
            .create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public Object fromJson(String json) {
        return gson.fromJson(json, Object.class);
    }

    public static <E> E fromJson(String json, Class<E> clazz) {
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            log.error("Parse exception class=" + clazz + " value=" + json);
            throw e;
        }
    }

    public static <E> E fromJson(Reader reader, Class<E> clazz) {
        return gson.fromJson(reader, clazz);
    }

    public static <E> E fromJson(File f, Class<E> clazz) {
        E value;
        try (Reader r = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8)) {
            value = gson.fromJson(r, clazz);
        } catch (Exception e) {
            throw new FileParseException(e.getMessage(), e);
        }
        return value;
    }

    public static <E> E fromFile(File file, Class<E> clazz) {
        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, clazz);
        } catch (Exception e) {
            throw new FileParseException(e.getMessage(), e);
        }
    }

    public static <E> String jsonOrNull(E e) {
        if (e != null)
            return Jsonizable.toJson(e);
        return null;
    }


    public static void toCompressJson(Object o, OutputStream os) throws IOException {
        try (GZIPOutputStream out = new GZIPOutputStream(os)) {
            String json = gson.toJson(o);
            byte[] dd = json.getBytes();
            out.write(dd);
            out.flush();
        }
    }

    public static String toBase64CompressJson(Object o) throws Base64CompressionException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             GZIPOutputStream out = new GZIPOutputStream(os)) {
            out.write(gson.toJson(o).getBytes(StandardCharsets.UTF_8));
            out.flush();
            final byte[] compressed = os.toByteArray();
            return Base64.getEncoder().encodeToString(compressed);
        } catch (Exception e) {
            throw new Base64CompressionException(e);
        }
    }

    public static class FileParseException extends RuntimeException {
        public FileParseException(String message, Exception e) {
            super(message, e);
        }
    }

    public static class Base64CompressionException extends Exception {
        public Base64CompressionException(Exception e) {
            super(e.getMessage(), e);
        }
    }


}
