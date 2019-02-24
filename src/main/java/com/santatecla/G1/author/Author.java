package com.santatecla.G1.author;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonView;
import com.santatecla.G1.book.Book;
import com.santatecla.G1.citation.Citation;
import com.santatecla.G1.theme.Theme;

@Entity
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@JsonView(BasicAtt.class)
	private String name;
	@JsonView(BasicAtt.class)
	private String urlImage;
	@JsonView(BasicAtt.class)
	private String bornDate;
	@JsonView(BasicAtt.class)
	private String deathDate;
	@JsonView(BasicAtt.class)
	private String bornPlace;
	@Column(length=500)
	@JsonView(BasicAtt.class)
	private String urlMap;
	@JsonView(BasicAtt.class)
	private int imgId;
	
	/********************************************
	* RELATIONS WITH OTHER CLASES TO DDBB MODEL
	********************************************/
	
	@OneToMany
	private List<Book> books;
	
	/********************************************
	 * METHODS OF THE CLASS
	 ********************************************/
	
	//Constructor to Spring
	public Author() {}

	//The type of the dates is Date, when we operate with date, to show we will use SimpleFormatDate
	public Author(String name, String bornDate, String deathDate, int imgId) {
		super();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		this.name = name;
		this.urlImage= "";
		this.urlMap="";
		this.bornPlace="";
		this.bornDate = bornDate;
		this.deathDate = deathDate;
		this.books = new ArrayList<>();
		this.imgId = imgId;
	}

	public Author(String name, String urlImage, String bornDate, String deathDate, String bornPlace,
			String urlMap, int imgId) {
		super();
		this.id = id;
		this.name = name;
		this.urlImage = urlImage;
		this.bornDate = bornDate;
		this.deathDate = deathDate;
		this.bornPlace = bornPlace;
		this.urlMap = urlMap;
		this.imgId = imgId;
		this.books = new ArrayList<>();

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}


	public String getBornPlace() {
		return bornPlace;
	}

	public void setBornPlace(String bornPlace) {
		this.bornPlace = bornPlace;
	}

	public String getUrlMap() {
		return urlMap;
	}

	public String getBornDate() {
		return bornDate;
	}

	public void setBornDate(String bornDate) {
		this.bornDate = bornDate;
	}

	public String getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public void setUrlMap(String urlMap) {
		this.urlMap = urlMap;
	}
	
	public void addBook(Book book) {
		this.books.add(book);
	}
	public List<Book> getBooks(){
		return this.books;
	}
	public int getImgId() {
		return imgId;
	}
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return "Nombe: "+name + "; Fecha de nacimiento: " +bornDate + "; Fecha de defunción: "+ deathDate;
	}
}
