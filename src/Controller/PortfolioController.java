package Controller;

import Model.PortfolioInterface;
import Model.Service.PortfolioService;
import Model.Service.PortfolioServiceInterface;
import Model.Service.StockServiceInterface;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Controller class for the Portfolio Management System. This class provides basic functionality for
 * managing portfolios. It interacts with the PortfolioService and StockService classes to perform
 * operations on portfolios and stocks.
 */
public class PortfolioController implements PortfolioControllerInterface {

  private final PortfolioServiceInterface portfolioService;
  private final StockServiceInterface stockServiceInterface;


  /**
   * Constructor for the PortfolioControllerBasic class.
   *
   * @param stockService The StockService model object to be used by the controller.
   */
  public PortfolioController(StockServiceInterface stockService) {
    this.portfolioService = new PortfolioService(stockService);
    this.stockServiceInterface = stockService;
  }

  /**
   * Getter for the PortfolioService object.
   *
   * @return The PortfolioService object used by the controller.
   */
  public PortfolioServiceInterface getPortfolioService() {
    return portfolioService;
  }

  /**
   * Creates a new portfolio with the given name.
   *
   * @param name The name of the new portfolio.
   * @return The newly created Portfolio object.
   */
  public Payload createNewPortfolio(String name) {
    try {
      return new Payload(this.portfolioService.createNewPortfolio(name), "");
    } catch (IllegalArgumentException e) {
      return new Payload(null, e.getMessage());
    }
  }

  /**
   * Adds a stock to the given portfolio with the given symbol, quantity, and date.
   *
   * @param portfolio The portfolio to which the stock will be added.
   * @param symbol    The symbol of the stock to be added.
   * @param quantity  The quantity of the stock to be added.
   * @param date      The date on which the stock was purchased.
   */
  public Payload addStockToPortfolio(PortfolioInterface portfolio, String symbol, int quantity,
      LocalDate date) {
    try {
      return new Payload(this.portfolioService.addStockToPortfolio(portfolio.getName(), symbol,
          quantity, date), "");
    } catch (IllegalArgumentException e) {
      return new Payload(null, e.getMessage());
    }
  }

  /**
   * Calculates the value of the portfolio with the given name on the given date.
   *
   * @param name   The name of the portfolio for which the value will be calculated.
   * @param onDate The date on which the value of the portfolio will be calculated.
   * @return The value of the portfolio on the given date.
   */
  public Payload calculatePortfolioValue(String name, LocalDate onDate) {
    try {
      return new Payload(this.portfolioService.calculatePortfolioValue(name, onDate), "");
    } catch (IllegalArgumentException e) {
      return new Payload(null, e.getMessage());
    }
  }

  /**
   * Calculate total investment in a perticular portfolio by a given date.
   *
   * @param name   name of the portfolio
   * @param onDate date
   * @return total investment
   */
  public Payload calculateTotalInvestment(String name, LocalDate onDate) {
    try {
      return new Payload(this.portfolioService.calculatePortfolioInvestment(name, onDate), "");
    } catch (IllegalArgumentException e) {
      return new Payload(null, e.getMessage());
    }
  }

  /**
   * Sell a specific number of shares of a specific stock on a specified date from a given
   * portfolio
   */
  public Payload sellStockFromPortfolio(PortfolioInterface portfolio, String stockSymbol,
      int quantity,
      LocalDate date) {
    try {
      return new Payload(
          this.portfolioService.sellStockFromPortfolio(portfolio.getName(), stockSymbol, quantity,
              date), "");
    } catch (IllegalArgumentException e) {
      return new Payload(null, e.getMessage());
    }
  }

  /**
   * Saves the portfolios to a CSV file at the given file path.
   *
   * @param filePath The file path where the portfolios will be saved.
   * @throws IllegalArgumentException if there is an error saving the portfolios to the file.
   */
  public Payload savePortfolio(String filePath) throws IllegalArgumentException {
    try {
      this.portfolioService.savePortfoliosToCSV(filePath);
      return new Payload(null, "");
    } catch (Exception e) {
      return new Payload(null, e.getMessage());
    }
  }

