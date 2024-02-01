package mk.ukim.finki.soa.productcatalog.service;

import mk.ukim.finki.soa.productcatalog.model.Characteristic;
import mk.ukim.finki.soa.productcatalog.model.dto.CharacteristicDto;

import java.util.List;

public interface CharacteristicService {
    Characteristic addCharacteristic();
    Characteristic getCharacteristicById(String id);
    Characteristic getCharacteristic(CharacteristicDto characteristicDto);
    List<Characteristic> getAll();
}
