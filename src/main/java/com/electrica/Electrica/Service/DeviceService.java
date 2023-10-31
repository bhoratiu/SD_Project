package com.electrica.Electrica.Service;

import com.electrica.Electrica.DTO.DeviceDTO;
import com.electrica.Electrica.Entity.Device;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DeviceService {
    List<DeviceDTO> getAllDevices();

    DeviceDTO getDeviceById(Long id);

    DeviceDTO addDevice(DeviceDTO deviceDTO);

    DeviceDTO updateDevice(Long id, DeviceDTO deviceDTO);

    void deleteDevice(Long id);

    @Transactional
    Device addIdsToDevice(Long deviceId, List<Long> userIds);

    @Transactional
    Device removeIdsToDevice(Long deviceId, List<Long> userIds);

    List<Device> getUserDevices(Long userId);

    void unlinkUserDevices(Long userId);
}
