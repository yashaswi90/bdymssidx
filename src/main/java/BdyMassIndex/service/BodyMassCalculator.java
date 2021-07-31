package BdyMassIndex.service;

import static BdyMassIndex.enums.BodyMassCategory.MODERATELY_OBESE;
import static BdyMassIndex.enums.BodyMassCategory.NORMAL;
import static BdyMassIndex.enums.BodyMassCategory.OVERWEIGHT;
import static BdyMassIndex.enums.BodyMassCategory.SEVERELY_OBSES;
import static BdyMassIndex.enums.BodyMassCategory.UNDERWEIGHT;
import static BdyMassIndex.enums.BodyMassCategory.VERY_SEVERELY_OBESE;
import static BdyMassIndex.enums.HealthRisk.High;
import static BdyMassIndex.enums.HealthRisk.Low;
import static BdyMassIndex.enums.HealthRisk.Malnutrition;
import static BdyMassIndex.enums.HealthRisk.Medium;
import static BdyMassIndex.enums.HealthRisk.VeryHigh;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import BdyMassIndex.domain.Person;
import BdyMassIndex.domain.PersonMedicalResponse;
import BdyMassIndex.enums.BodyMassCategory;
import BdyMassIndex.enums.HealthRisk;

public class BodyMassCalculator {

    public List<PersonMedicalResponse> calculateBodyMassI(String jsonArray) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Person> persons = objectMapper.readValue(jsonArray, new TypeReference<List<Person>>() {
        });
        List<PersonMedicalResponse> responses = new ArrayList<>();

        persons.stream().forEach(person -> {
            float bmi = calculateBmi(person);
            filterHealthRiskAndCategory(responses, bmi);
        });
        Map<String, Long> healthMap = responses.stream()
                .collect(groupingBy(PersonMedicalResponse::getBodyMassCategory, counting()));

        if (healthMap.get(OVERWEIGHT.toString()) != null) {
            System.out.println("Total count is " + healthMap.get(OVERWEIGHT.toString()));
        }
        return responses;
    }

    private static void filterHealthRiskAndCategory(List<PersonMedicalResponse> responses, float bmi) {
        if (bmi < 18.4) {
            responses.add(createPersonResponseObj(bmi, UNDERWEIGHT, Malnutrition));
        } else if (bmi > 18.5 || bmi < 24.9) {
            responses.add(createPersonResponseObj(bmi, NORMAL, Low));
        } else if (bmi > 25 || bmi < 29.9) {
            responses.add(createPersonResponseObj(bmi, MODERATELY_OBESE, Medium));
        } else if (bmi > 30 || bmi < 39.9) {
            responses.add(createPersonResponseObj(bmi, SEVERELY_OBSES, High));
        } else {
            responses.add(createPersonResponseObj(bmi, VERY_SEVERELY_OBESE, VeryHigh));
        }
    }

    private static PersonMedicalResponse createPersonResponseObj(float bmi, BodyMassCategory underweight, HealthRisk malnutrition) {
        return PersonMedicalResponse.
                builder().bodyMassIndex(bmi)
                .bodyMassCategory(underweight.toString())
                .healthRisk(malnutrition.toString())
                .build();

    }

    private static float calculateBmi(Person person) {
        return (float) ((100 * 100 * person.getWeightKg()) / (person.getHeightCm() * person.getHeightCm()));
    }

    public static void main(String[] args) throws JsonProcessingException {
        String input = "[{\"gender\": \"Male\", \"heightCm\": 171, \"weightKg\": 96 }, { \"gender\": \"Male\", \"heightCm\": 161, \"weightKg\":\n" +
                "85 }, { \"gender\": \"Male\", \"heightCm\": 180, \"weightKg\": 77 }, { \"gender\": \"Female\", \"heightCm\": 166,\n" +
                "\"weightKg\": 2}, {\"gender\": \"Female\", \"heightCm\": 150, \"weightKg\": 70}, {\"gender\": \"Female\",\n" +
                "\"heightCm\": 167, \"weightKg\": 82}]";


//        System.out.println(calculateBodyMassI(input));
    }
}
