package com.api.parkingcontrol.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_RESIDENT")
public class ResidentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 130)
    private String responsibleName;

    @Column(nullable = false, length = 30)
    private String apartment;

    @Column(nullable = false, length = 30)
    private String block;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @OneToOne
    @JoinColumn(name = "parking_spot_model_id")
    private ParkingSpotModel parkingSpotModel;

}
