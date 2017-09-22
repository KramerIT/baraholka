package com.kramar.data.test.service;

import com.kramar.data.dto.AdvertDto;
import com.kramar.data.type.AdvertStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AdvertService {

    Page<AdvertDto> getAllAdverts(final Pageable pageable);

    List<AdvertDto> getAllAdvertsByStatus(AdvertStatus advertStatus);

    Page<AdvertDto> getAllAdvertsByUser(final Pageable pageable);

    AdvertDto createAdvert(final AdvertDto advertDto);

    AdvertDto getAdvertById(final UUID id);

    void deleteAdvertById(final UUID id);

    AdvertDto modifyAdvertById(final UUID id, final AdvertDto advertDto);

}
