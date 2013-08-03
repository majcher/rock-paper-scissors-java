package pl.mmajcherski.rps.domain.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Players implements Iterable<Player> {
	
	private Map<PlayerId, Player> players = new HashMap<>();

	public void add(Player player) {
		players.put(player.getId(), player);
	}
	
	public Set<PlayerId> getAllIds() {
		return Collections.unmodifiableSet(players.keySet());
	}
	
	public Player getPlayerById(PlayerId playerId) {
		checkPlayersContain(playerId);
		
		return players.get(playerId);
	}
	
	private void checkPlayersContain(PlayerId playerId) {
		if (!contains(playerId)) {
			throw new IllegalArgumentException(String.format("No player with ID: %s in this game", playerId));
		}
	}

	public Player getOpponentOf(PlayerId playerId) {
		List<PlayerId> allPlayersIds = new ArrayList<>(players.keySet());
		allPlayersIds.remove(playerId);
		return players.get(allPlayersIds.get(0));
	}
	
	public boolean contains(PlayerId playerId) {
		return players.containsKey(playerId);
	}
	
	public int size() {
		return players.size();
	}

	@Override
	public Iterator<Player> iterator() {
		return Collections.unmodifiableCollection(players.values()).iterator();
	}
	
}
