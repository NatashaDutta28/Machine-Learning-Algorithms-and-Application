package cs.ml;

import java.io.IOException;
import org.apache.log4j.Logger;

public class FTwo implements Strategy {

	Util util;
	final static Logger logger = Logger.getLogger(FOne.class);

	public FTwo() {
		super();

	}

	public FTwo(String fileName) {
		util = new Util(fileName);
		// TODO Auto-generated constructor stub

	}

	public void playCraps(int initialBalance) throws IOException {
		// TODO Auto-generated method stub
		int game = 1;
		int avlblBalance = initialBalance;
		int bet = Constants.INITIAL_BET;

		while (game <= 10 && avlblBalance > 0) {
			int diceRoll = util.rollDice();

			if (diceRoll == 7) {
				logger.info(Constants.WIN);
				avlblBalance = avlblBalance + bet;
				util.writeOutput(game, bet, avlblBalance);
				bet = Constants.INITIAL_BET;
				game++;

			} else if (diceRoll == 2 || diceRoll == 3 || diceRoll == 12) {
				logger.info(Constants.LOOSE);
				avlblBalance = avlblBalance - bet;
				util.writeOutput(game, bet, avlblBalance);
				bet = util.setBet(avlblBalance, bet);
				game++;
			} else {
				int newDiceRoll = 0;
				logger.info(Constants.LOOSE);
				avlblBalance = avlblBalance - bet;
				util.writeOutput(game, bet, avlblBalance);
				bet = util.setBet(avlblBalance, bet);
				game++;
				if (bet <= 0) {
					break;
				} else {
					while (game <= 10 && avlblBalance > 0) {
						newDiceRoll = util.rollDice();
						if (newDiceRoll == 7) {
							logger.info(Constants.LOOSE);
							avlblBalance = avlblBalance - bet;
							util.writeOutput(game, bet, avlblBalance);
							game++;
							bet = util.setBet(avlblBalance, bet);
							break;

						} else if (newDiceRoll == diceRoll) {
							logger.info(Constants.WIN);
							avlblBalance = avlblBalance + bet;
							util.writeOutput(game, bet, avlblBalance);
							game++;
							bet = Constants.INITIAL_BET;
							break;
						} else {
							logger.info(Constants.LOOSE);
							avlblBalance = avlblBalance - bet;
							util.writeOutput(game, bet, avlblBalance);
							bet = util.setBet(avlblBalance, bet);
							game++;
						}
					}

				}
			}

		}
		String result = " 2 \t\t\t" + (game - 1) + "\t\t\t" + avlblBalance;
		util.writeResult(result);

	}

}
