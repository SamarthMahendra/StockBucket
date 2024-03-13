package controller;

import model.service.StockService;
import view.View;


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
    PortfolioControllerInterface portfolioController = new PortfolioController(new StockService("W0M1JOKC82EZEQA8"));
    View view = new View();
    PortfolioMenuControllerInterface controller = new PortfolioMenuController(portfolioController,
        view);
    // interaction with the user
    // [View] <- [PortfolioMenuController] -> [PortfolioController] -> [PortfolioService] -> [Model]
    controller.displayMainMenu();
  }
}
