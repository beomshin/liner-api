package com.web.liner.vo;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.web.liner.mapping.BrandMapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "placetb")
@Table(name = "placetb")
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties({"regDt", "modDt", "placeId"})
public class PlaceTb  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "placeId")
	private Long placeId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "regDt", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date regDt;
	
	@Column(name = "modDt")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date modDt;
	
	@Transient
	private List<BrandMapping> brands;
	
	@Builder
	public PlaceTb(Long placeId, String name, Date regDt, Date modDt) {
		super();
		this.placeId = placeId;
		this.name = name;
		this.regDt = regDt;
		this.modDt = modDt;
	}

	
	@Override
	public String toString() {
		
		String blist = "";
		for (BrandMapping b : brands) {
			blist += b.getName() + ",";
		}
		
		return "PlaceTb [placeId=" + placeId + ", name=" + name + ", regDt=" + regDt + ", modDt=" + modDt + ", brands="
				+ blist + "]";
	}

	
}
