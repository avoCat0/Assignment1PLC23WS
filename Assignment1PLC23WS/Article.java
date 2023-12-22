package Assignment1PLC23WS;

import java.io.*;
import java.time.Year;
import java.text.DecimalFormat;


//Abstract class Article
public abstract class Article implements Serializable {
 private int id;
 private String title;
 private int releaseYear;
 private String publisher;
 private double basePrice;


 // Constructor
 public Article(int id, String title, String publisher, int releaseYear, double basePrice)  {
     int currentYear = Year.now().getValue();
     

        
            if (releaseYear > currentYear) {
                throw new IllegalArgumentException("Error: Invalid release year.");
            }

        
    
     
        if (basePrice < 0)
    	     throw new IllegalArgumentException("Error: Invalid parameter");

    
        try{    
            this.id = id;
        }
        catch(NumberFormatException e){
            throw new IllegalArgumentException("Error: Invalid parameter.");
        }
     this.title = title;
     this.releaseYear = releaseYear;
     this.publisher = publisher;
     this.basePrice = basePrice;
 }

 // Abstract method to calculate discount (to be implemented in subclasses)
 public abstract double getDiscount();

 // Method to calculate article age
 public int getAge() {
     int currentYear = Year.now().getValue();
     return currentYear - releaseYear;
 }

 // Method to calculate article price after applying discount
 public double getPrice() {
     double finalPrice = basePrice - (basePrice * getDiscount());

    DecimalFormat df = new DecimalFormat("#.##");
    String roundedPrice = df.format(finalPrice);
        
    return Double.parseDouble(roundedPrice);

 }


 public int getId() {
     return id;
 }

 public String getTitle() {
     return title;
 }

 public int getReleaseYear() {
     return releaseYear;
 }

 public String getPublisher() {
     return publisher;
 }

 public double getBasePrice() {
     return basePrice;
 }


 public void setId(int id) {
     this.id = id;
 }

 public void setTitle(String title) {
     this.title = title;
 }

 public void setReleaseYear(int releaseYear) {
     this.releaseYear = releaseYear;
 }

 public void setPublisher(String publisher) {
     this.publisher = publisher;
 }

 public void setBasePrice(double basePrice) {
     this.basePrice = basePrice;
 }
}

