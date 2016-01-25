/**
 * RapidMiner
 *
 * Copyright (C) 2001-2016 by RapidMiner and the contributors
 *
 * Complete list of developers available at our web site:
 *
 *      https://rapidminer.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.rapidminer.operator.game;

import java.io.Serializable;

import com.rapidminer.tools.RandomGenerator;


/**
 * This is a class containing information about a game setting. Some situation in which decisions
 * must be made. We want to use methods of statistical learning for making these decisions.
 *
 * @author Sebastian Land
 */
public class GameData implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @return dummy property of the situation
	 */
	public int getAge() {
		return 10;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 256; i++) {
			builder.append(RandomGenerator.getGlobalRandomGenerator().nextString(32));
			builder.append("\n");
		}
		return builder.toString();
	}
}
