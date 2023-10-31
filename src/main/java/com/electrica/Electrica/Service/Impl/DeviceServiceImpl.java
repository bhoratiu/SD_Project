package com.electrica.Electrica.Service.Impl;

import com.electrica.Electrica.DTO.DeviceDTO;
import com.electrica.Electrica.Entity.Device;
import com.electrica.Electrica.Repo.DeviceRepository;
import com.electrica.Electrica.Exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.electrica.Electrica.Service.DeviceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DeviceDTO> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
                .map(device -> modelMapper.map(device, DeviceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceDTO getDeviceById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));
        return modelMapper.map(device, DeviceDTO.class);
    }

    @Override
    @Transactional
    public DeviceDTO addDevice(DeviceDTO deviceDTO) {
        Device device = modelMapper.map(deviceDTO, Device.class);
        Device savedDevice = deviceRepository.save(device);
        return modelMapper.map(savedDevice, DeviceDTO.class);
    }

    @Override
    @Transactional
    public DeviceDTO updateDevice(Long id, DeviceDTO deviceDTO) {
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));

        existingDevice.setDescription(deviceDTO.getDescription());
        existingDevice.setMaximumHourlyEnergyConsumption(deviceDTO.getMaximumHourlyEnergyConsumption());
        existingDevice.setUserIds(deviceDTO.getUserIds());

        Device updatedDevice = deviceRepository.save(existingDevice);
        return modelMapper.map(updatedDevice, DeviceDTO.class);
    }

    @Override
    @Transactional
    public void deleteDevice(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));
        deviceRepository.delete(device);
    }

    @Override
    public Device addIdsToDevice(Long deviceId, List<Long> userIds) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id " + deviceId));
        device.getUserIds().addAll(userIds);
        return deviceRepository.save(device);
    }

    @Override
    public Device removeIdsToDevice(Long deviceId, List<Long> userIds) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id " + deviceId));
        device.getUserIds().removeAll(userIds);
        return deviceRepository.save(device);
    }

    @Override
    public List<Device> getUserDevices(Long userId) {
        return deviceRepository.findByUserId(userId);
    }

    @Override
    public void unlinkUserDevices(Long userId) {
        List<Device> devices = deviceRepository.findByUserIdsContaining(userId);
        for (Device device : devices) {
            device.getUserIds().remove(userId);
            deviceRepository.save(device);
        }
    }
}
