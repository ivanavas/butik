package ba.sum.fpmoz.butik.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Date date;

    @Column(columnDefinition = "tinyint(1) default 0") // popravljen bug s zapisom u bazu
    boolean payed;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    User user;

    @ManyToMany
    @JoinTable(name="articles_discounts",
            joinColumns=@JoinColumn(name="dscount_id"),
            inverseJoinColumns = @JoinColumn(name="article_id")
    )
    List<Article> articles;

    public Discount() {}

    public Discount(Long id, Date date, boolean payed) {
        this.id = id;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
