package edu.pnu.domain;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Repository
public class Datehistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long seq;
	@JsonIgnore
	@Temporal(TemporalType.DATE)
	private Date date; // 먹은 날
	@Enumerated(EnumType.STRING)
	private Slot slot; // 아침, 점심, 저녁
	@Lob
    @Column(columnDefinition = "longtext")
	private String img; 
	
	@OneToMany(mappedBy = "datehistory", cascade = CascadeType.ALL)
    private List<Diet> diets;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "member_username", updatable = false)
    private Member member;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "nutrient_id")
	@JsonIgnore
	private Nutrient nutrient;
}
