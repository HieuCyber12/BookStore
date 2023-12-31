package com.bookstore.model;
// Generated Oct 10, 2023, 5:06:29 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Reviews generated by hbm2java
 */
@Entity
@Table(name = "Reviews", schema = "dbo", catalog = "bookstore")
@NamedQueries({
	@NamedQuery(name = "Reviews.findAll", query = "SELECT r FROM Reviews r ORDER BY r.createdAt DESC"),
	@NamedQuery(name = "Reviews.countAll", query = "SELECT COUNT(r) FROM Reviews r"),
	@NamedQuery(name = "Reviews.findByCustomerAndBook", 
		query = "SELECT r FROM Reviews r WHERE r.users.userId =:customerId"
				+ " AND r.books.bookId =:bookId"),
	@NamedQuery(name = "Reviews.mostFavoredBooks",
		query = "SELECT r.books, COUNT(r.books.bookId) AS ReviewCount, AVG(r.rating) as AvgRating FROM Reviews r "
				+ "GROUP BY r.books.bookId HAVING AVG(r.rating) >= 4.0 "
				+ "ORDER BY ReviewCount DESC, AvgRating DESC"),
	@NamedQuery(name = "Reviews.countByCustomer",
				query = "SELECT COUNT(r.reviewId) FROM Reviews r WHERE r.users.userId =:customerId")
})
public class Reviews implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer reviewId;
	private Books books;
	private Users users;
	private int rating;
	private String headline;
	private String comment;
	private Date createdAt;
	private Date updatedAt;
	private boolean verified;
	private Set<ReviewImages> reviewImageses = new HashSet<ReviewImages>(0);

	public Reviews() {
	}

	public Reviews(Books books, Users users, int rating, boolean verified) {
		this.books = books;
		this.users = users;
		this.rating = rating;
		this.verified = verified;
	}

	public Reviews(Books books, Users users, int rating, String headline, String comment,
			boolean verified, Set<ReviewImages> reviewImageses) {
		this.books = books;
		this.users = users;
		this.rating = rating;
		this.headline = headline;
		this.comment = comment;
		this.verified = verified;
		this.reviewImageses = reviewImageses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "review_id", unique = true, nullable = false)
	public Integer getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "book_id", nullable = false)
	public Books getBooks() {
		return this.books;
	}

	public void setBooks(Books books) {
		this.books = books;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "rating", nullable = false)
	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Column(name = "headline")
	public String getHeadline() {
		return this.headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	@Column(name = "comment")
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, length = 23)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false, length = 23)
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "verified", nullable = false)
	public boolean isVerified() {
		return this.verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reviews")
	public Set<ReviewImages> getReviewImageses() {
		return this.reviewImageses;
	}

	public void setReviewImageses(Set<ReviewImages> reviewImageses) {
		this.reviewImageses = reviewImageses;
	}

	@Transient
	public String getStars() {
		String result = "";
		
		int numberOfStarsOn = (int) rating;
		
		for (int i = 1; i <= numberOfStarsOn; i++) {
			result += "on,";
		}
		
		for (int j = numberOfStarsOn + 1; j <= 5; j++) {
			result += "off,";
		}
		
		return result.substring(0, result.length() - 1);
	}
}
