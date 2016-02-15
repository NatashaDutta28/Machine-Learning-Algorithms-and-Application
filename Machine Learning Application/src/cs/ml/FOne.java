package cs.ml;

import java.io.IOException;


import org.apache.log4j.Logger;

public class FOne implements Strategy {
	static final int INITIAL_BET = 100;
	Util util;

	final static Logger logger = Logger.getLogger(FOne.class);

	public FOne() {
		super();
	}

	public FOne(String fileName) {
		util = new Util(fileName);
	}

	public void playCraps(int initialBalance) throws IOException {
		// TODO Auto-generated method stub
		int game = 1;
		int avlblBalance = initialBalance;

		while (game <= 10 && avlblBalance > 0) {

			int diceRoll = util.rollDice();
			if (diceRoll == 7) {
				logger.info(Constants.WIN);
				avlblBalance = avlblBalance + INITIAL_BET;
				util.writeOutput(game, INITIAL_BET, avlblBalance);
				game++;

			} else if (diceRoll == 2 || diceRoll == 3 || diceRoll == 12) {
				logger.info(Constants.LOOSE);
				avlblBalance = avlblBalance - INITIAL_BET;
				util.writeOutput(game, INITIAL_BET, avlblBalance);
				game++;
			} else {

				int newDiceRoll = 0;
				logger.info(Constants.LOOSE);
				avlblBalance = avlblBalance - INITIAL_BET;
				util.writeOutput(game, INITIAL_BET, avlblBalance);
				game++;

				while (game <= 10 && avlblBalance > 0) {
					newDiceRoll = util.rollDice();

					if (newDiceRoll == 7) {
						logger.info(Constants.LOOSE);
						avlblBalance = avlblBalance - INITIAL_BET;
						util.writeOutput(game, INITIAL_BET, avlblBalance);
						game++;
						break;

					} else if (newDiceRoll == diceRoll) {
						logger.info(Constants.WIN);
						avlblBalance = avlblBalance + INITIAL_BET;
						util.writeOutput(game, INITIAL_BET, avlblBalance);
						game++;
						break;
					} else {
						logger.info(Constants.LOOSE);
						avlblBalance = avlblBalance - INITIAL_BET;
						util.writeOutput(game, INITIAL_BET, avlblBalance);
						game++;
					}
				}

			}

		}
		String newLine = "---------------------------------------------------";
		util.writeResult(newLine);
		String result = "Strategy \t  Number Of Games \t Ending Balance";
		util.writeResult(result);
		result = " 1 \t\t\t" + (game - 1) + "\t\t\t" + avlblBalance;
		util.writeResult(result);

	}

}
