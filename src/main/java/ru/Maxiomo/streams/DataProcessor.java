package ru.Maxiomo.streams;

import java.io.BufferedWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class DataProcessor {

    @SneakyThrows
    public static Map<String, Integer> loadData(String dataPath) {
        URL data = DataProcessor.class.getClassLoader().getResource(dataPath);
        Path filePath = Paths.get(data.toURI());

        return Files.lines(filePath, StandardCharsets.UTF_8)
                .map(str -> str.replaceAll("[,]", " "))
                .map(String::trim)
                .flatMap(line -> Arrays.stream(line.split("\\r?\\n")))
                .collect(Collectors.groupingBy(str -> str))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, ent -> ent.getValue().size()));
    }

    public static List<String> mapToList(Map<String, Integer> dataMap) {
        return dataMap.keySet().stream()
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public static void rewriteData(Iterable<?> iterable, String fileName) {
        Path path = Paths.get("")
                .toAbsolutePath()
                .resolve("results")
                .resolve(fileName);
        Files.deleteIfExists(path);

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Object elem : iterable) {
                bw.write(elem.toString());
                bw.newLine();
            }
        }
    }

}
