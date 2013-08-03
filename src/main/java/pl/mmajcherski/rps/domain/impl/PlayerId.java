package pl.mmajcherski.rps.domain.impl;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public final class PlayerId {

	private final String value;
	
	public PlayerId(String value) {
		requireNonNull(value, "Player must have non-null ID");
		
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PlayerId other = (PlayerId) obj;
		return Objects.equals(this.value, other.value);
	}

	@Override
	public String toString() {
		return value;
	}

}
