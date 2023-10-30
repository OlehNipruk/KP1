package repository;

import model.Souvenir;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SouvenirRepositoryImpl implements SouvenirRepository {
    private List<Souvenir> souvenirs = new ArrayList<>();
    private long currentId = 1;
    private final String dataFile = "souvenirs.dat";
    private FileManager<Souvenir> fileManager;

    public SouvenirRepositoryImpl() {
        fileManager = new FileManager<>(dataFile);

        if (loadData()) {
            updateCurrentId();
        }
    }

    @Override
    public void addSouvenir(Souvenir souvenir) {
        souvenir.setId(currentId);
        souvenirs.add(souvenir);
        currentId++;
        saveData();
    }

    @Override
    public void removeSouvenir(Souvenir souvenir) {
        souvenirs.remove(souvenir);
        saveData();
    }

    @Override
    public void updateSouvenir(Souvenir updatedSouvenir) {
        for (int i = 0; i < souvenirs.size(); i++) {
            if (souvenirs.get(i).getId() == updatedSouvenir.getId()) {
                souvenirs.set(i, updatedSouvenir);
                saveData();
                return;
            }
        }
    }

    @Override
    public List<Souvenir> getAllSouvenirs() {
        return new ArrayList<>(souvenirs);
    }

    private void saveData() {
        fileManager.saveObjects(souvenirs);
    }

    @Override
    public Souvenir getSouvenirById(long id) {
        Optional<Souvenir> result = souvenirs.stream().filter(s -> s.getId() == id).findFirst();
        return result.orElse(null);
    }

    private boolean loadData() {
        List<Souvenir> loadedSouvenirs = fileManager.loadObjects();
        if (loadedSouvenirs != null) {
            souvenirs = loadedSouvenirs;
            return true;
        }
        return false;
    }

    private void updateCurrentId() {
        currentId = souvenirs.stream()
                .mapToLong(Souvenir::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public List<Souvenir> getSouvenirsByManufacturer(long manufacturerId) {
        List<Souvenir> manufacturerSouvenirs = new ArrayList<>();
        for (Souvenir souvenir : souvenirs) {
            if (souvenir.getManufacturerId() == manufacturerId) {
                manufacturerSouvenirs.add(souvenir);
            }
        }
        return manufacturerSouvenirs;
    }
}
