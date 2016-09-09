package com.sample.compass.model;

import java.io.Serializable;

/**
 * Profile item.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Item extends Serializable {

	public ProfilePoint getProfilePoint();

	public int getChildCount();

}
