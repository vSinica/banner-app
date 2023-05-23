package ru.vados.BannerApp.Dto;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {

    @Value
    public static class CategoryCreate{
        @NotBlank
        String categoryName;
        @NotBlank
        String categoryReqId;
    }

    @Value
    public static class CategoryUpdate{
        @NotBlank
        String categoryName;
        @NotBlank
        String categoryReqId;
        @NotBlank
        Long idCategory;
    }

    @Value
    public static class CategoryDelete{
        @NotBlank
        Long idCategory;
    }

    @Value
    public static class Categoryitem {
        Long id;
        String name;
        String reqName;
        Boolean deleted;
    }



}
