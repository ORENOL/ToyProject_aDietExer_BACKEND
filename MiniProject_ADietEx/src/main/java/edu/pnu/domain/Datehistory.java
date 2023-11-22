package edu.pnu.domain;

import java.sql.Blob;
import java.util.List;


import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	private String date; // 먹은 날
	@Enumerated(EnumType.STRING)
	private Slot slot; // 아침, 점심, 저녁
	private Blob img;  // Blob
	
	@OneToMany(mappedBy = "datehistory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Diet> diets;
	
	@ManyToOne
    @JoinColumn(name = "member_username", updatable = false)
    private Member member;
}
