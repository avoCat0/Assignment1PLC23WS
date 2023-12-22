package Assignment1PLC23WS;

public class DVD extends Article {
    private int lengthMinutes;
    private int ageRating;

    public DVD(int id, String title, int releaseYear, String publisher, double basePrice, int lengthMinutes, int ageRating) {
    	super(id, title, publisher, releaseYear, basePrice);
        this.lengthMinutes = lengthMinutes;
        //AgeRating restriction?
        
        
        if(ageRating != 0 && ageRating != 6 && ageRating != 12 && ageRating != 16 && ageRating != 18)
        	throw new IllegalArgumentException("Error: Invalid age rating.");
        
        this.ageRating = ageRating;
    }

    @Override
    public double getDiscount() {
        switch (ageRating) {
            case 0: return 0.20; // 20% discount
            case 6: return 0.15; // 15% discount
            case 12: return 0.10; // 10% discount
            case 16: return 0.05; // 5% discount
            case 18: return 0.00; // 0% discount
            default: return 0.00; // Default to no discount
        }
    }

    public int getLengthMinutes() {
        return lengthMinutes;
    }

    public int getAgeRating() {
        return ageRating;
    }

    @Override
    public String toString() {
        return "Type:       DVD\n"     +
                "Id:         " + getId() +"\n" + 
                "Title:      " + getTitle() +"\n"+ 
                "Year:       " + getReleaseYear() +"\n"+
                "Publisher:  " + getPublisher() +"\n"+
                "Base price: " + getBasePrice() +"\n"+
                "Price:      " + getPrice() +"\n"+ 
                "Length:     " + getLengthMinutes() +"\n"+ 
                "Age rating: " + getAgeRating() +"\n";
    
        }
}
