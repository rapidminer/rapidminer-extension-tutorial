/**
 * RapidMiner
 *
 * Copyright (C) 2001-2015 by RapidMiner and the contributors
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
package com.rapidminer.operator.game.extractors;

import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.game.GameDataIOObject;
import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.operator.ports.OutputPort;


/**
 * A simple extractor of properties of a game data object.
 *
 * @author Sebastian Land
 */
public class ExtractAgeOperator extends Operator {

	/** defining the ports */
	private InputPort gameDataInput = getInputPorts().createPort("game data", GameDataIOObject.class);
	private OutputPort gameDataOutput = getOutputPorts().createPort("game data");

	/**
	 * The default constructor needed in exactly this signature
	 *
	 * @param description
	 *            the operator description
	 */
	public ExtractAgeOperator(OperatorDescription description) {
		super(description);

		/** Adding a rule for meta data transformation: GameData will be passed through */
		getTransformer().addPassThroughRule(gameDataInput, gameDataOutput);
	}

	@Override
	public void doWork() throws OperatorException {
		GameDataIOObject input = gameDataInput.getData(GameDataIOObject.class);

		extractValues(input);

		gameDataOutput.deliver(input);
	}

	/**
	 * This method could extract arbitrary properties from the GameData and put it as a key value
	 * pair into the GameDataIOObject. Each pair will become a single attribute in the resulting
	 * ExampleSet and hence each execution of the subprocess must result in exactly the same number
	 * of pairs. Otherwise for some examples there are undefined attributes.
	 */
	private void extractValues(GameDataIOObject input) {
		input.setValue("Age", input.getGameData().getAge());
	}
}
