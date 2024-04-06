package controller.fileio;

import java.io.IOException;
import java.util.List;
import model.PortfolioInterface;

/**
 * Interface for the FileIO class.
 */
public interface FileIO {

  /**
   * Reads the file and returns a list of portfolios.
   *
   * @param filePath The path of the file to read
   * @return List of portfolios
   */
  List<PortfolioInterface> readFile(String filePath) throws IOException;

  /**
   * Writes to the file.
   *
   * @param portfolio The list of portfolios to write to the file
   * @param filePath  The path of the file to write the portfolios to
   * @return True if the write was successful, false otherwise
   */
  Boolean writeFile(List<PortfolioInterface> portfolio, String filePath) throws IOException;
}