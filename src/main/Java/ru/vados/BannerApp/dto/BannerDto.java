package ru.vados.BannerApp.dto;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BannerDto {

    @Value
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

    @Value
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

    @Value
    public static class BannerDelete{
        @NotNull
        Long id;
    }

    @Value
    public static class BannerItem{
        Long id;
        String name;
        Long price;
        String content;
        Boolean deleted;
    }
}
