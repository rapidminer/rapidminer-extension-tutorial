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

import java.util.LinkedList;
import java.util.List;

import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.OperatorChain;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.operator.ports.OutputPort;
import com.rapidminer.operator.ports.metadata.SubprocessTransformRule;


/**
 * This operator will feed all GameData objects to it's inner sub process and will execute it in
 * order to build an example set from the extracted key value pairs.
 *
 * @author Sebastian Land
 */
public class ProcessGameDataOperator extends OperatorChain {

	private OutputPort innerGameDataSource = getSubprocess(0).getInnerSources().createPort("game data");
	private InputPort innerGameDataSink = getSubprocess(0).getInnerSinks().createPort("game data");
	private OutputPort exampleSetOutput = getOutputPorts().createPort("example set");

	/**
	 * The default operator constructor.
	 *
	 * @param description
	 *            the operator description
	 */
	public ProcessGameDataOperator(OperatorDescription description) {
		super(description, "Property Extraction");

		/**
		 * very short and insufficient meta data transformation: Should be much more sophisticated.
		 */

		getTransformer().addGenerationRule(innerGameDataSource, GameDataIOObject.class);
		getTransformer().addRule(new SubprocessTransformRule(getSubprocess(0)));
		getTransformer().addGenerationRule(exampleSetOutput, ExampleSet.class);
	}

	@Override
	public void doWork() throws OperatorException {
		List<GameData> loadedData = new LinkedList<GameData>();
		loadedData.add(new GameData());
		/**
		 * Iterate over all GameData objects and feed them through the subprocess one by one.
		 * Extending ExampleSet each time by one example
		 */
		ExampleSet resultSet = null;
		for (GameData gameData : loadedData) {
			innerGameDataSource.deliver(new GameDataIOObject(gameData));
			getSubprocess(0).execute();
			GameDataIOObject result = innerGameDataSink.getData(GameDataIOObject.class);

			if (resultSet == null) {
				resultSet = createInitialExampleSet(result);
			} else {
				extendExampleSet(resultSet, result);
			}
		}

		exampleSetOutput.deliver(resultSet);
	}

	/**
	 * This method has to extend the given resultSet by the example extracted from the result
	 * object.
	 */
	private void extendExampleSet(ExampleSet resultSet, GameDataIOObject result) {}

	/**
	 * This will create the first initial example set from the result object. At first the
	 * MemoryExampleTable will be created to storing the data, then for each entry in the map an
	 * attribute is created and put together into an example set.
	 */
	private ExampleSet createInitialExampleSet(GameDataIOObject result) {
		return null;
	}
}
