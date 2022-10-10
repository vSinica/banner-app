package ru.vados.JpaTestWork.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BannerDto {

    @Data
    public static class BannerCreate{
        @NotBlank
        String categoryName;
        @NotBlank
        String bannerName;
        @NotBlank
        String bannerText;
        @NotNull
        Long price;
    }

    @Data
    public static class BannerUpdate{
        @NotNull
        String categoryName;
        @NotNull
        String bannerName;
        @NotNull
        String bannerText;
        @NotBlank
        Long price;
        @NotBlank
        Long id;
    }

    @Data
    public static class BannerDelete{
        @NotNull
        Long id;
    }

}
