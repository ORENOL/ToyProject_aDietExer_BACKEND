package edu.pnu.domain;

import java.util.Date;
import java.util.List;

import edu.pnu.domain.enums.Slot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DietRequest {

	private Date date;
	private Slot slot;
	private List<Diet> dietList;
	private String img;
	private Nutrient nutrient;
	private Float weight;
}
