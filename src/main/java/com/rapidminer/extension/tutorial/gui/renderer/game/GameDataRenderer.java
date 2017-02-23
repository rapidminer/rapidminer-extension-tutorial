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
package com.rapidminer.extension.tutorial.gui.renderer.game;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.rapidminer.extension.tutorial.operator.game.GameDataIOObject;
import com.rapidminer.gui.renderer.AbstractTableModelTableRenderer;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.tools.container.Pair;

/**
 * A renderer for the extracted values of GameDataIOObjects
 * 
 * @author Sebastian Land
 */
public class GameDataRenderer extends AbstractTableModelTableRenderer {

	@Override
	public String getName() {
		return "Extracted Values";
	}

	@Override
	public TableModel getTableModel(Object renderable, IOContainer ioContainer, boolean isReporting) {
		if (renderable instanceof GameDataIOObject) {
			GameDataIOObject object = (GameDataIOObject) renderable;
			final List<Pair<String, Double>> values = new ArrayList<Pair<String, Double>>();
			for (String key : object.getValueMap().keySet()) {
				values.add(new Pair<String, Double>(key, object.getValueMap().get(key)));
			}

			return new AbstractTableModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public int getColumnCount() {
					return 2;
				}

				@Override
				public String getColumnName(int column) {
					if (column == 0)
						return "Attribute";
					return "Value";
				}
				
				@Override
				public int getRowCount() {
					return values.size();
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					Pair<String, Double> pair = values.get(rowIndex);
					if (columnIndex == 0)
						return pair.getFirst();
					return pair.getSecond();
				}
			};
		}
		return new DefaultTableModel();
	}
	
	@Override
	public boolean isSortable() {
		return false;
	}
	
	@Override
	public boolean isAutoresize() {
		return false;
	}
	
	@Override
	public boolean isColumnMovable() {
		return true;
	}
}
