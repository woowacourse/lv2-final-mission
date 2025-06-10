package ordering.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(nullable = false)
    private Long count;

    private String detail;
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private EmailStatus emailStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(User user, Category category, Product product, Long count, String detail,
        LocalDateTime createdAt, EmailStatus emailStatus, OrderStatus orderStatus) {
        this.user = user;
        this.category = category;
        this.product = product;
        this.count = count;
        this.detail = detail;
        this.amount = calculateAmount(product, count);
        this.createdAt = createdAt;
        this.emailStatus = emailStatus;
        this.orderStatus = orderStatus;
    }

    private double calculateAmount(Product product, Long count) {
        return product.getPrice() * count;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public Product getProduct() {
        return product;
    }

    public Long getCount() {
        return count;
    }

    public String getDetail() {
        return detail;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public EmailStatus getEmailStatus() {
        return emailStatus;
    }
}
