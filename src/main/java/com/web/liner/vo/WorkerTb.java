package com.web.liner.vo;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.web.liner.mapping.BrandMapping;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "workertb")
@Table(name = "workertb")
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties({"regDt", "modDt"})
public class WorkerTb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "workerId")
	private Long workerId;
	
	@Column(name = "name")
	private String name;
	
	@OneToOne
	@JoinColumn(name = "accountId")
	private AccountTb accountTb;
	
	@Column(name = "kakaoId")
	private String kakaoId;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "state")
	private int state;
	
	@Column(name = "authFlag")
	private int authFlag;
	
	@Column(name = "count")
	private int count;
	
	@Column(name = "regDt", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")

	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date regDt;
	
	@Column(name = "modDt")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date modDt;
	
}
