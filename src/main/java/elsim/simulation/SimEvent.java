package main.java.elsim.simulation;

import java.time.LocalDateTime;

public abstract class SimEvent {
	private LocalDateTime timestamp;

	public SimEvent(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public abstract void processEvent();

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
