package com.goncharov.tinyurl.mapper;

import com.goncharov.tinyurl.dto.UrlRequestDto;
import com.goncharov.tinyurl.entity.Url;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UrlMapper {
    UrlMapper INSTANCE = Mappers.getMapper(UrlMapper.class);

    @Mapping(source = "alias", target = "alias")
    UrlRequestDto toDto(Url url);
}
