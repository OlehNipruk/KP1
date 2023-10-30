package service;



import model.Manufacturer;
import model.Souvenir;
import repository.ManufacturerRepository;
import repository.ManufacturerRepositoryImpl;
import repository.SouvenirRepository;
import repository.SouvenirRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ManufacturerService {
    private ManufacturerRepository manufacturerRepository;
    private SouvenirRepository souvenirRepository;

    public ManufacturerService() {
        manufacturerRepository = new ManufacturerRepositoryImpl();
        souvenirRepository = new SouvenirRepositoryImpl();
    }

    public void addManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.addManufacturer(manufacturer);
    }

    public void updateManufacturer(Manufacturer updatedManufacturer) {
        manufacturerRepository.updateManufacturer(updatedManufacturer);
    }

    public Manufacturer getManufacturerById(long id) {
        return manufacturerRepository.getManufacturerById(id);
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.getAllManufacturers();
    }

    public void deleteManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.removeManufacturer(manufacturer);
    }

    public List<Manufacturer> getManufacturersByPrice(double maxPrice) {
        List<Manufacturer> allManufacturers = manufacturerRepository.getAllManufacturers();
        return allManufacturers.stream()
                .filter(manufacturer -> {
                    List<Souvenir> souvenirs = souvenirRepository.getSouvenirsByManufacturer(manufacturer.getId());
                    return souvenirs.stream().anyMatch(souvenir -> souvenir.getPrice() < maxPrice);
                })
                .collect(Collectors.toList());
    }

}

