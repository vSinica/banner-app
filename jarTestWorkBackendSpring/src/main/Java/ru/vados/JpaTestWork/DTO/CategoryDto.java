package ru.vados.JpaTestWork.Dto;

import lombok.Data;

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
}
