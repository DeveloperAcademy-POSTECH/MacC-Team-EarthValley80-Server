package com.ada.earthvalley.yomojomo.auth.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ada.earthvalley.yomojomo.common.baseEntities.BaseEntity;
import com.ada.earthvalley.yomojomo.auth.entities.enums.VendorType;
import com.ada.earthvalley.yomojomo.user.entities.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class VendorResource extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "vendor_resource_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String vendorId;

	@Enumerated(EnumType.STRING)
	private VendorType type;
}
