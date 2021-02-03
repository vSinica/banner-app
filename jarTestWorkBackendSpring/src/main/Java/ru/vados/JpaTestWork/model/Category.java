package ru.vados.JpaTestWork.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "category_id", updatable = false, nullable = false, unique=true)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE},
            orphanRemoval = true,
            mappedBy = "category_id"
    )
    @JsonManagedReference
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
    private String req_name;

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

    public String getReq_name() {
        return req_name;
    }

    public void setReq_name(String req_name) {
        this.req_name = req_name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}
