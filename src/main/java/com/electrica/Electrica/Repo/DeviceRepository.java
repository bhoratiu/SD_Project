package com.electrica.Electrica.Repo;

import com.electrica.Electrica.Entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    @Query("SELECT d FROM Device d WHERE :userId MEMBER OF d.userIds")
    List<Device> findByUserId(Long userId);
    List<Device> findByUserIdsContaining(Long userId);
}