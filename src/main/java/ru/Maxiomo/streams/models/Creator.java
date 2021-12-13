package ru.Maxiomo.streams.models;

import java.util.List;
import java.util.ListIterator;
import lombok.Data;
import ru.Maxiomo.streams.annotation.DataParcer;

@Data
public class Creator {
    @DataParcer(headerName = "CreatorList")
    private List<Car> cars;
    @DataParcer(headerPosition = 0)
    private String name;

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder createrData = new StringBuilder("Производитель: ");
        createrData.append(this.name + "; Его машины:\n");
        ListIterator<Car> listIterator = cars.listIterator();
        while (listIterator.hasNext()) {
            createrData.append(listIterator.next().toString());
            if (listIterator.hasNext()) {
                createrData.append("\n");
            }
        }
        return createrData.toString();
    }
}
