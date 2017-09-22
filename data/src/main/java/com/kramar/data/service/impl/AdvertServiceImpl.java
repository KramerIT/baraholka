package com.kramar.data.service.impl;

import com.kramar.data.converter.AdvertConverter;
import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.repository.AdvertRepository;
import com.kramar.data.service.AdvertService;
import com.kramar.data.service.AuthenticationService;
import com.kramar.data.dto.AdvertDto;
import com.kramar.data.type.AdvertStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertRepository advertRepository;
    @Autowired
    private AdvertConverter advertConverter;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public Page<AdvertDto> getAllAdverts(final Pageable pageable) {
        return advertRepository.findAll(pageable).map(advertConverter::transform);
    }

    @Override
    public List<AdvertDto> getAllAdvertsByStatus(AdvertStatus advertStatus) {
        List<AdvertDbo> byAdvertStatus = advertRepository.findByAdvertStatus(advertStatus);
        return (List<AdvertDto>) advertConverter.transform(byAdvertStatus);
    }

    @Override
    public Page<AdvertDto> getAllAdvertsByUser(final Pageable pageable) {
        final UserDbo user = authenticationService.getCurrentUser();
        return advertRepository.findByOwner(user, pageable).map(advertConverter::transform);
    }

    @Override
    public AdvertDto createAdvert(final AdvertDto advertDto) {
        advertDto.setId(null);
        AdvertDbo advertDbo = advertConverter.transform(advertDto);
        advertDbo = advertRepository.save(advertDbo);
        return advertConverter.transform(advertDbo);
    }

    @Override
    public AdvertDto getAdvertById(UUID id) {
        final AdvertDbo advertDbo = advertRepository.getById(id);
        return advertConverter.transform(advertDbo);
    }

    @Override
    public void deleteAdvertById(UUID id) {
        final AdvertDbo advertDbo = advertRepository.getById(id);
        advertRepository.delete(advertDbo);
    }

    @Override
    public AdvertDto modifyAdvertById(UUID id, AdvertDto advertDto) {
        final AdvertDbo oldAdvert = advertRepository.getById(id);
        advertDto.setId(oldAdvert.getId());
        AdvertDbo advertDbo = advertConverter.transform(advertDto);
        advertDbo = advertRepository.save(advertDbo);
        return advertConverter.transform(advertDbo);
    }
}
