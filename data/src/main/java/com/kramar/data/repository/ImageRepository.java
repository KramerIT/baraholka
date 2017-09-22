package com.kramar.data.repository;

import com.kramar.data.dbo.ImageDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<ImageDbo, UUID> {

    Optional<ImageDbo> findById(UUID id);

    default ImageDbo getById(UUID id) {
        return findById(id)
                .orElseThrow(() ->
//                        new ResourceNotFoundException(ErrorReason.RESOURCE_NOT_FOUND, "user"));
                        new RuntimeException("RESOURCE_NOT_FOUND"));
    }
}
