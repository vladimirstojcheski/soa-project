package mk.ukim.finki.soa.productcatalog.service.impl;

import mk.ukim.finki.soa.productcatalog.model.Characteristic;
import mk.ukim.finki.soa.productcatalog.model.dto.CharacteristicDto;
import mk.ukim.finki.soa.productcatalog.repository.CharacteristicRepository;
import mk.ukim.finki.soa.productcatalog.service.CharacteristicService;
import mk.ukim.finki.soa.productcatalog.service.mapper.CharacteristicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacteristicServiceImpl implements CharacteristicService {

    @Autowired
    CharacteristicRepository characteristicRepository;

    @Autowired
    CharacteristicMapper characteristicMapper;

    @Override
    public Characteristic addCharacteristic() {
        return null;
    }

    @Override
    public Characteristic getCharacteristicById(String id) {
        return characteristicRepository.findById(id).orElse(null);
    }

    @Override
    public Characteristic getCharacteristic(CharacteristicDto characteristicDto) {
        if (characteristicDto.getId() != null) {
            Optional<Characteristic> characteristic = characteristicRepository.findById(characteristicDto.getId());
            if (characteristic.isPresent()) {
                return characteristic.get();
            }
        }
        Characteristic characteristic = characteristicRepository.findCharacteristicByNameAndValue(characteristicDto.getName(), characteristicDto.getValue());
        if (characteristic != null) {
            return characteristic;
        }
        return characteristicRepository.save(characteristicMapper.mapCharacteristic(characteristicDto));
    }

    @Override
    public List<Characteristic> getAll() {
        return characteristicRepository.findAll();
    }
}
