package com.goncharov.tinyurl.mapper;

import com.goncharov.tinyurl.dto.UrlGenerateDto;
import com.goncharov.tinyurl.dto.UrlInfoDto;
import com.goncharov.tinyurl.entity.Url;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UrlMapper {
    UrlMapper INSTANCE = Mappers.getMapper(UrlMapper.class);

    @Mapping(target = "creationDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "expirationDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    UrlGenerateDto generateDto(Url url);

    @Mapping(target = "creationDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "expirationDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "numberOfClicks", source = "counter")
    UrlInfoDto infoDto(Url url);
}
