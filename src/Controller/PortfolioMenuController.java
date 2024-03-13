package Controller;
import Model.Portfolio;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

/**
 * Class to represent the view for the portfolio.
 */
public class PortfolioMenuController implements PortfolioMenuControllerInterface {

  private static final Scanner scanner = new Scanner(System.in);
  private final PortfolioControllerInterface portfolioController;

  public PortfolioMenuController(PortfolioControllerInterface portfolioController) {
    this.portfolioController = portfolioController;
  }

  /**
   * Display the main menu.
   */
  public void displayMainMenu() {
    boolean running = true;
    while (running) {
      System.out.println("\nPortfolio Management System:");
      System.out.println("1. Create a new portfolio");
      System.out.println("2. Examine a portfolio");
      System.out.println("3. Calculate portfolio value");
      System.out.println("4. Save portfolio");
      System.out.println("5. Load portfolio");
      System.out.println("6. Exit");
      System.out.print("Select an option: ");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
        case 1:
          this.createNewPortfolio();
          break;
        case 2:
          this.examinePortfolio();
          break;
        case 3:
          this.calculatePortfolioValue();
          break;
        case 4:
          this.savePortfolio();
          break;
        case 5:
          this.loadPortfolio();
          break;
        case 6:
          System.out.println("Exiting...");
          running = false;
          break;
        default:
          System.out.println("Invalid option. Please try again.");
      }
    }
  }

  /**
   * Create a new portfolio.
   */
  public void createNewPortfolio() {
    System.out.println("Enter new portfolio name:");
    String name = scanner.nextLine().trim();
    Payload payload = portfolioController.createNewPortfolio(name);
    if (this.printIfError(payload)) {
      return;
    }
    Portfolio newPortfolio = (Portfolio) payload.getData();
    boolean flag = true;
    System.out.println("Enter the stocks you want to add to the portfolio");
    int quantity =0 ;
    while (flag) {
      System.out.println("Enter the stock symbol:");
      String symbol = scanner.nextLine().trim();
      while (true) {
        System.out.println("Enter the quantity of the stock:");

        if (scanner.hasNextInt()) {
          quantity = scanner.nextInt();
          scanner.nextLine();
          if (quantity > 0) {
            break;
          } else {
            System.out.println("Quantity must be greater than 0");
          }
        } else {
          System.out.println("Cannot purchase Fractional Shares");
          scanner.nextLine();
        }
      }
      LocalDate date;
      while (true) {
        System.out.println("Enter the purchase date (YYYY-MM-DD):");
        String dateString = scanner.nextLine().trim();
        try {
          date = LocalDate.parse(dateString);
        } catch (Exception e) {
          System.out.println("Invalid date format. Please try again.");
          continue;
        }
        date = LocalDate.parse(dateString);
        if (!date.isBefore(LocalDate.now())) {
          System.out.println("Date must be before today. Please try again.");
          continue;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
          System.out.println("Date must be on a weekday. Please try again.");
          continue;
        }
        break;
      }
      payload = portfolioController.addStockToPortfolio(newPortfolio, symbol, quantity, date);
      if (this.printIfError(payload)) {
        return;
      }
      System.out.println("Press q to exit, Press n to go on");
      String exitChar = scanner.nextLine().trim();
      if (exitChar.equals("q")) {
        flag = false;
      }

    }
    System.out.println("Portfolio '" + name + "' has been created and populated.");
  }

  /**
   * Examine a portfolio.
   */
  public void examinePortfolio() {
    System.out.println("Available portfolios:");
    portfolioController.getPortfolioService().listPortfolioNames().forEach(System.out::println);

    System.out.println("Enter the name of the portfolio to examine:");
    String name = scanner.nextLine().trim();
    portfolioController.getPortfolioService().getPortfolioByName(name)
        .ifPresentOrElse(
            portfolio -> {
              System.out.println("Stocks in " + name + ":");
              portfolio.getStocks().forEach(stock ->
                  System.out.println(stock.getSymbol() + " - Quantity: " + stock.getQuantity()
                      + ", Purchase Price: " + stock.getPurchasePrice() + ", Purchase Date: "
                      + stock.getPurchaseDate()));
            },
            () -> System.out.println("Portfolio not found.")
        );
  }

  /**
   * Calculate the portfolio value.
   */
  public void calculatePortfolioValue() {
    System.out.println("Enter the name of the portfolio:");
    String name = scanner.nextLine().trim();
    System.out.println("Enter the date (YYYY-MM-DD) to calculate the portfolio value:");
    String dateInput = scanner.nextLine().trim();
    try {
      Payload payload = portfolioController.calculatePortfolioValue(name,
          LocalDate.parse(dateInput));
      if (this.printIfError(payload)) {
        return;
      }
      System.out.println("Value of the portfolio '" + name + "' on " + dateInput + ": "
          + ((Optional<BigDecimal>) payload.getData()).get());
    } catch (Exception e) {
      System.out.println("Error calculating portfolio value: " + e.getMessage());
    }
  }

  /**
   * Save the portfolio.
   */
  public void savePortfolio() {
    System.out.println("Enter the file path to save the portfolio:");
    String filePath = scanner.nextLine().trim();
    Object payload = portfolioController.savePortfolio(filePath);
    if (((Optional<Payload>) payload).isPresent() && ((Optional<Payload>) payload).get()
        .isError()) {
      System.out.println("Error: " + ((Optional<Payload>) payload).get().getMessage());
      return;
    }
    System.out.println("Portfolios have been saved successfully to " + filePath);
  }

  /**
   * Load the portfolio.
   */
  public void loadPortfolio() {
    System.out.println("Enter the file path to load portfolios from:");
    String filePath = scanner.nextLine().trim();
    Object payload = portfolioController.loadPortfolio(filePath);
    if (Objects.nonNull(payload) && ((Payload) payload).isError()) {
      System.out.println("Error: " + payload);
      return;
    }
    System.out.println("Portfolios have been loaded successfully.");
  }

  private Boolean printIfError(Payload payload) {
    if (payload.isError()) {
      System.out.println("Error: " + payload.getMessage());
      return true;
    }
    return false;
  }
}