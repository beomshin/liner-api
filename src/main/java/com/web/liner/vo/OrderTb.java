package com.web.liner.vo;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "ordertb")
@Table(name = "ordertb")
@JsonInclude(Include.NON_NULL)
@DynamicInsert
@JsonIgnoreProperties({"regDt", "modDt", "amount", "discountType", "discount"})
public class OrderTb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId")
	private Long orderId;
	
	@Column(name = "orderCode")
	private String orderCode;
	
	@OneToOne
	@JoinColumn(name = "workerId")
	private WorkerTb workerTb;
	
	@Column(name = "extTranCode")
	private String extTranCode;
	
	@Column(name = "state")
	@ColumnDefault("0")
	private int state;
	
	@Column(name = "brand")
	private String brand;
	
	@Column(name = "place")
	private String place;
	
	@Column(name = "beginTime")
	private String beginTime;
	
	@Column(name = "endTime")
	private String endTime;
	
	@Column(name = "orderTime", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Timestamp orderTime;
	
	@Column(name = "serviceTime")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
	private Date serviceTime;
	
	@Column(name = "amount")
	@ColumnDefault("0")
	private int amount;
	
	@Column(name = "discountType")
	@ColumnDefault("0")
	private int discountType;
	
	@Column(name = "discount")
	@ColumnDefault("0")
	private int discount;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;

	@Column(name = "`desc`")
	private String desc;

	@Column(name = "regDt", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date regDt;

	@Column(name = "modDt")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date modDt;

	@Transient
	private String workerInfo;
}