  /**
   * Saves the cache to a CSV file at the given file path.
   *
   * @param filePath The file path where the cache will be saved.
   * @throws IllegalArgumentException if there is an error saving the cache to the file.
   */
  public Payload saveCache(String filePath) throws IllegalArgumentException {
    try {
      stockServiceInterface.saveCache(filePath);
      return new Payload(null, "");
    } catch (Exception e) {
      return new Payload(null, e.getMessage());
    }
  }

  /**
   * loads the cache from a CSV file at the given file path.
   *
   * @param filePath The file path from where the cache will be loaded.
   * @throws IllegalArgumentException if there is an error loading the cache from the file.
   */
  public Payload loadCache(String filePath) throws IllegalArgumentException {
    try {
      stockServiceInterface.loadCache(filePath);
      return new Payload(null, "");
    } catch (Exception e) {
      return new Payload(null, e.getMessage());
    }
  }

  /**
   * Loads portfolios from a CSV file at the given file path.
   *
   * @param filePath The file path from which the portfolios will be loaded.
   * @throws IllegalArgumentException if there is an error loading the portfolios from the file.
   */
  public Payload loadPortfolio(String filePath) throws IllegalArgumentException {
    try {
      return new Payload(null, this.portfolioService.loadPortfoliosFromCSV(filePath));
    } catch (Exception e) {
      return new Payload(null, e.getMessage());
    }
  }

  /**
   * get number of portfolios.
   *
   * @return number of portfolios
   */
  public int getNumPortfolios() {
    return this.portfolioService.getNumberOfPortfolios();
  }


  public StringBuilder GenGraph(String identifier, LocalDate startDate, LocalDate endDate)
      throws IllegalArgumentException {
    try {
      return this.portfolioService.plotPerformanceChart(identifier, startDate, endDate);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  /**
   * Finds the crossover days for a given stock symbol within a specified date range. A crossover
   * day is a day when the closing price of the stock is higher than the opening price.
   *
   * @param symbol    The symbol of the stock to analyze.
   * @param startDate The start date of the date range.
   * @param endDate   The end date of the date range.
   * @return A list of dates within the specified range that are crossover days.
   */
  public Payload findCrossoverDays(String symbol, LocalDate startDate, LocalDate endDate) {
    try {
      return new Payload(this.stockServiceInterface.findCrossoverDays(symbol, startDate, endDate),
          "");
    } catch (IllegalArgumentException e) {
      return new Payload(null, e.getMessage());
    }
  }

  public Payload inspectStockPerformance(String symbol, LocalDate date) {
    try {
      String performance = stockServiceInterface.inspectStockGainOrLoss(symbol, date);
      return new Payload(performance, "");
    } catch (IllegalArgumentException e) {
      return new Payload(null, e.getMessage());
    }
  }


  /**
   * Finds the moving crossover days for a given stock symbol within a specified date range. A
   * moving crossover day is a day when the closing price of the stock is higher than the moving
   * average.
   *
   * @param symbol            The symbol of the stock to analyze.
   * @param startDate         The start date of the date range.
   * @param endDate           The end date of the date range.
   * @param shortMovingPeriod The number of days to consider for the short moving average.
   * @param longMovingPeriod  The number of days to consider for the long moving average.
   * @return A list of dates within the specified range that are moving crossover days.
   */
  public Payload findMovingCrossoverDays(String symbol, LocalDate startDate, LocalDate endDate,
      int shortMovingPeriod, int longMovingPeriod) {
    try {
      return new Payload(this.stockServiceInterface.findMovingCrossoverDays(symbol, startDate,
          endDate, shortMovingPeriod, longMovingPeriod), "");
    } catch (IllegalArgumentException e) {
      return new Payload(null, e.getMessage());
    }

  }

  // Method to compute X-day moving average for a stock
  public Payload computeStockMovingAverage(String symbol, LocalDate endDate, int days) {
    try {
      BigDecimal average = stockServiceInterface.computeXDayMovingAverage(symbol, endDate,
          days);
      return new Payload(average, "");

    } catch (IllegalArgumentException e) {
      return new Payload(null, e.getMessage());
    }
  }
}


