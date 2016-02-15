package cs.ml;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

public class Main {

	final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws IOException {
		
		BasicConfigurator.configure();

		int round = 1;
		FOne fOne = new FOne(Constants.FILE_NAME);
		FTwo fTwo = new FTwo(Constants.FILE_NAME);
		FThree fThree = new FThree(Constants.FILE_NAME);
		try {
			while (round <= Constants.MAX_ROUND) {
				logger.info("Round :: " + round);
				fOne.playCraps(Constants.INITIAL_BALANCE);
				fTwo.playCraps(Constants.INITIAL_BALANCE);
				fThree.playCraps(Constants.INITIAL_BALANCE);
				round++;

			}
		} catch (IOException ie) {
			logger.error("IO Exception :" + ie.getMessage());
		} catch (Exception e) {
			logger.error("Exception :" + e.getMessage());
		}
	}

}