package com.service.category.services;
import com.service.category.entities.ConfigEntity;
import com.service.category.repositories.ConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConfigCacheService {

    private final ConfigRepository configRepository;

    // Map to store multiple values per key
    private final Map<String, List<String>> configCache = new ConcurrentHashMap<>();

    @Autowired
    public ConfigCacheService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

//    @PostConstruct
    public void init() {
        refreshCache();
    }

    public synchronized void refreshCache() {
        List<ConfigEntity> allConfigs = configRepository.findAll();
        configCache.clear();

        for (ConfigEntity config : allConfigs) {
            String key = config.getConfigKey();
            String value = config.getConfigValue();

            configCache.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }

        System.out.println("Multi-value config cache refresh completed.");
    }

    public List<String> getValues(String key) {
        return configCache.getOrDefault(key, Collections.emptyList());
    }
    public Map<String, List<String>> getAllConfigs() {
        return Collections.unmodifiableMap(configCache);
    }
}