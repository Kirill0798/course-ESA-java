package com.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "gift")
public class Gift{

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "price")
    private Double price;

    @JsonIgnore
    @OneToMany(mappedBy = "gift")
    private List<Box> boxes;

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public Double getWidth(){return width;}

    public void setWidth(Double width){this.width = width;}

    public Double getHeight(){return height;}

    public void setHeight(Double height){this.height = height;}

    public Double getWeight(){return weight;}

    public void setWeight(Double weight){this.weight = weight;}

    public Double getPrice(){return price;}

    public void setPrice(Double price){this.price = price;}

    public List<Box> getBoxes() {return boxes;}

    public void setBoxes(List<Box> boxes) {this.boxes = boxes;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gift gift = (Gift) o;
        return id == gift.id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
