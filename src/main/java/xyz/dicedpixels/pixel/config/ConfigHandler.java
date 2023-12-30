package xyz.dicedpixels.pixel.config;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.loader.api.FabricLoader;

public class ConfigHandler {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    private final Path path;

    private ConfigHandler(String name) {
        path = CONFIG_DIR.resolve(String.format("%s.json", name));
    }

    public <T> T load(Class<T> type) {
        if (!Files.exists(path)) {
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                throw new RuntimeException("Could not instantiate configuration class", ex);
            }
        }
        try (var reader = Files.newBufferedReader(path)) {
            return GSON.fromJson(reader, type);
        } catch (Exception ex) {
            throw new RuntimeException("Could not deserialize configuration", ex);
        }
    }

    public void save(Object configuration) {
        try (var writer = Files.newBufferedWriter(path)) {
            GSON.toJson(configuration, writer);
        } catch (Exception ex) {
            throw new RuntimeException("Could not serialize configuration", ex);
        }
    }

    public static ConfigHandler of(String name) {
        if (name.isEmpty()) {
            throw new RuntimeException("Empty configuration name");
        }
        return new ConfigHandler(name);
    }
}
