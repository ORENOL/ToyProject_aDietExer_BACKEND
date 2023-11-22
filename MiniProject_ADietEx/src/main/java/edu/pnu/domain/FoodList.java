package edu.pnu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class FoodList {
	
	@Id
	@JsonIgnore
	private String food_code;
	private String food_name; // 식단 이름
	private float serving_size; // 제공량
	private float intake_size;
	private float kcal; // 칼로리	
	private float carbohydrate; // 탄수화물
	private float protein; // 단백질
	private float fat; // 지방
	private float sugars; // 당류
	private float sodium; // 나트륨
	private float cholesterol; // 콜레스테롤
	private float saturated_fat; // 포화지방
	private float trans_fat; // 트랜스지방
	private float vita_b1;
	private float vita_b2;
	private float vita_b12;
	private float vita_c;
	private float magnesium;
	private float calcium;
	private float fiber;
	private float water;
	
}
