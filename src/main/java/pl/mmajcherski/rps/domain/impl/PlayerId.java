package pl.mmajcherski.rps.domain.impl;

import java.util.Objects;

public class PlayerId {

	private String value;
	
	public PlayerId(String value) {
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
