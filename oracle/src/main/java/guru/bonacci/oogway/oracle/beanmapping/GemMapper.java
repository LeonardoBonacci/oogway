package guru.bonacci.oogway.oracle.beanmapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import guru.bonacci.oogway.domain.GemCarrier;
import guru.bonacci.oogway.oracle.persistence.Gem;

@Mapper
public interface GemMapper {

    GemMapper MAPPER = Mappers.getMapper( GemMapper.class );

    @Mappings({
        @Mapping(source = "saying", target = "saying"),
        @Mapping(source = "author", target = "author")
    })
    Gem toIntGem(GemCarrier carrier);

    @Mappings({
        @Mapping(source = "saying", target = "saying"),
        @Mapping(source = "author", target = "author")
    })
    GemCarrier toExtGem(Gem gem);
}
