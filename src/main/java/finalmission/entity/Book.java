package finalmission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    private String name;
    private String author;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private int inventory;
    private int period;

    public Book() {
    }

    public Book(Long id, String name, String author, Category category, int inventory, int period) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.inventory = inventory;
        this.period = period;
    }

    public Book(String name, String author, Category category, int inventory, int period) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.inventory = inventory;
        this.period = period;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }

    public int getInventory() {
        return inventory;
    }

    public int getPeriod() {
        return period;
    }
}
