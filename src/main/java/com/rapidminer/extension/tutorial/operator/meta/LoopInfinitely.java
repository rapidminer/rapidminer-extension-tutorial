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
package com.rapidminer.extension.tutorial.operator.meta;

import java.util.List;

import com.rapidminer.operator.OperatorChain;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.ports.PortPairExtender;
import com.rapidminer.operator.ports.metadata.SubprocessTransformRule;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.ParameterTypeBoolean;
import com.rapidminer.parameter.ParameterTypeInt;
import com.rapidminer.parameter.conditions.BooleanParameterCondition;


/**
 * This super operator will execute it's inner process infinitely once every minute.
 *
 * @author Sebastian Land
 */
public class LoopInfinitely extends OperatorChain {

	/**
	 * The key for frequency parameter.
	 */
	public static final String PARAMETER_FREQUENCY = "frequency";

	/**
	 * The parameter key for the restrict frequency parameter.
	 */
	public static final String PARAMETER_RESTRICT_FREQUENCY = "restrict_frequency";

	private final PortPairExtender inputPortPairExtender = new PortPairExtender("input", getInputPorts(), getSubprocess(0)
			.getInnerSources());

	/**
	 * The default constructor needed in exactly this signature
	 *
	 * @param description
	 *            the operator description
	 */
	public LoopInfinitely(OperatorDescription description) {
		super(description, "Executed Process");

		inputPortPairExtender.start();

		getTransformer().addRule(inputPortPairExtender.makePassThroughRule());
		getTransformer().addRule(new SubprocessTransformRule(getSubprocess(0)));
	}

	@Override
	public void doWork() throws OperatorException {
		int secondsBetweenStarts = getParameterAsInt(PARAMETER_FREQUENCY);

		inputPortPairExtender.passDataThrough();
		while (true) {
			checkForStop();
			long start = System.currentTimeMillis();
			getSubprocess(0).execute();
			long end = System.currentTimeMillis();

			long wait = secondsBetweenStarts * 1000 - (end - start);
			if (wait > 0) {  // if we have to wait anyway
				try {
					Thread.sleep(wait);
				} catch (InterruptedException e) {
					// Don't do anything: Only executing too early
				}
			}
		}
	}

	@Override
	public List<ParameterType> getParameterTypes() {
		List<ParameterType> types = super.getParameterTypes();
		types.add(new ParameterTypeBoolean(PARAMETER_RESTRICT_FREQUENCY,
				"If checked, the frequency of subprocess execution might be restricted.", false, false));

		ParameterType type = new ParameterTypeInt(PARAMETER_FREQUENCY,
				"This parameter defines the number of seconds between the start of two subsequent subprocess executions.",
				1, Integer.MAX_VALUE, 5, false);
		type.registerDependencyCondition(new BooleanParameterCondition(this, PARAMETER_RESTRICT_FREQUENCY, true, true));
		types.add(type);

		return types;
	}
}
