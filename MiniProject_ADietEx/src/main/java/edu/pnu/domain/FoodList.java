package edu.pnu.domain;

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
	private String 식품코드;
    private String 식품명;
    private String _1회제공량;
    private String 칼로리;
    private String 수분;
    private String 단백질;
    private String 지방;
    private String 탄수화물;
    private String 당류;
    private String 식이섬유;
    private String 칼슘;
    private String 철;
    private String 마그네슘;
    private String 인;
    private String 칼륨;
    private String 나트륨;
    private String 아연;
    private String 구리;
    private String 망간;
    private String 비타민B1;
    private String 비타민B2;
    private String 비타민B12;
    private String 비타민C;
    private String 포화지방산;
    private String 콜레스테롤;
    private String 트랜스지방산;
	
}
