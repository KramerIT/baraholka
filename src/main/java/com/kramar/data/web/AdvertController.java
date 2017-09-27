package com.kramar.data.web;

import com.kramar.data.dto.AdvertDto;
import com.kramar.data.service.AdvertService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(AdvertController.REQUEST_MAPPING)
public class AdvertController {

    public static final String REQUEST_MAPPING = "/adverts";

    @Autowired
    private AdvertService advertService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "List all advert",
            notes = "List all advert using paging",
            response = AdvertDto.class,
            responseContainer = "List"
    )
    public Page<AdvertDto> getAllAdvert(final Pageable pageable) {
        return advertService.getAllAdverts(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "Find advert by id",
            notes = "Find advert by id (UUID)",
            response = AdvertDto.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Advert with such ID", response = AdvertDto.class),
            @ApiResponse(code = 404, message = "Advert with such ID doesn't exists")
    })
    public AdvertDto getAdvert(@PathVariable("id") final UUID id) {
        return advertService.getAdvertById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertDto createAdvert(@RequestBody @Validated final AdvertDto advertDto) {
        return advertService.createAdvert(advertDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdvertDto modifyAdvert(@PathVariable("id") final UUID id, @RequestBody @Validated final AdvertDto advertDto) {
        return advertService.modifyAdvertById(id, advertDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdvert(@PathVariable("id") final UUID id) {
        advertService.deleteAdvertById(id);
    }


}
