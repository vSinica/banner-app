package ru.vados.BannerApp.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "banners")
public class BannerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banner")
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private List<RequestEntity> requests = new ArrayList<>();

    public void addRequest(RequestEntity request){
        this.requests.add(request);
    }

    public void removeRequest(RequestEntity request){
        this.requests.remove(request);
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    @Fetch(FetchMode.JOIN)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="name", scope = CategoryEntity.class)
    @JsonIdentityReference(alwaysAsId=true)
    private CategoryEntity category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean deleted;

}
