package Controller;

import Model.Service.StockService;


/**
 * Main class for the Portfolio Management System.
 */
public class Main {

  /**
   * Main method for the Portfolio Management System.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    PortfolioControllerInterface portfolioController = new PortfolioController(
        new StockService("W0M1JOKC82EZEQA8"));
    PortfolioMenuControllerInterface controller = new PortfolioMenuController(portfolioController);
    // interaction with the user
    // [Main] -> [PortfolioView] -> [PortfolioController] -> [PortfolioService] -> [Model]
    controller.displayMainMenu();
  }
}