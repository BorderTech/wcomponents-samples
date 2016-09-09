package com.sample.compass.model;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DataModel extends Serializable {

	public int getColumnCount();

	public int getRowCount();

	public DataAttributeMeta getAttributeMeta(final int col);

	public Object getAttributeValue(final int row, final int col);

	public String getRowKey(final int row);

}
