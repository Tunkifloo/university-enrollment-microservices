package com.springback.apimatriculas.service.interfaces;

import com.springback.apimatriculas.dto.request.FacultadRequestDTO;
import com.springback.apimatriculas.dto.response.FacultadResponseDTO;

import java.util.List;

public interface IFacultadService {

    FacultadResponseDTO create(FacultadRequestDTO requestDTO);
    FacultadResponseDTO getById(Long id);
    List<FacultadResponseDTO> getAll();
    List<FacultadResponseDTO> getAllActive();
    FacultadResponseDTO update(Long id, FacultadRequestDTO requestDTO);
    void delete(Long id);
    List<FacultadResponseDTO> searchByNombre(String nombre);
}
