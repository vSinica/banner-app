package ru.vados.JpaTestWork.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "banners")
public class Banner {

    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE},
            orphanRemoval = true,
            mappedBy = "banner"
    )
    @JsonIgnore
    private List<Request> requests = new ArrayList<>();

    public void addRequest(Request request){
        this.requests.add(request);
    }

    public void removeRequest(Request request){
        this.requests.remove(request);
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "categoryId")
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="name", scope = Category.class)
    @JsonIdentityReference(alwaysAsId=true)
    private Category categoryId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean deleted;

    public Banner() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banner banner = (Banner) o;
        return id.equals(banner.id) &&
                name.equals(banner.name) &&
                content.equals(banner.content) &&
                deleted.equals(banner.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, content, deleted);
    }
}
