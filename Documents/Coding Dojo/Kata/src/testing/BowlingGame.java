package testing;

public class BowlingGame {

	private int rolls[] = new int[21];
	private int currentRoll = 0;
	
	public void roll(int pinsDown) {
			rolls[currentRoll++] = pinsDown;
	}
	
	public int score() {
		int score = 0;
		int frameIndex = 0;
		for(int frame = 0; frame < 10; frame++) {
			if(isStrikeIndex(frameIndex)) {
				score += 10 + strikeBonus(frameIndex);
				frameIndex ++;
			}
			else if(isSpareIndex(frameIndex)) {
				score += 10 + spareBonus(frameIndex);
				frameIndex += 2;
			}else {
				score += rolls[frameIndex] + rolls[frameIndex+1];
				frameIndex += 2;	
			}
		}
		return score;
	}
	
	private int spareBonus(int frameIndex) {
		return rolls[frameIndex+2];
	}

	private int strikeBonus(int frameIndex) {
		return rolls[frameIndex+1] + rolls[frameIndex+2];
	}

	private boolean isStrikeIndex(int frameIndex) {
		return rolls[frameIndex] == 10;
	}

	private boolean isSpareIndex(int frameIndex) {
		return (rolls[frameIndex] + rolls[frameIndex + 1]) == 10;
	}

	public static void main(String[] args) {
		BowlingGame g = new BowlingGame();
		g.roll(0);
	}
	
}
