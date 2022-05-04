package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.ResidentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResidentRepository extends JpaRepository<ResidentModel, UUID> {
    boolean existsByApartmentAndBlock(String apartment, String block);
}
