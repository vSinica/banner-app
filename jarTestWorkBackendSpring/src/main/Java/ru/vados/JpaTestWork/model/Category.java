package ru.vados.JpaTestWork.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE},
            orphanRemoval = true,
            mappedBy = "categoryId"
    )
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
    private List<Banner> banners = new ArrayList<>();

    public void addBanner(Banner banner){
        this.banners.add(banner);
    }

    public void removeBanner(Banner banner){
        this.banners.remove(banner);
    }

    public boolean hasBanner(){
        return !banners.isEmpty();
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String reqName;

    @Column(nullable = false)
    private Boolean deleted;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReqName() { return reqName; }

    public void setReqName(String reqName) {this.reqName = reqName; }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}
