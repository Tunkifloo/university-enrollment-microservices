package com.springback.apimatriculas.service.interfaces;

import com.springback.apimatriculas.dto.request.CarreraRequestDTO;
import com.springback.apimatriculas.dto.response.CarreraResponseDTO;

import java.util.List;

public interface ICarreraService {

    CarreraResponseDTO create(CarreraRequestDTO requestDTO);
    CarreraResponseDTO getById(Long id);
    List<CarreraResponseDTO> getAll();
    List<CarreraResponseDTO> getAllActive();
    List<CarreraResponseDTO> getByFacultadId(Long facultadId);
    CarreraResponseDTO update(Long id, CarreraRequestDTO requestDTO);
    void delete(Long id);
    List<CarreraResponseDTO> searchByNombre(String nombre);
}