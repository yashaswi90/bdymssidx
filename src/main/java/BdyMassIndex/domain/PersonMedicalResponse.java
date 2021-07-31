package BdyMassIndex.domain;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class PersonMedicalResponse {

    private Float bodyMassIndex;
    private String bodyMassCategory;
    private String healthRisk;
    private int count;




}
