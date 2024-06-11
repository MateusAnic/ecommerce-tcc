package com.zprmts.tcc.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zprmts.tcc.ecommerce.enums.StatusPerfumeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "perfume")
public class Perfume {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfume_id_seq")
    @SequenceGenerator(name = "perfume_id_seq", sequenceName = "perfume_id_seq", allocationSize = 1)

    @Column(name = "id_perfume")
    private Long id;

    @Column(name = "description")
    private String description;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "price")
    private Double price;
    
    @Column(name = "categories")
    private String categories;

    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "perfume_rating")
    private Double perfumeRating;

    @Column(name = "ativo")
    private StatusPerfumeEnum ativo;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "ORDER_ITEM",
            joinColumns = @JoinColumn(name = "id_perfume"),
            inverseJoinColumns = @JoinColumn(name = "id_order")
    )
    private Set<Order> orderItemSet = new HashSet<>();

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "perfume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    public Perfume() {
        this.ativo = StatusPerfumeEnum.ATIVO;
    }
}
