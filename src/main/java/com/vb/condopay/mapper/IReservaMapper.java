package com.vb.condopay.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vb.condopay.database.entity.ReservaModel;
import com.vb.condopay.dto.request.ReservaRequestDto;
import com.vb.condopay.dto.response.ReservaResponseDto;

@Mapper(componentModel = "spring")
public interface IReservaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "valorCobrado", ignore = true)
    ReservaModel toEntity(ReservaRequestDto dto);

    ReservaResponseDto toResponse(ReservaModel model);
}
