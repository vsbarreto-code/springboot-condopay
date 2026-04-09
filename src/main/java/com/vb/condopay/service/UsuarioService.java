package com.vb.condopay;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vb.condopay.database.entity.UsuarioModel;
import com.vb.condopay.database.repository.IUsuarioRepository;
import com.vb.condopay.dto.request.UsuarioRequestDto;
import com.vb.condopay.dto.response.UsuarioResponseDto;
import com.vb.condopay.exception.handler.ConflitoException;
import com.vb.condopay.exception.handler.NaoEncontradoException;
import com.vb.condopay.mapper.IUsuarioMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final IUsuarioRepository repository;
    private final IUsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    // POST
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

    // GET
    public List<UsuarioResponseDto> listarUsuario() {
        List<UsuarioModel> lista = repository.findAll();

        return lista.stream()
                    .map(UsuarioResponseDto::new)
                    .toList();
    }

    // GET
    public UsuarioResponseDto buscarUsuario(UUID id) {
        var usuario = repository.findById(id)
                                .orElseThrow(() -> new NaoEncontradoException("Usuario não encontrado!!"));

        return mapper.toResponse(usuario);
    }

    // GET
    public UsuarioResponseDto usuarioLogado(Jwt jwt) {
        var email = jwt.getSubject();

        var usuario = repository.findByEmail(email)
                                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!"));

        return mapper.toResponse(usuario);
    }
}
