package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Souvenir implements Serializable {
    @Serial
    private static final long serialVersionUID = -440429073126104564L;
    private long id;
    private String name;

    private Long manufacturerId;
    private Date releaseDate;
    ;
    private double price;

    public Souvenir(long id, String name, Long manufacturerId, Date releaseDate, double price) {
        this.id = id;
        this.name = name;
        this.manufacturerId = manufacturerId;
        this.releaseDate = releaseDate;
        this.price = price;
    }

    public Souvenir(String name, Long manufacturerId, Date releaseDate, double price) {
        this.name = name;
        this.manufacturerId = manufacturerId;
        this.releaseDate = releaseDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Souvenir{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturerId=" + manufacturerId +
                ", releaseDate=" + releaseDate +
                ", price=" + price +
                '}';
    }
}
