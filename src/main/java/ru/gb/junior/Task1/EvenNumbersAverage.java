package ru.gb.junior.Task1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class EvenNumbersAverage {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(List.of(10, 15, 8, 9, 1, 5, -50, 8, 0, -7, 10, 6));
        System.out.println(result(numbers));
    }
    static <T extends Number> double result (List<T> numbers){
        return BigDecimal.valueOf(
                numbers.stream()
                        .filter(e-> e != null)
                        .mapToDouble(Number::doubleValue)
                        .filter(e -> e % 2 == 0)
                        .average()
                        .orElse(0.0))
                .setScale(3, RoundingMode.HALF_UP).doubleValue();
    }
}
