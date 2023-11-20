package edu.pnu.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seq; // 시퀀스 id

	private String 식품명; // 식단 이름
	private String _1회제공량; // 제공량
	private String 섭취량;
	private String 칼로리; // 칼로리	
	private String 탄수화물; // 탄수화물
	private String 단백질; // 단백질
	private String 지방; // 지방
	private String 당류; // 당류
	private String 나트륨; // 나트륨
	private String 콜레스테롤; // 콜레스테롤
	private String 포화지방; // 포화지방
	private String 트랜스지방; // 트랜스지방

	@ManyToOne
	@JoinColumn(name = "datehistory_seq")
	@JsonIgnore
	private Datehistory datehistory;
}
