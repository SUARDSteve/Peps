/**
This file is part of the CSC4509 teaching unit.

Copyright (C) 2012-2018 Télécom SudParis

This is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This software platform is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with the CSC4509 teaching unit. If not, see <http://www.gnu.org/licenses/>.

Initial developer(s): Denis Conan
Contributor(s):
 */
package common;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * This class contains the configuration of some logging facilities.
 * 
 * To recapitulate, logging levels are: TRACE, DEBUG, INFO, WARN, ERROR, FATAL.
 * 
 * @author Denis Conan
 * 
 */
public final class Log {
	/**
	 * states whether logging is enabled or not.
	 */
	public static final boolean LOG_ON = true;
	/**
	 * name of logger for the general part (config, etc.).
	 */
	public static final String LOGGER_NAME_GEN = "general";
	/**
	 * logger object for the general part.
	 */
	public static final Logger GEN = Logger.getLogger(LOGGER_NAME_GEN);
	/**
	 * name of logger for the communication part.
	 */
	public static final String LOGGER_NAME_COMM = "communication";
	/**
	 * logger object for the communication part.
	 */
	public static final Logger COMM = Logger.getLogger(LOGGER_NAME_COMM);
	/**
	 * name of logger for the testing part (used in JUnit classes).
	 */
	public static final String LOGGER_NAME_TEST = "test";
	/**
	 * logger object for the testing part.
	 */
	public static final Logger TEST = Logger.getLogger(LOGGER_NAME_TEST);

	/**
	 * static configuration, which can be changed by command line options.
	 */
	static {
		BasicConfigurator.configure();
		GEN.setLevel(Level.WARN);
		COMM.setLevel(Level.WARN);
		TEST.setLevel(Level.WARN);
	}

	/**
	 * private constructor to avoid instantiation.
	 */
	private Log() {
	}

	/**
	 * configures a logger to a level.
	 * 
	 * @param loggerName
	 *            the name of the logger.
	 * @param level
	 *            the level.
	 */
	public static void configureALogger(final String loggerName,
			final Level level) {
		if (loggerName == null) {
			return;
		}
		if (loggerName.equalsIgnoreCase(LOGGER_NAME_GEN)) {
			GEN.setLevel(level);
		} else if (loggerName.equalsIgnoreCase(LOGGER_NAME_COMM)) {
			COMM.setLevel(level);
		} else if (loggerName.equalsIgnoreCase(LOGGER_NAME_TEST)) {
			TEST.setLevel(level);
		}
	}
}
