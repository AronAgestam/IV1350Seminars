package main.integration;

public class Discount {

	private double discountSum;
	private double discountPercentage;
    private String discountDescription;

	public Discount(double discountSum, double discountPercentage, String discountDescription)  {
        this.discountSum = discountSum;
        this.discountPercentage = discountPercentage;
        this.discountDescription = discountDescription;
	}

    /**
     * @return String description of Discount.
     */
    @Override
    public String toString() {
        String discountString = "Discount: -" + discountSum + " (" + discountDescription + discountPercentage *100 +"%)";
        return discountString;
    }

    /**
     * @return double return the discountSum
     */
    public double getDiscountSum() {
        return discountSum;
    }
    /**
     * @param discountSum the discountSum to set
     */
    public void setDiscountSum(double discountSum) {
        this.discountSum = discountSum;
    }

    /**
     * @return double return the discountPercentage
     */
    public double getDiscountPercentage() {
        return discountPercentage;
    }
    /**
     * @param discountPercentage the discountPercentage to set
     */
    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }


    /**
     * @return String return the discountDescription
     */
    public String getDiscountDescription() {
        return discountDescription;
    }
    /**
     * @param discountDescription the discountDescription to set
     */
    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }

}
