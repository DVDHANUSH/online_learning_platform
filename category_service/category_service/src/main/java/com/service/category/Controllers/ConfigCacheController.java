package com.service.category.Controllers;
import com.service.category.services.ConfigCacheService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/config-cache")
public class ConfigCacheController {

    @Autowired
    private ConfigCacheService configCacheService;

    @GetMapping("/{key}")
    public ResponseEntity<List<String>> getValues(@PathVariable String key) {
        return ResponseEntity.ok(configCacheService.getValues(key));
    }
    @GetMapping("/all")
    public Map<String, List<String>> getAll() {
        return configCacheService.getAllConfigs();
    }
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh() {
        configCacheService.refreshCache();
        return ResponseEntity.ok("Cache manually refreshed from DB.");
    }
}