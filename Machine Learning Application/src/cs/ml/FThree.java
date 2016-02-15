package cs.ml;

import java.io.IOException;


import org.apache.log4j.Logger;

public class FThree implements Strategy {

	Util util;
	final static Logger logger = Logger.getLogger(FThree.class);

	public FThree() {
		super();
	}

	public FThree(String fileName) {
		util = new Util(fileName);
	}

	public void playCraps(int initialBalance) throws IOException {
		int game = 1;
		int avlblBalance = initialBalance;
		int bet = Constants.INITIAL_BET;

		while (game <= 10 && avlblBalance > 0) {
			int diceRoll = util.rollDice();

			if (diceRoll == 7) {
				logger.info(Constants.WIN);
				avlblBalance = avlblBalance + bet;
				util.writeOutput(game, bet, avlblBalance);
				bet = util.setBet(avlblBalance, bet);
				game++;

			} else if (diceRoll == 2 || diceRoll == 3 || diceRoll == 12) {
				logger.info(Constants.LOOSE);
				avlblBalance = avlblBalance - bet;
				util.writeOutput(game, bet, avlblBalance);
				bet = Constants.INITIAL_BET;
				game++;
			} else {
				int newDiceRoll = 0;
				logger.info(Constants.LOOSE);
				avlblBalance = avlblBalance - bet;
				util.writeOutput(game, bet, avlblBalance);
				bet = Constants.INITIAL_BET;
				game++;

				while (game <= 10 && avlblBalance > 0) {
					newDiceRoll = util.rollDice();
					if (newDiceRoll == 7) {
						logger.info(Constants.LOOSE);
						avlblBalance = avlblBalance - bet;
						util.writeOutput(game, bet, avlblBalance);
						bet = Constants.INITIAL_BET;
						game++;
						break;

					} else if (newDiceRoll == diceRoll) {
						logger.info(Constants.WIN);
						avlblBalance = avlblBalance + bet;
						util.writeOutput(game, bet, avlblBalance);
						bet = util.setBet(avlblBalance, bet);
						game++;
						break;
					} else {
						logger.info(Constants.LOOSE);
						avlblBalance = avlblBalance - bet;
						util.writeOutput(game, bet, avlblBalance);
						bet = Constants.INITIAL_BET;
						game++;
					}
				}

			}

		}

		String result = " 3 \t\t\t" + (game - 1) + "\t\t\t" + avlblBalance;
		util.writeResult(result);
	}

}
