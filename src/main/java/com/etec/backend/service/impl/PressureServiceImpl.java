package com.etec.backend.service.impl;

import com.etec.backend.dto.PressureResponseDTO;
import com.etec.backend.dto.ResponseDTO;
import com.etec.backend.entity.Pressure;
import com.etec.backend.repository.PressureRepository;
import com.etec.backend.service.PressureService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PressureServiceImpl implements PressureService {

    private final PressureRepository pressureRepository;

    @Override
    public List<PressureResponseDTO> findByUserId(Long userId) {
        List<Pressure> pressures = pressureRepository.findByUserId(userId);
        return pressures.stream()
                .map(pressure -> new PressureResponseDTO(pressure.getId(), pressure.getDiastolic(),
                        pressure.getSystolic(), pressure.getPulse(), pressure.getDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Object create(Pressure pressure) {
        Pressure savedPressure = pressureRepository.save(pressure);
        return new PressureResponseDTO(savedPressure.getId(), savedPressure.getDiastolic(), savedPressure.getSystolic(),
                savedPressure.getPulse(), savedPressure.getDate());
    }

    @Override
    public Object update(Long id, Pressure pressure) {
        if (!pressureRepository.existsById(id)) {
            return new ResponseDTO("Não existe esse ID.");
        }
        pressure.setId(id);
        Pressure updatedPressure = pressureRepository.save(pressure);
        return new PressureResponseDTO(updatedPressure.getId(), updatedPressure.getDiastolic(),
                updatedPressure.getSystolic(), updatedPressure.getPulse(), updatedPressure.getDate());
    }

    @Override
    public Object delete(Long id) {
        if (!pressureRepository.existsById(id)) {
            return new ResponseDTO("Não existe esse ID.");
        }
        pressureRepository.deleteById(id);
        return new ResponseDTO("Removido com sucesso.");
    }
}