package ru.Maxiomo.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import ru.Maxiomo.streams.annotation.DataParcerProcessor;
import ru.Maxiomo.streams.models.Car;
import ru.Maxiomo.streams.models.Creator;

public class App {

    @SneakyThrows
    public static void main(String[] args) {
        Map<String, Integer> carMap = DataProcessor.loadData("CAR_DATA.csv");
        List<Car> carList = listCreator(carMap);

        printData(carList);
        printGroupedByColors(carList);
        toCarCreator(carList);
    }

    public static List<Car> listCreator(Map<String, Integer> map) {
        DataParcerProcessor processor = new DataParcerProcessor();
        List<Car> carList = new ArrayList<>();
        List<String> stringList = DataProcessor.mapToList(map);
        ListIterator<String> itt = stringList.listIterator();

        String line = itt.next();
        processor.readClass(line, Car.class);
        Car temp;
        while (itt.hasNext()) {
            line = itt.next();
            temp = processor.parseObject(line, Car.class, 4);
            if (!temp.hasEmptyField())
                carList.add(temp);
        }
        return carList;
    }

    public static void printData(List<Car> cars) {
        DataProcessor.rewriteData(
                cars.stream()
                        .collect(Collectors.mapping(Car::toString, Collectors.toList())),
                "task5Res.txt");
    }

    public static void printGroupedByColors(List<Car> cars) {
        DataProcessor.rewriteData(
                cars.stream()
                        .collect(Collectors.groupingBy(Car::getColor,
                                Collectors.mapping(Car::toString, Collectors.toList())))
                        .entrySet(),
                "task6Res.txt");
    }

    public static void toCarCreator(List<Car> cars) {
        Map<String, List<Car>> groupedByCreator = cars.stream()
                .collect(Collectors.groupingBy(
                        Car::getCreator,
                        Collectors.toList()));
        List<Creator> creatorList = new ArrayList<>();
        for (Map.Entry<String, List<Car>> entry : groupedByCreator.entrySet()) {
            List<Car> carsList = entry.getValue();
            String creatorKey = entry.getKey();
            Creator creator = new Creator();
            creator.setName(creatorKey);
            creator.setCars(carsList);
            creatorList.add(creator);
        }
        DataProcessor.rewriteData(
                creatorList.stream().collect(Collectors.mapping(Creator::toString, Collectors.toList())),
                "task7Res.txt");
        List<String> creatorNames = new ArrayList<>();
        for (Map.Entry<String, List<Car>> entry : groupedByCreator.entrySet()) {
            List<Car> values = entry.getValue();
            String key = entry.getKey();
            if (values.size() >= 2) {
                creatorNames.add(key);
            }
        }

        creatorNames = creatorNames.stream().sorted().collect(Collectors.toList());

        ListIterator<String> iterMakers = creatorNames.listIterator();
        Iterable<String> iterableMakers = () -> iterMakers;
        DataProcessor.rewriteData(iterableMakers, "task8Res.txt");
    }

}
