package com.ada.earthvalley.yomojomo.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.earthvalley.yomojomo.auth.entities.VendorResource;
import com.ada.earthvalley.yomojomo.auth.entities.enums.VendorType;

public interface VendorResourceRepository extends JpaRepository<VendorResource, Long> {
	Optional<VendorResource> findByVendorIdAndType(String vendorId, VendorType type);
}
