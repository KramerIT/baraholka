package com.kramar.data.repository;

import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.exception.ErrorReason;
import com.kramar.data.exception.ResourceNotFoundException;
import com.kramar.data.type.AdvertStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdvertRepository extends JpaRepository<AdvertDbo, UUID> {

    Page<AdvertDbo> findByOwner(final UserDbo user, final Pageable pageable);

    List<AdvertDbo> findByAdvertStatus(final AdvertStatus advertStatus);

    Optional<AdvertDbo> findById(final UUID id);

    Optional<AdvertDbo> findByHeadLine(final String headLine);

    default AdvertDbo getById(final UUID id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorReason.RESOURCE_NOT_FOUND, "advert"));
    }

}
