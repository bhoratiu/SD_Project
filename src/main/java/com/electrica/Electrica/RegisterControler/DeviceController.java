package com.electrica.Electrica.RegisterControler;

import com.electrica.Electrica.DTO.AddIdsDTO;
import com.electrica.Electrica.DTO.DeviceDTO;
import com.electrica.Electrica.Entity.Device;
import com.electrica.Electrica.Service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<DeviceDTO> addDevice(@RequestBody DeviceDTO deviceDTO) {
        DeviceDTO createdDevice = deviceService.addDevice(deviceDTO);
        return ResponseEntity.ok(createdDevice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable Long id) {
        DeviceDTO deviceDTO = deviceService.getDeviceById(id);
        return ResponseEntity.ok(deviceDTO);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<DeviceDTO> devices = deviceService.getAllDevices();
        return ResponseEntity.ok(devices);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable Long id, @RequestBody DeviceDTO deviceDTO) {
        DeviceDTO updatedDevice = deviceService.updateDevice(id, deviceDTO);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok("Device deleted successfully");
    }

    @PostMapping("/{deviceId}/add-ids")
    public ResponseEntity<Device> addIdsToDevice(@PathVariable Long deviceId, @RequestBody AddIdsDTO addIdsDTO) {
        Device updatedDevice = deviceService.addIdsToDevice(deviceId, addIdsDTO.getUserIds());
        return ResponseEntity.ok(updatedDevice);
    }

    @PostMapping("/{deviceId}/remove-ids")
    public ResponseEntity<Device> removeIdsToDevice(@PathVariable Long deviceId, @RequestBody AddIdsDTO addIdsDTO) {
        Device updatedDevice = deviceService.removeIdsToDevice(deviceId, addIdsDTO.getUserIds());
        return ResponseEntity.ok(updatedDevice);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Device>> getUserDevices(@PathVariable Long userId) {
        List<Device> devices = deviceService.getUserDevices(userId);
        return ResponseEntity.ok(devices);
    }

    @PostMapping("/unlinkUserDevices")
    public ResponseEntity<?> unlinkUserDevices(@RequestBody Long userId) {
        try {
            deviceService.unlinkUserDevices(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to unlink devices");
        }
    }
}
