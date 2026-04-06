package com.vb.condopay.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vb.condopay.dto.request.UsuarioRequestDto;
import com.vb.condopay.dto.response.UsuarioResponseDto;
import com.vb.condopay.database.entity.UsuarioModel;

@Mapper(componentModel = "spring")
public interface IUsuarioMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    UsuarioModel toEntity(UsuarioRequestDto dto);

    UsuarioResponseDto toResponse(UsuarioModel model);
}
