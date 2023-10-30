package repository;



import model.Souvenir;

import java.util.List;

public interface SouvenirRepository {
    List<Souvenir> getSouvenirsByManufacturer(long manufacturerId);

    void addSouvenir(Souvenir souvenir);

    void removeSouvenir(Souvenir souvenir);

    Souvenir getSouvenirById(long id);

    List<Souvenir> getAllSouvenirs();

    void updateSouvenir(Souvenir souvenir);

}