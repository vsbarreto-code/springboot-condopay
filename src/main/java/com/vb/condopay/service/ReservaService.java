package com.vb.condopay.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vb.condopay.database.entity.ReservaModel;
import com.vb.condopay.database.entity.enums.StatusEnum;
import com.vb.condopay.database.repository.IAreaComumRepository;
import com.vb.condopay.database.repository.IReservaRepository;
import com.vb.condopay.database.repository.IUsuarioRepository;
import com.vb.condopay.dto.request.ReservaRequestDto;
import com.vb.condopay.dto.response.ReservaResponseDto;
import com.vb.condopay.exception.handler.NaoEncontradoException;
import com.vb.condopay.mapper.IReservaMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final IUsuarioRepository usuarioRepository;
    private final IAreaComumRepository areaComumRepository;
    private final IReservaRepository repository;
    private final IReservaMapper mapper;

    // POST
    @Transactional
    public ReservaResponseDto criarReserva(ReservaRequestDto dto, Jwt jwt) {

        //Usuario
        var email = jwt.getSubject();
        var usuario = usuarioRepository.findByEmail(email)
                                       .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!"));

        //AreaComum
        var areaComum = areaComumRepository.findById(dto.areaComumId())
                                           .orElseThrow(() -> new NaoEncontradoException("Area Comum não encontrada"));
        boolean existeConflito = repository
            .existsByAreaComumIdAndStatusAndDataInicioLessThanAndDataFimGreaterThan(
                dto.areaComumId(),
                StatusEnum.CONFIRMADO,
                dto.dataFim(),
                dto.dataInicio()
            );

        if (existeConflito) {
            throw new RuntimeException("Já existe uma reserva nesse horário");
        }

        if (dto.dataFim()
               .isBefore(dto.dataInicio())) {
            throw new RuntimeException("Data fim não pode ser antes da data início");
        }

        //Reserva
        var reserva = mapper.toEntity(dto);

        reserva.setUsuario(usuario);
        reserva.setAreaComum(areaComum);
        reserva.setValorCobrado(areaComum.getValorReserva());

        var reservaSalva = repository.save(reserva);

        return mapper.toResponse(reservaSalva);
    }

    // GET -> AUTHETICATION
    public List<ReservaResponseDto> listarReservaAutenticado(Jwt jwt) {
        var email = jwt.getSubject();

        var usuario = usuarioRepository.findByEmail(email)
                                       .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado!"));


        List<ReservaModel> reservaLista = repository.findByUsuarioId(usuario.getId())
                                                    .stream()
                                                    .toList();

        return reservaLista.stream()
                           .map(ReservaResponseDto::new)
                           .toList();
    }

    // GET -> ID
    public ReservaResponseDto detalharReserva(UUID id) {
        var reserva = repository.findById(id)
                                .orElseThrow(() -> new NaoEncontradoException("Reserva não encontrada"));

        return mapper.toResponse(reserva);
    }

    //UPDATE -> ID
    @Transactional
    public void cancelarReserva(UUID id) {
        //Reserva
        var reserva = repository.findById(id)
                                .orElseThrow(() -> new NaoEncontradoException("Reserva não encontrada"));

        if (!reserva.getStatus()
                    .equals(StatusEnum.CONFIRMADO)) {
            throw new RuntimeException("Só reservas confirmadas podem ser canceladas");
        }

        reserva.setStatus(StatusEnum.CANCELADO);
    }
}
