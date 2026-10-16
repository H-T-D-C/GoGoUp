package com.velters.gogoup;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class GoGoUpConfig {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("gogoup.json");

    private GoGoUpConfig() {
    }

    public static void loadOnStartup() throws IOException {
        if (!Files.exists(CONFIG_PATH)) {
            save(HeightLimit.MAX_BUILD_Y);
        }
        HeightLimit.setMaxBuildY(readMaxBuildY());
    }

    public static void setMaxBuildY(int value) throws IOException {
        validate(value);
        save(value);
        HeightLimit.setMaxBuildY(value);
    }

    public static void reload() throws IOException {
        HeightLimit.setMaxBuildY(readMaxBuildY());
    }

    private static int readMaxBuildY() throws IOException {
        if (!Files.exists(CONFIG_PATH)) {
            throw new IOException("設定ファイルが見つかりません: " + CONFIG_PATH);
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_PATH, StandardCharsets.UTF_8)) {
            JsonElement root = JsonParser.parseReader(reader);
            if (!root.isJsonObject()) {
                throw new IOException("JSONのルートはオブジェクトにしてください。");
            }

            JsonElement element = root.getAsJsonObject().get("maxBuildY");
            if (element == null || !element.isJsonPrimitive()) {
                throw new IOException("maxBuildY に整数を指定してください。");
            }

            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (!primitive.isNumber()) {
                throw new IOException("maxBuildY に整数を指定してください。");
            }

            String text = primitive.getAsString();
            if (!text.matches("-?\\d+")) {
                throw new IOException("maxBuildY に整数を指定してください。");
            }

            try {
                int value = Integer.parseInt(text);
                validate(value);
                return value;
            } catch (NumberFormatException exception) {
                throw new IOException("maxBuildY は整数の範囲を超えています。", exception);
            }
        } catch (JsonParseException exception) {
            throw new IOException("JSONの形式が正しくありません。", exception);
        }
    }

    private static void save(int value) throws IOException {
        validate(value);
        Files.createDirectories(CONFIG_PATH.getParent());
        Path temporary = Files.createTempFile(CONFIG_PATH.getParent(), "gogoup-", ".json.tmp");
        try {
            try (Writer writer = Files.newBufferedWriter(temporary, StandardCharsets.UTF_8)) {
                JsonObject json = new JsonObject();
                json.addProperty("maxBuildY", value);
                writer.write(json.toString());
                writer.write(System.lineSeparator());
            }

            try {
                Files.move(temporary, CONFIG_PATH, StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException exception) {
                Files.move(temporary, CONFIG_PATH, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    private static void validate(int value) throws IOException {
        if (value < HeightLimit.MIN_BUILD_Y || value > HeightLimit.MAX_BUILD_Y) {
            throw new IOException("maxBuildY は " + HeightLimit.MIN_BUILD_Y + "〜" + HeightLimit.MAX_BUILD_Y
                    + " の範囲で指定してください。");
        }
    }
}
