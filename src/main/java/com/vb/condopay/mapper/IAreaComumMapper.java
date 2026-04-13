package com.vb.condopay.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vb.condopay.database.entity.AreaComumModel;
import com.vb.condopay.dto.request.AreaComumRequestDto;
import com.vb.condopay.dto.response.AreaComumResponseDto;

@Mapper(componentModel = "spring")
public interface IAreaComumMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "diponivel", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    AreaComumModel toEntity(AreaComumRequestDto dto);

    AreaComumResponseDto toResponse(AreaComumModel model);
}
