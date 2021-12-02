package com.web.liner.vo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "authtb")
@Table(name = "authtb")
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties({"bankId", "regDt", "modDt"})
@DynamicInsert
public class AuthTb {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authId")
	private Long authId;
	
	@Column(name = "authCode")
	private String authCode;
	
	@ManyToOne
	@JoinColumn(name = "workerId")
	private WorkerTb workerTb;
	
	@Column(name = "regDt", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date regDt;
	
	@Column(name = "modDt")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date modDt;
}
