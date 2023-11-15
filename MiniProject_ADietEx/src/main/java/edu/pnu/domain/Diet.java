package edu.pnu.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
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
	private long seq;
	// member의 id와 외래키 연결
	private int Member_id;
	private String name;
	private int Serving_size;
	private float kcal;
	private float carbs;
	private float protein;
	private float fat;
	private float sugars;
	private float sodium;
	private float cholesterol;
	private float saturated_fat;
	private float trans_fat;
	@Temporal(TemporalType.TIMESTAMP)
	@Builder.Default
	private Date regidate = new Date();
}
