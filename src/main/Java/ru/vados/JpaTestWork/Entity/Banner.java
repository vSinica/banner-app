package ru.vados.JpaTestWork.Entity;

import com.fasterxml.jackson.annotation.*;
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
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banner")
    @Fetch(FetchMode.JOIN)
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
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    @Fetch(FetchMode.JOIN)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="name", scope = Category.class)
    @JsonIdentityReference(alwaysAsId=true)
    private Category category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean deleted;

}
