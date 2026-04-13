package com.vb.condopay.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vb.condopay.database.entity.AreaComumModel;
import com.vb.condopay.database.repository.IAreaComumRepository;
import com.vb.condopay.dto.request.AreaComumRequestDto;
import com.vb.condopay.dto.response.AreaComumResponseDto;
import com.vb.condopay.exception.handler.NaoEncontradoException;
import com.vb.condopay.mapper.IAreaComumMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AreaComumService {

    private final IAreaComumRepository repository;
    private final IAreaComumMapper mapper;

    // POST
    @Transactional
    public AreaComumResponseDto cadastrarAreaComum(AreaComumRequestDto dto) {
        var areaComum = mapper.toEntity(dto);
        var areaSalva = repository.save(areaComum);
        return mapper.toResponse(areaSalva);
    }

    // GET
    public List<AreaComumResponseDto> listarAreaComum() {
        List<AreaComumModel> lista = repository.findAll();

        return lista.stream()
                    .map(AreaComumResponseDto::new)
                    .toList();
    }

    // GET
    public AreaComumResponseDto buscarAreaComum(UUID id) {
        var areaComum = repository.findById(id)
                                  .orElseThrow(() -> new NaoEncontradoException("Area Comum não encontrada"));

        return mapper.toResponse(areaComum);
    }

    //DELETE
    @Transactional
    public void deletarAreaComum(UUID id) {
        repository.deleteById(id);
    }
}
