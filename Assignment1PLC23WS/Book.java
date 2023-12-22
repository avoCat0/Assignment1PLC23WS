package Assignment1PLC23WS;

public class Book extends Article {
    private int numPages;

    public Book(int id, String title, int releaseYear, String publisher, double basePrice, int numPages) {
        super(id, title, publisher, releaseYear, basePrice);
        this.numPages = numPages;
    }

    @Override
    public double getDiscount() {
        double discount = 0.05 * getAge(); // 5% discount per year

        // Maximum discount of 30%
        return Math.min(discount, 0.3) + (numPages > 1000 ? 0.03 : 0);
    }

    public int getNumPages() {
        return numPages;
    }

    @Override
    public String toString() {
        return "Type:       Book\n"     +
                "Id:         " + getId() +"\n"+
                "Title:      " + getTitle() +"\n"+ 
                "Year:       " + getReleaseYear() +"\n"+
                "Publisher:  " + getPublisher() +"\n"+
                "Base price: " + getBasePrice() +"\n"+
                "Price:      " + getPrice() +"\n"+ 
                "Pages:      " + getNumPages()+"\n";
        
    }
}



