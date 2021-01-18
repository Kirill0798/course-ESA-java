package com.app.entities;

import javax.persistence.*;


@Entity
@Table(schema="public", name="box")
public class Box {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name="address")
    private String address;

    @ManyToOne(optional = false)
    @JoinColumn(name = "gift_id")
    private Gift gift;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public Gift getGift() { return gift; }

    public void setGift(Gift gift) { this.gift = gift; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return id == box.id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
