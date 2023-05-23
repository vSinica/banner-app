package ru.vados.BannerApp.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
    private List<BannerEntity> banners = new ArrayList<>();

    public void addBanner(BannerEntity banner){
        this.banners.add(banner);
    }

    public void removeBanner(BannerEntity banner){
        this.banners.remove(banner);
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String reqName;

    @Column(nullable = false)
    private Boolean deleted;

}
