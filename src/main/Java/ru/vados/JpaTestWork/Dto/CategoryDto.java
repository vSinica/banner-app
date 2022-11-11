package ru.vados.JpaTestWork.Dto;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {

    @Data
    public static class CategoryCreate{
        @NotBlank
        String categoryName;
        @NotBlank
        String categoryReqId;
    }

    @Data
    public static class CategoryUpdate{
        @NotBlank
        String categoryName;
        @NotBlank
        String categoryReqId;
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
