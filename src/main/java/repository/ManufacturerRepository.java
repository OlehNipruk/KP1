package repository;

import model.Manufacturer;

import java.util.List;

public interface ManufacturerRepository {
    void addManufacturer(Manufacturer manufacturer);

    void removeManufacturer(Manufacturer manufacturer);

    Manufacturer getManufacturerById(long id);

    List<Manufacturer> getAllManufacturers();

    void updateManufacturer(Manufacturer manufacturer);

    List<Manufacturer> getManufacturersByCountry(String country);
}