package edu.pnu.domain;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Member {

	@Id
	@Column(name="Member_ID")
	private String id;
	private String nickname;
	private String password;
	@Temporal(TemporalType.TIMESTAMP)
	@Builder.Default
	private Date regidate = new Date();
	private String email;

}
