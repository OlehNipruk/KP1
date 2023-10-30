package repository;



import model.Manufacturer;

import java.util.ArrayList;
import java.util.List;

public class ManufacturerRepositoryImpl implements ManufacturerRepository {
    private List<Manufacturer> manufacturers = new ArrayList<>();
    private long currentId = 1;
    private final String dataFile = "manufacturers.dat";
    private FileManager<Manufacturer> fileManager;

    public ManufacturerRepositoryImpl() {
        fileManager = new FileManager<>(dataFile);

        if (loadData()) {
            updateCurrentId();
        }
    }

    private void updateCurrentId() {
        currentId = manufacturers.stream()
                .mapToLong(Manufacturer::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(currentId);
        manufacturers.add(manufacturer);
        currentId++;
        saveData();
    }

    @Override
    public void removeManufacturer(Manufacturer manufacturer) {
        manufacturers.remove(manufacturer);
        saveData();
    }

    @Override
    public Manufacturer getManufacturerById(long id) {
        for (Manufacturer manufacturer : manufacturers) {
            if (manufacturer.getId() == id) {
                return manufacturer;
            }
        }
        return null;
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturers;
    }

    @Override
    public void updateManufacturer(Manufacturer updatedManufacturer) {
        for (int i = 0; i < manufacturers.size(); i++) {
            Manufacturer manufacturer = manufacturers.get(i);
            if (manufacturer.getId() == updatedManufacturer.getId()) {
                manufacturers.set(i, updatedManufacturer);
                saveData();
                break;
            }
        }
    }

    private void saveData() {
        fileManager.saveObjects(manufacturers);
    }

    private boolean loadData() {
        List<Manufacturer> loadedManufacturers = fileManager.loadObjects();
        if (loadedManufacturers != null) {
            manufacturers = loadedManufacturers;
            return true;
        }
        return false;
    }

    @Override
    public List<Manufacturer> getManufacturersByCountry(String country) {
        List<Manufacturer> matchingManufacturers = new ArrayList<>();
        for (Manufacturer manufacturer : manufacturers) {
            if (manufacturer.getCountry().equalsIgnoreCase(country)) {
                matchingManufacturers.add(manufacturer);
            }
        }
        return matchingManufacturers;
    }
}
