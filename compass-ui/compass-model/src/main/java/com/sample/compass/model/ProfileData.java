package com.sample.compass.model;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ProfileData extends Serializable {

	public int getColumnCount();

	public int getRowCount();

	public AttributeMetaData getAttributeMetaData(final int col);

	public Object getValueAt(final int row, final int col);

	public String getRowKey(final int row);

}
