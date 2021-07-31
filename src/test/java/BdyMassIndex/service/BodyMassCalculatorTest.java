package BdyMassIndex.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import BdyMassIndex.domain.PersonMedicalResponse;

public class BodyMassCalculatorTest {

    private BodyMassCalculator bodyMassCalculator;


    @Before
    public void setUp() {
        bodyMassCalculator = new BodyMassCalculator();
    }


    @Test
    public void shouldCalculateBodyMassIndex() throws JsonProcessingException {

        String input = "[{\"gender\": \"Male\", \"heightCm\": 171, \"weightKg\": 96 }, { \"gender\": \"Male\", \"heightCm\": 161, \"weightKg\":\n" +
                "85 }, { \"gender\": \"Male\", \"heightCm\": 180, \"weightKg\": 77 }, { \"gender\": \"Female\", \"heightCm\": 166,\n" +
                "\"weightKg\": 2}, {\"gender\": \"Female\", \"heightCm\": 150, \"weightKg\": 70}, {\"gender\": \"Female\",\n" +
                "\"heightCm\": 167, \"weightKg\": 82}]";

        bodyMassCalculator = new BodyMassCalculator();
        List<PersonMedicalResponse> responses = bodyMassCalculator.calculateBodyMassI(input);
        Assert.assertEquals(6, responses.size());

    }


}