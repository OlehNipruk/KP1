package service;


import model.Manufacturer;
import model.Souvenir;
import repository.ManufacturerRepository;
import repository.ManufacturerRepositoryImpl;
import repository.SouvenirRepository;
import repository.SouvenirRepositoryImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class SouvenirService {
    private SouvenirRepository souvenirRepository;
    private ManufacturerRepository manufacturerRepository;

    public SouvenirService() {
        souvenirRepository = new SouvenirRepositoryImpl();
        manufacturerRepository = new ManufacturerRepositoryImpl();
    }

    public void addSouvenir(Souvenir souvenir) {
        souvenirRepository.addSouvenir(souvenir);
    }

    public void editSouvenir(Souvenir editedSouvenir) {
        souvenirRepository.updateSouvenir(editedSouvenir);
    }

    public List<Souvenir> getAllSouvenirs() {
        return souvenirRepository.getAllSouvenirs();
    }

    public Souvenir getSouvenirById(long id) {
        return souvenirRepository.getSouvenirById(id);
    }

    public List<Souvenir> getSouvenirsByManufacturer(long manufacturerId) {
        List<Souvenir> allSouvenirs = souvenirRepository.getAllSouvenirs();
        return allSouvenirs.stream()
                .filter(souvenir -> souvenir.getManufacturerId() == manufacturerId)
                .collect(Collectors.toList());
    }

    public List<Souvenir> getSouvenirsByCountry(String country) {
        List<Long> manufacturerIds = manufacturerRepository.getManufacturersByCountry(country)
                .stream()
                .map(manufacturer -> manufacturer.getId())
                .collect(Collectors.toList());

        List<Souvenir> allSouvenirs = souvenirRepository.getAllSouvenirs();
        return allSouvenirs.stream()
                .filter(souvenir -> manufacturerIds.contains(souvenir.getManufacturerId()))
                .collect(Collectors.toList());
    }

    public void updateSouvenir(Souvenir souvenir) {
        souvenirRepository.updateSouvenir(souvenir);
    }

    public void deleteSouvenirsByManufacturer(long manufacturerId) {
        List<Souvenir> souvenirsToDelete = souvenirRepository.getSouvenirsByManufacturer(manufacturerId);
        for (Souvenir souvenir : souvenirsToDelete) {
            souvenirRepository.removeSouvenir(souvenir);
        }
    }

    public List<Manufacturer> getManufacturersForSouvenirByYear(String souvenirName, int year) {
        List<Souvenir> souvenirsByYear = getSouvenirsByYear(year);
        List<Manufacturer> manufacturers = new ArrayList<>();

        for (Souvenir souvenir : souvenirsByYear) {
            if (souvenir.getName().equals(souvenirName)) {
                Manufacturer manufacturer = manufacturerRepository.getManufacturerById(souvenir.getManufacturerId());
                if (manufacturer != null && !manufacturers.contains(manufacturer)) {
                    manufacturers.add(manufacturer);
                }
            }
        }
        return manufacturers;
    }

    public List<Souvenir> getSouvenirsByYear(int year) {
        List<Souvenir> allSouvenirs = souvenirRepository.getAllSouvenirs();
        return allSouvenirs.stream()
                .filter(souvenir -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(souvenir.getReleaseDate());
                    return calendar.get(Calendar.YEAR) == year;
                })
                .collect(Collectors.toList());
    }
}
