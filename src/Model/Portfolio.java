package Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Model.Service.StockServiceInterface;

/**
 * Class to represent a portfolio of stocks.
 */
public class Portfolio implements PortfolioInterface {

  private final String name;
  private List<Tradable> stocks = new ArrayList<>();

  /**
   * Constructor for the Portfolio class.
   *
   * @param name The name of the portfolio.
   */
  public Portfolio(String name) {
    this.name = name;
  }

  /**
   * Constructor for the Portfolio class.
   *
   * @param name The name of the portfolio.
   * @param initialStocks The initial stocks in the portfolio.
   */
  public Portfolio(String name, List<Stock> initialStocks) {
    this.name = name;
    this.stocks = new ArrayList<>(initialStocks);
  }

  /**
   * Adds a stock to the portfolio.
   *
   * @param symbol The symbol of the stock.
   * @param quantity The quantity of the stock.
   * @param purchasePrice The purchase price of the stock.
   * @param purchaseDate The date on which the stock was purchased.
   */
  public void addStock(String symbol, int quantity, BigDecimal purchasePrice, LocalDate purchaseDate) {
    // check if the stock is already in the portfolio - > s.buy else new Stock
    this.stocks.stream().filter(s -> s.getSymbol().equals(symbol)).findFirst().ifPresentOrElse(s -> s.buy(quantity, purchaseDate, purchasePrice), () -> this.stocks.add(new Stock(symbol, quantity, purchasePrice, purchaseDate)));
  }

  /**
   * Sell a stock from the portfolio.
   *
   * @param stock The stock to remove.
   * @param quantity The quantity of the stock to remove.
   * @param date The date of the sale.
   * @throws IllegalArgumentException if the stock is not in the portfolio.
   */
  public void sellStock(String stock, int quantity, LocalDate date, BigDecimal sellingPrice) throws IllegalArgumentException {
    // throw an if date is in the future
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Cannot sell stock in the future");
    }

    // stream through the stocks in the portfolio and find the stock to sell
    this.getStocks().stream()
        .filter(s -> s.getSymbol().equals(stock))
        .findFirst()
        .ifPresentOrElse(
            s -> {
              if (s.getQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough stock to sell");
              }
              s.sell(quantity, date, sellingPrice);
            },
            () -> {
              throw new IllegalArgumentException("Stock not found");
            }
        );
  }

  /**
   * calculates the total value of the portfolio on a given date.
   *
   * @param stockService The stock service to use to fetch stock prices.
   * @param date         The date on which to calculate the value.
   */
  @Override
  public BigDecimal calculateValue(StockServiceInterface stockService, LocalDate date) {
    return this.stocks.stream()
        .map(s -> s.calculateValue(stockService, date))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  /**
   * Get Money invested in this portfolio.
   */
  public BigDecimal calculateInvestment(LocalDate date) {
    BigDecimal investment = BigDecimal.ZERO;
    for (Tradable stock : this.stocks) {
      investment = investment.add(stock.calculateInvestment(date));
    }
    return investment;
  }

  /**
   * getter for the stocks in the portfolio.
   */
  public List<Tradable> getStocks() {
    return new ArrayList<>(stocks); // Return a copy to protect internal list
  }

  /**
   * Getter for the name of the portfolio.
   *
   * @return The name of the portfolio.
   */
  public String getName() {
    return name;
  }

  /**
   * Get stock quantity by symbol.
   */
  public int getStockQuantity(String symbol, LocalDate date) {
    return this.stocks.stream().filter(s -> s.getSymbol().equals(symbol)).findFirst().map(s -> s.getQuantity(date)).orElse(0);
  }
}
