package edu.pnu.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class Diet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long seq; // 시퀀스 id
	private String Member_id; 	// member의 id와 외래키 연결 예정
	private String name; // 식단 이름
	private String Serving_size; // 제공량
	private String kcal; // 칼로리	
	private String carbs; // 탄수화물
	private String protein; // 단백질
	private String fat; // 지방
	private String sugars; // 당류
	private String sodium; // 나트륨
	private String cholesterol; // 콜레스테롤
	private String saturated_fat; // 포화지방
	private String trans_fat; // 트랜스지방
	@Temporal(TemporalType.TIMESTAMP)
	@Builder.Default
	private Date regidate = new Date(); // 먹은 날
	//@Enumerated(EnumType.STRING)
	//private WHEN when; // 아침, 점심, 저녁
}
