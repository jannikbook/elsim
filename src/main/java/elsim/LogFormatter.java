package main.java.elsim;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
	@Override
	public String format(LogRecord record) {
		var split = record.getSourceClassName().split("\\.");
		var sourceClass = split[split.length - 1];
		var level = record.getLevel().toString();
		var msg = record.getMessage();

		return String.format("%s: [%s] %s\n", level, sourceClass, msg);
	}
}
