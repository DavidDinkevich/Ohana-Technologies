package main;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class HistoryManager {
	private Session session;
	private List<Entry> entries;
	private float netCost;
	
	public HistoryManager(Session session) {
		entries = new ArrayList<>();
		this.session = session;
	}
	
	public void addEntry(Entry entry) {
		entries.add(entry);
		netCost += entry.getAction().getCost();
	}
	
	public Entry getEntry(int timestamp) {
		for (Entry e : entries) {
			if (e.getTimestamp() == timestamp)
				return e;
		}
		return null;
	}
	
	public boolean removeEntry(int timestamp) {
		// I can do this because only the timestamp matters when
		// comparing entries.
		Entry e = new Entry(timestamp, null, null);
		boolean removed = entries.remove(e);
		if (removed)
			netCost -= e.getAction().getCost();
		return removed;
	}
	
	public List<Entry> getEntries() {
		return Collections.unmodifiableList(entries);
	}
	
	public float getNetCost() {
		return netCost;
	}
	
	public void printData() {
		System.out.print("Desired inside temp: " + session.getDesiredTemp());
		System.out.println("  Net Cost: " + netCost);
		System.out.println("-----------------");
		entries.forEach(System.out::println);
	}
	
	public static class Entry {
		private final int timestamp;
		private final Container container;
		private final Action action;
		
		public Entry(int timestamp, Container container, Action action) {
			this.timestamp = timestamp;
			this.container = container;
			this.action = action;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (!(o instanceof Entry))
				return false;
			Entry other = (Entry)o;
			return other.timestamp == timestamp;
		}
		
		@Override
		public int hashCode() {
			return timestamp;
		}
		
		@Override
		public String toString() {
			return timestamp + "," + container.getInsideTemp() + "," +
					container.getOutsideTemp() + "," + action;
		}

		public int getTimestamp() {
			return timestamp;
		}
		
		public Container getContainer() {
			return container;
		}
		
		public Action getAction() {
			return action;
		}
		
	}
}
