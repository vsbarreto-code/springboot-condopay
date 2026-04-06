package com.vb.condopay;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vb.condopay.database.repository.IUsuarioRepository;
import com.vb.condopay.dto.request.UsuarioRequestDto;
import com.vb.condopay.dto.response.UsuarioResponseDto;
import com.vb.condopay.exception.handler.ConflitoException;
import com.vb.condopay.mapper.IUsuarioMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final IUsuarioRepository repository;
    private final IUsuarioMapper mapper;

    // POST
    @Transactional
    public UsuarioResponseDto cadastrarUsuario(UsuarioRequestDto dto) {
        if (repository.existsByEmail(dto.email())) {
            // 409
            throw new ConflitoException("EMAIL JÁ CADASTRADO NO SISTEMA, TENTE NOVAMENTE!!");
        }
        var usuarioSalvo = repository.save(mapper.toEntity(dto));
        // 201
        return mapper.toResponse(usuarioSalvo);
    }


}
