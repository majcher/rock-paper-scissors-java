package pl.mmajcherski.rps.ui;

public enum GameMode {

	HUMAN_COMPUTER ("Human vs. Computer"),
	COMPUTER_COMPUTER ("Computer vs. Computer");

	private String fullName;
	
	private GameMode(final String fullName) {
		this.fullName = fullName;
	}
	
	public String getFullName() {
		return fullName;
	}
	
}
