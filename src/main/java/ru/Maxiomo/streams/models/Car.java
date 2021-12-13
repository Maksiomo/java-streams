package ru.Maxiomo.streams.models;

import lombok.Data;
import ru.Maxiomo.streams.annotation.DataParcer;

@Data
public class Car {
    @DataParcer(headerPosition = 0)
    private String model;
    @DataParcer(headerPosition = 1)
    private String creator;
    @DataParcer(headerPosition = 2)
    private String editionYear;
    @DataParcer(headerPosition = 3)
    private String color;

    public boolean hasEmptyField() {
        return this.model.isBlank() || this.creator.isBlank() || this.editionYear.isBlank()
                || this.color.isBlank();
    }

    @Override
    public String toString() {
        return "Машина: " + this.model + "; Производитель: " + this.creator + "; Год выпуска: " + this.editionYear
                + "; Окраска: " + this.color + ";";
    }
}
