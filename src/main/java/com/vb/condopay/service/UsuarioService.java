package com.vb.condopay;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDto cadastrarUsuario(UsuarioRequestDto dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new ConflitoException("EMAIL JÁ CADASTRADO NO SISTEMA, TENTE NOVAMENTE!!");
        }

        var entity = mapper.toEntity(dto);
        entity.setSenha(passwordEncoder.encode(dto.senha()));

        var usuarioSalvo = repository.save(entity);
        return mapper.toResponse(usuarioSalvo);
    }
}
