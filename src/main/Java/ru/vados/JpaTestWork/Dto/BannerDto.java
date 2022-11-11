package ru.vados.JpaTestWork.Dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Value;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.vados.JpaTestWork.Entity.CategoryEntity;
import ru.vados.JpaTestWork.Entity.RequestEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    @Value
    public static class BannerItem{
        Long id;
        String name;
        Long price;
        String content;
        Boolean deleted;
    }
}
