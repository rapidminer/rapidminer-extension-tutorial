/**
 * RapidMiner
 *
 * Copyright (C) 2001-2014 by RapidMiner and the contributors
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

import java.util.HashMap;
import java.util.Map;

import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.ResultObjectAdapter;


/**
 * This class contains the game date, recorded during runtime of the game.
 *
 * @author Sebastian Land
 */
public class GameDataIOObject extends ResultObjectAdapter {

	private static final long serialVersionUID = 1725159059797569345L;

	private GameData data;
	private Map<String, Double> valueMap = new HashMap<String, Double>();

	/**
	 *
	 * @param data
	 *            the game data to wrap in an {@link IOObject}.
	 */
	public GameDataIOObject(GameData data) {
		this.data = data;
	}

	public GameData getGameData() {
		return data;
	}

	/**
	 * This sets a value of this GameDataIOObject, which is later on extracted as an attribute in
	 * the resulting ExampleSet.
	 *
	 * @param identifier
	 *            the identifier
	 * @param value
	 *            the value
	 */
	public void setValue(String identifier, double value) {
		valueMap.put(identifier, value);
	}

	/**
	 * For extracting all identifiers / values
	 *
	 * @return the map containing identifiers and values
	 */
	public Map<String, Double> getValueMap() {
		return valueMap;
	}

	@Override
	public String toResultString() {
		StringBuilder builder = new StringBuilder();
		builder.append("The following values have been extracted:\n");
		for (String key : getValueMap().keySet()) {
			builder.append(key + ":\t" + getValueMap().get(key) + "\n");
		}

		builder.append("\n\nThe data: \n");
		builder.append(data.toString());

		return builder.toString();
	}
}
