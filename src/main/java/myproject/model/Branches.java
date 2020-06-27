package myproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * @author paveldikin
 * @date 27.06.2020
 */
@Getter
@Setter
@Entity
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Branches {
    @Id
    private Long id;
    private String title;
    private Double lon;
    private Double lat;
    private String address;
    @Transient
    private Long distance;
    @Transient
    private Integer dayOfWeek;
    @Transient
    private Integer hourOfDay;
    @Transient
    private Long predicting;
}
