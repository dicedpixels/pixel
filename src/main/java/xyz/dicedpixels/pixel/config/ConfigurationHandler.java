package xyz.dicedpixels.pixel.config;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.loader.api.FabricLoader;

public class ConfigurationHandler {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path DIR = FabricLoader.getInstance().getConfigDir();
    private final Path path;

    private ConfigurationHandler(Path path) {
        this.path = path;
    }

    public <T> T load(Class<T> type) {
        if (!Files.exists(path)) {
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
        try (var reader = Files.newBufferedReader(path)) {
            return GSON.fromJson(reader, type);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void save(Object configuration) {
        try (var writer = Files.newBufferedWriter(path)) {
            GSON.toJson(configuration, writer);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Path path;

        public Builder with(String name) {
            this.path = DIR.resolve(String.format("%s.json", name));
            return this;
        }

        public ConfigurationHandler build() {
            if (path == null) {
                throw new RuntimeException();
            }
            return new ConfigurationHandler(path);
        }
    }
}
