package Model.Transactions;

import java.math.BigDecimal;

public class SaleInfo implements TranactionInfo{

  /**
   * The quantity of the stock. Sell price of the stock.
   */
  private int quantity;
  private final BigDecimal sellPrice;

  /**
   * Constructor for the SaleInfo class.
   *
   * @param quantity  The quantity of the stock.
   * @param sellPrice The sell price of the stock.
   */
  public SaleInfo(int quantity, BigDecimal sellPrice) {
    this.quantity = quantity;
    this.sellPrice = sellPrice;
  }

  /**
   * Getter for the quantity of the stock.
   *
   * @return The quantity of the stock.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Get the purchase price of the stock.
   *
   * @return The purchase price of the stock.
   */
  @Override
  public BigDecimal getPrice() {
    return this.sellPrice;
  }

}