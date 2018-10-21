package guru.bonacci.oogway.oracle.beanmapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import guru.bonacci.oogway.oracle.persistence.Gem;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.GemIdCarrier;

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
    
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "saying", target = "saying"),
        @Mapping(source = "author", target = "author")
    })
    Gem toIntIdGem(GemIdCarrier carrier);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "saying", target = "saying"),
        @Mapping(source = "author", target = "author")
    })
    GemIdCarrier toExtIdGem(Gem gem);
}
