package com.web.liner.vo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Entity(name = "banktb")
@Table(name = "banktb")
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties({"bankId", "regDt", "modDt"})
@DynamicInsert
public class BankTb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bankId")
	private Long bankId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "regDt", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date regDt;
	
	@Column(name = "modDt")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date modDt;
	
}
