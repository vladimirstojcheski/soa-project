package mk.ukim.finki.soa.productcatalog.service.mapper;

import mk.ukim.finki.soa.productcatalog.model.Characteristic;
import mk.ukim.finki.soa.productcatalog.model.dto.CharacteristicDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CharacteristicMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "value", target = "value")
    Characteristic mapCharacteristic(CharacteristicDto characteristicDto);
}
