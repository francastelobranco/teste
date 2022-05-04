package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ResidentModel;
import com.api.parkingcontrol.repositories.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResidentService {

    @Autowired
    ResidentRepository residentRepository;


    @Transactional
    public ResidentModel save(ResidentModel residentModel) {
        return residentRepository.save(residentModel);
    }

    public Object findAll() {
        return residentRepository.findAll();
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return residentRepository.existsByApartmentAndBlock(apartment, block);
    }

    public Optional<ResidentModel> findById(UUID id) {
        return residentRepository.findById(id);
    }

    @Transactional
    public void delete(ResidentModel residentModel) {
        residentRepository.delete(residentModel);
    }
}
