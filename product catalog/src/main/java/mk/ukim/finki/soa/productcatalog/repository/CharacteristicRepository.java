package mk.ukim.finki.soa.productcatalog.repository;

import mk.ukim.finki.soa.productcatalog.model.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, String> {
    Characteristic findCharacteristicByNameAndValue(String name, String value);
}
