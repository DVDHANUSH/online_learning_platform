package com.service.course.services;
        import com.service.course.dto.CustomPropConfigDto;
        import jakarta.annotation.PostConstruct;
        import java.util.*;
        import java.util.concurrent.ConcurrentHashMap;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomPropConfigManager {

    @Autowired
    private CustomPropConfigService customPropConfigService;

    // In-memory property map cache: configId -> key -> value
    private final Map<String, Map<String, String>> propertyMap = new ConcurrentHashMap<>();
    @PostConstruct
    public void init() {
        try {
            log.info("Initializing CustomPropConfigManager...");
            refreshPropertyMap();
            log.info("Initialization successful.");
        } catch (Exception e) {
            log.error("Failed to initialize CustomPropConfigManager", e);
            throw e; // re-throw so Spring knows it's fatal
        }
    }
    public void refreshPropertyMap() {
        log.info("Refreshing property map cache from DB");
        propertyMap.clear();
        List<CustomPropConfigDto> configDtos = customPropConfigService.getAllConfigProperties();
        if (configDtos == null || configDtos.isEmpty()) {
            log.warn("No config properties found in DB.");
            return;
        }

        for (CustomPropConfigDto dto : configDtos) {
            if (dto.getConfigId() == null || dto.getProperties() == null) {
                log.warn("Skipping invalid config: {}", dto);
                continue;
            }
            propertyMap.put(dto.getConfigId(), dto.getProperties());
        }

        log.info("Property map cache loaded with {} configs", propertyMap.size());
    }
    public Map<String, String> getPropertiesByConfigId(String configId) {
        return propertyMap.getOrDefault(configId, Collections.emptyMap());
    }
    public void saveProperty(String configId, String key, String value) {
        log.info("=== Property Map BEFORE DB update ===");
        printPropertyMap();

        // Save to DB
        CustomPropConfigDto dto = new CustomPropConfigDto();
        dto.setConfigId(configId);
        dto.setProperties(Map.of(key, value));
        customPropConfigService.updateOrSave(List.of(dto));

        // Refresh in-memory map
        refreshPropertyMap();

        log.info("=== Property Map AFTER DB update ===");
        printPropertyMap();
    }
    private void printPropertyMap() {
        if (propertyMap.isEmpty()) {
            log.info("Property map is currently empty.");
        } else {
            for (Map.Entry<String, Map<String, String>> entry : propertyMap.entrySet()) {
                log.info("ConfigId: {}", entry.getKey());
                entry.getValue().forEach((k, v) -> log.info("   {} = {}", k, v));
            }
        }
    }
}