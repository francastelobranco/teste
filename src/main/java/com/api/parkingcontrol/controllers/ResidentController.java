package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ResidentDto;
import com.api.parkingcontrol.models.ResidentModel;
import com.api.parkingcontrol.services.ResidentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/resident")
public class ResidentController {

    @Autowired
    ResidentService residentService;

    @PostMapping
    public ResponseEntity<Object> saveResident(@RequestBody @Valid ResidentDto residentDto) {
        if(residentService.existsByApartmentAndBlock(residentDto.getApartment(), residentDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este apartamento/bloco já está cadastrado");
        }

        var residentModel = new ResidentModel();
        BeanUtils.copyProperties(residentDto, residentModel);
        residentModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.save(residentModel));
    }

    @GetMapping//mjij
    public ResponseEntity<Object> getAllResidents(){
        return ResponseEntity.status(HttpStatus.OK).body(residentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneResident(@PathVariable(value = "id") UUID id){
        Optional<ResidentModel> residentModelOptional = residentService.findById(id);

        if (!residentModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proprietário não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(residentModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteResident(@PathVariable(value = "id") UUID id) {
        Optional<ResidentModel> residentModelOptional = residentService.findById(id);

        if (!residentModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proprietário não encontrado");
        }

        residentService.delete(residentModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Proprietário excluído com sucesso");
    }

    @PutMapping("atualizar_proprietario/{id}") // melhorar a uri
    public ResponseEntity<Object>updateResident (@PathVariable(value = "id") UUID id,
                                                 @RequestBody @Valid ResidentDto residentDto){
        Optional<ResidentModel> residentModelOptional = residentService.findById(id);

        if (!residentModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proprietário não encontrado");
        }

        var residentModel = new ResidentModel();
        BeanUtils.copyProperties(residentDto, residentModel);
        residentModel.setId(residentModelOptional.get().getId());
        residentModel.setRegistrationDate(residentModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(residentService.save(residentModel));

    }
}
