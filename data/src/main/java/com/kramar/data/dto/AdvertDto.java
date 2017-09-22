package com.kramar.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kramar.data.type.AdvertStatus;
import com.kramar.data.type.AdvertType;
import com.kramar.data.type.CurrencyType;
import com.kramar.data.type.ImageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(NON_NULL)
public class AdvertDto extends AbstractDto {

    @NotNull
    private AdvertType advertType;

    @NotNull
    private AdvertStatus advertStatus;

    @Length(max = 50)
    @NotBlank
    private String headLine;

    @Digits(integer = 10, fraction = 2)
    private Double price;

    @NotNull
    private CurrencyType currencyType;

    @Length(max = 4000)
    private String description;

    private UUID headLineImage;

    private Map<ImageType, UUID> images;

}