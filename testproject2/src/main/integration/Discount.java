package main.integration;

public class Discount {

	private double discountSum;
	private double discountFraction;
    private String discountDescription;

	public Discount(double discountSum, double discountFraction, String discountDescription)  {
        this.discountSum = discountSum;
        this.discountFraction = discountFraction;
        this.discountDescription = discountDescription;
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
     * @return double return the discountFraction
     */
    public double getDiscountFraction() {
        return discountFraction;
    }
    /**
     * @param discountFraction the discountFraction to set
     */
    public void setDiscountFraction(double discountFraction) {
        this.discountFraction = discountFraction;
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
