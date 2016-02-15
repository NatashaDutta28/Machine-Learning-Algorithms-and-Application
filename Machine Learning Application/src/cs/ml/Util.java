package cs.ml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.apache.log4j.Logger;

public class Util {

	String fileName;
	final static Logger logger = Logger.getLogger(Util.class);

	public Util() {

	}

	public Util(String fileName) {
		this.fileName = fileName;
	}

	public int rollDice() {
		Random r = new Random();
		int dice1 = r.nextInt(6) + 1;
		int dice2 = r.nextInt(6) + 1;
		logger.info("Dice Roll : " + (dice1 + dice2));
		int diceRoll = dice1 + dice2;
		return diceRoll;
	}

	public void writeResult(String result) throws IOException {

		FileWriter fileWriter = new FileWriter(fileName, true);
		fileWriter.write(result);
		String newLine = System.getProperty("line.separator");
		fileWriter.write(newLine);
		fileWriter.close();
		logger.info(result);
	}

	public void writeOutput(int iteration, int bet, int endingBalance) {
		logger.info("Iteration : " + iteration + " Bet Amount: " + bet + " Remaining Balance : " + endingBalance);
		logger.info("---------------------------------------------------------------------------------");
	}

	public int setBet(int avlblBalance, int prevBet) {
		int newBet = 0;
		if (avlblBalance > 0) {
			if ((prevBet * 2) <= avlblBalance)
				newBet = prevBet * 2;
			else
				newBet = avlblBalance;

		}
		return newBet;

	}
}
