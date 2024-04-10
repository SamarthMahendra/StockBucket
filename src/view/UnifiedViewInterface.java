package view;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import model.Tradable;

/**
 * Interface for the View (text view and GUI view).
 */
public interface UnifiedViewInterface {

  /**
   * Displays a message to the user.
   *
   * @param message The message to display.
   */
  void displayMessage(String message);

  /**
   * Displays an error message to the user.
   *
   * @param message The error message to display.
   */
  void displayError(String message);

  /**
   * Requests input from the user.
   *
   * @param prompt The prompt to display to the user.
   * @return The user's input.
   */
  String requestInput(String prompt);

  /**
   * Displays a message to prompt for input from the user.
   *
   * @param message The message to display to the user as a prompt.
   */
  void inputMessage(String message);

  /**
   * Reads a line of input from the user.
   *
   * @return The user's input.
   */
  String readLine();

  /**
   * Requests a date from the user.
   *
   * @param prompt The prompt to display to the user.
   * @return The user's input date.
   */
  LocalDate requestDate(String prompt);

  /**
   * Displays a performance chart to the user.
   *
   * @param data The data to display in the chart.
   */
  void displayPerformanceChart(Map<LocalDate, BigDecimal> data);

  /**
   * Displays the main menu to the user.
   *
   * @throws IOException if an I/O error occurs.
   */
  void displayMainMenu() throws IOException;

  /**
   * Reads an integer from the user.
   *
   * @return The integer read from the user.
   */
  Integer readInt();

  /**
   * Displays the flexible portfolio menu to the user.
   *
   * @throws IOException if an I/O error occurs.
   */
  void displayFlexiblePortfolioMenu() throws IOException;

  /**
   * Displays the crossover days for a given stock within a specified date range.
   *
   * @param symbol    Stock symbol.
   * @param startDate Start date.
   * @param endDate   End date.
   * @param data      List of crossover days.
   * @throws IOException if an I/O error occurs.
   */
  void displayCrossoverDays(String symbol, LocalDate startDate, LocalDate endDate,
      List<LocalDate> data) throws IOException;

  /**
   * Displays the normal portfolio menu to the user.
   *
   * @throws IOException if an I/O error occurs.
   */
  void displayNormalPortfolioMenu() throws IOException;

  /**
   * Displays the moving crossover days for a given stock within a specified date range and moving average periods.
   *
   * @param symbol            Stock symbol.
   * @param startDate         Start date.
   * @param endDate           End date.
   * @param shortMovingPeriod Short moving period.
   * @param longMovingPeriod  Long moving period.
   * @param data              List of crossover days.
   * @throws IOException if an I/O error occurs.
   */
  void displayMovingCrossoverDays(String symbol, LocalDate startDate, LocalDate endDate,
      int shortMovingPeriod, int longMovingPeriod, Map<String, Object> data) throws IOException;

  /**
   * Displays the available portfolios to the user.
   *
   * @param portfolios List of available portfolios.
   * @throws IOException if an I/O error occurs.
   */
  void displayAvailablePortfolios(List<String> portfolios) throws IOException;

  /**
   * Displays a message indicating that a stock has been successfully added to a portfolio.
   *
   * @param portfolioName Portfolio name.
   * @param symbol        Stock symbol.
   * @param quantity      Quantity of stock.
   * @throws IOException if an I/O error occurs.
   */
  void displayStockAdded(String portfolioName, String symbol, int quantity) throws IOException;

  /**
   * Displays information about portfolio investment.
   *
   * @param name      Portfolio name.
   * @param dateInput Date input.
   * @param string    Additional information.
   * @throws IOException if an I/O error occurs.
   */
  void displayPortfolioInvestment(String name, String dateInput, String string) throws IOException;

  /**
   * Displays information about a sold stock.
   *
   * @param portfolioName Portfolio name.
   * @param symbol        Stock symbol.
   * @param quantity      Quantity of stock.
   * @throws IOException if an I/O error occurs.
   */
  void displayStockSold(String portfolioName, String symbol, int quantity) throws IOException;

  /**
   * Displays details of a portfolio.
   *
   * @param name   Portfolio name.
   * @param stocks List of stocks in the portfolio.
   * @throws IOException if an I/O error occurs.
   */
  void displayPortfolioDetails(String name, List<Tradable> stocks) throws IOException;

  /**
   * Displays a message indicating successful saving of data to a file.
   *
   * @param filePath File path.
   * @throws IOException if an I/O error occurs.
   */
  void displaySaveSuccess(String filePath) throws IOException;

  /**
   * Displays a message indicating successful loading of data from a file.
   *
   * @throws IOException if an I/O error occurs.
   */
  void displayLoadSuccess() throws IOException;

  /**
   * Displays information about portfolio value.
   *
   * @param name      Portfolio name.
   * @param dateInput Date input.
   * @param string    Additional information.
   * @throws IOException if an I/O error occurs.
   */
  void displayPortfolioValue(String name, String dateInput, String string) throws IOException;
}
