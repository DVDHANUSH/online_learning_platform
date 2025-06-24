package com.service.course.services;
import java.util.*;
import java.util.stream.Collectors;

import com.service.course.dto.CustomPropConfigDto;
import com.service.course.entities.CustomPropConfig;
import com.service.course.repositories.CustomPropConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
@Service
public class CustomPropConfigService {
    @Autowired
    private CustomPropConfigRepository customPropConfigRepository;

    // 🔽 Load from cache if present, otherwise fetch from DB
    @Cacheable(value = "configCache", key = "#configId")
    public CustomPropConfigDto getConfigPropertiesByConfigId(String configId) {
        List<CustomPropConfig> configList = customPropConfigRepository
                .findByConfigId(configId)
                .orElseThrow(() ->
                        new NoSuchElementException(String.format("No Properties For the Config ID %s", configId)));

        CustomPropConfigDto aggregatedDto = new CustomPropConfigDto();
        aggregatedDto.setConfigId(configId);
        aggregatedDto.setProperties(new HashMap<>());

        configList.forEach(config -> {
            aggregatedDto.getProperties()
                    .merge(config.getPropertyKey(), config.getPropertyValue(), (v1, v2) -> v1 + "," + v2);
        });

        return aggregatedDto;
    }

    // 🔽 Save or update config, then evict cache for updated configIds
    @Caching(evict = {
            @CacheEvict(value = "configCache", allEntries = true)
    })
    public List<CustomPropConfigDto> updateOrSave(List<CustomPropConfigDto> customPropConfigDtos) {
        List<CustomPropConfig> updatedConfigs =
                customPropConfigDtos.stream()
                        .flatMap(dto ->
                                dto.getProperties().entrySet().stream().map(entry -> {
                                    String key = entry.getKey();
                                    String value = entry.getValue();
                                    return customPropConfigRepository
                                            .findByConfigIdAndPropertyKey(dto.getConfigId(), key)
                                            .map(existingConfig -> {
                                                existingConfig.setPropertyValue(value);
                                                return existingConfig;
                                            }).orElseGet(() -> {
                                                CustomPropConfig newConfig = new CustomPropConfig();
                                                newConfig.setConfigId(dto.getConfigId());
                                                newConfig.setPropertyKey(key);
                                                newConfig.setPropertyValue(value);
                                                return newConfig;
                                            });
                                })
                        ).collect(Collectors.toList());

        customPropConfigRepository.saveAll(updatedConfigs);
        return customPropConfigDtos;
    }

    // 🔽 Evict cache for that configId when deleted
    @CacheEvict(value = "configCache", key = "#configId")
    public void deletePropertyByConfigIdAndPropertyKey(String configId, String propertyKey) {
        CustomPropConfig existing = customPropConfigRepository
                .findByConfigIdAndPropertyKey(configId, propertyKey)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                String.format("No such PropertyKey %s for Config ID %s", propertyKey, configId)));

        customPropConfigRepository.deleteByConfigIdAndPropertyKey(configId, propertyKey);
    }

    public CustomPropConfig save(CustomPropConfig customPropConfig) {
        return customPropConfigRepository.save(customPropConfig);
    }
    public List<CustomPropConfigDto> getAllConfigProperties() {
        List<CustomPropConfig> allConfigs = customPropConfigRepository.findAll();
        Map<String, CustomPropConfigDto> aggregatedConfigs = new HashMap<>();
        allConfigs.forEach(config -> {
            String configId = config.getConfigId();
            CustomPropConfigDto dto = aggregatedConfigs.computeIfAbsent(configId, k -> {
                CustomPropConfigDto newDto = new CustomPropConfigDto();
                newDto.setConfigId(configId);
                newDto.setProperties(new HashMap<>());
                return newDto;
            });
            dto.getProperties()
                    .merge(config.getPropertyKey(), config.getPropertyValue(), (v1, v2) -> v1 + "," + v2);
        });

        return new ArrayList<>(aggregatedConfigs.values());
    }
    public CustomPropConfigDto entityToDto(CustomPropConfig customPropConfig) {
        return CustomPropConfigDto.builder()
                .configId(customPropConfig.getConfigId())
                .properties(Map.of(
                        customPropConfig.getPropertyKey(), customPropConfig.getPropertyValue()))
                .build();
    }
}