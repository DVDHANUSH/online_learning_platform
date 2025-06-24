package com.service.course.Controllers;
import com.service.course.dto.CustomPropConfigDto;
import com.service.course.services.CustomPropConfigManager;
import com.service.course.services.CustomPropConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/custom/config")
@Tag(name = "CustomPropConfig", description = "CustomPropConfig related APIs")
public class CustomPropConfigController {
    @Autowired
    CustomPropConfigService customPropConfigService;
@Autowired
    CustomPropConfigManager customPropConfigManager;
    @GetMapping("/get")
    @Operation(
            summary = "Get All CustomPropConfig Properties",
            description = "Get All CustomPropConfig Properties")
    public ResponseEntity<List<CustomPropConfigDto>> getAllConfigProperties() {
        return ResponseEntity.ok(customPropConfigService.getAllConfigProperties());
    }

    @GetMapping("/get/{configId}")
    @Operation(
            summary = "Get CustomPropConfig Properties By ConfigId",
            description = "Get CustomPropConfig Properties By ConfigId")
    public ResponseEntity<CustomPropConfigDto> getConfigPropertiesByConfigId(
            @PathVariable("configId") String configId) {
        return ResponseEntity.ok(customPropConfigService.getConfigPropertiesByConfigId(configId));
    }

    @PostMapping("/save")
    @Operation(
            summary = "Save CustomPropConfig Properties",
            description = "Save CustomPropConfig Properties")
    public ResponseEntity<List<CustomPropConfigDto>> saveConfigProperties(
            @RequestBody List<CustomPropConfigDto> customPropConfig) {
        return ResponseEntity.ok(customPropConfigService.updateOrSave(customPropConfig));
    }
    @PostMapping("/saveManager")
    @Operation(
            summary = "Save CustomPropConfig Properties",
            description = "Save CustomPropConfig Properties")
    public ResponseEntity<String> saveConfigPropertiesManager(
            @RequestBody String name) {
        customPropConfigManager.saveProperty("item", name, "b");
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/delete/{configId}/{propertyKey}")
    @Operation(
            summary = "Delete CustomPropConfig Property By ConfigId and Property key",
            description = "Delete CustomPropConfig Property By ConfigId and Property key")
    public ResponseEntity<String> deletePropertyByConfigIdAndPropertyKey(
            @PathVariable("configId") String configId, @PathVariable("propertyKey") String propertyKey) {
        customPropConfigService.deletePropertyByConfigIdAndPropertyKey(configId, propertyKey);
        return ResponseEntity.ok(
                String.format("Property %s deleted successfully from configId %s", propertyKey, configId));
    }
}
