package com.nov0cx.sg.utils;

import com.nov0cx.sg.SG;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataConfig {
    private static YamlConfiguration cfg;
    private static File file;

    public DataConfig() {
        file = new File("./plugins/" + SG.getInstance().getName() + "/data.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public static void insert(String key, Object input) {
        cfg.set(key, input);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean constains(String key) {
        return cfg.contains(key);
    }

    public static Object get(String key) {
        return cfg.get(key);
    }

    public static void remove(String key) {
        cfg.getConfigurationSection(key).set(key, null);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
