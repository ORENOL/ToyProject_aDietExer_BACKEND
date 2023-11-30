package edu.pnu.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "datehistory")
public class Nutrient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long nutrient_id;

	@OneToOne(mappedBy = "nutrient", cascade = CascadeType.ALL)
	@JoinColumn(name = "datehistory_seq")
	//일대일 매핑에서는 외래키를 한쪽에서만 관리함
	//Datehistory에서 외래키를 관리하는것이 좋아보여서 nutrient class에서 매핑
	private Datehistory datehistory;
	
	private float totalCalcium;
	private float totalCarbohydrate;
	private float totalCholesterol;
	private float totalFat;
	private float totalFiber;
	private float totalKcal;
	private float totalMagnesium;
	private float totalProtein;
	private float totalSaturatedFat;
	private float totalSodium;
	private float totalSugars;
	private float totalTransFat;
	private float totalVitaB1;
	private float totalVitaB2;
	private float totalVitaB12;
	private float totalVitaC;
	private float totalWater;
}
