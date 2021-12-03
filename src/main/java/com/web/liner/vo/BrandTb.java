package com.web.liner.vo;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.web.liner.mapping.BrandMapping;
import com.web.liner.mapping.PlaceMapping;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "brandtb")
@Table(name = "brandtb")
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties({"regDt", "modDt", "brandId"})
public class BrandTb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "brandId")
	private Long brandId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "regDt", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date regDt;
	
	@Column(name = "modDt")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private Date modDt;
	
	@Transient
	private List<PlaceMapping> places;

	@Builder
	public BrandTb(Long brandId, String name, Date regDt, Date modDt) {
		super();
		this.brandId = brandId;
		this.name = name;
		this.regDt = regDt;
		this.modDt = modDt;
	}
	
	@Override
	public String toString() {
		
		String plist = "";
		for (PlaceMapping p : places) {
			plist += p.getName() + ",";
		}
		
		return "PlaceTb [brandId=" + brandId + ", name=" + name + ", regDt=" + regDt + ", modDt=" + modDt + ", places="
				+ plist + "]";
	}

	
}
