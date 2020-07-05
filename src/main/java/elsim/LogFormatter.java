package main.java.elsim;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A custom formatter for our log files.
 * @author jbook
 */
public class LogFormatter extends Formatter {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String format(LogRecord record) {
		var sourceClassFull = record.getSourceClassName();
		String sourceClass;
		if (sourceClassFull == null) {
			sourceClass = "unknown";
		} else {
			var split = record.getSourceClassName().split("\\.");
			sourceClass = split[split.length - 1];
		}
		var level = record.getLevel().toString();
		var msg = record.getMessage();

		return String.format("%s:\t[%s] %s\n", level, sourceClass, msg);
	}
}
