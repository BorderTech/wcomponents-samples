/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.client.ui.view;

import com.github.bordertech.wcomponents.polling.PollingServicePanel;
import com.github.bordertech.wcomponents.polling.ResultHolder;
import com.sample.client.ui.util.CacheServiceUtil;
import java.io.Serializable;

/**
 * Polling panel that cached the service result.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class PollingCachePanel<S, T> extends PollingServicePanel<S, T> {

	@Override
	protected void handleCacheServiceResult(final S criteria, final Object result) {
		String key = getCacheKey(criteria);
		if (result instanceof Serializable) {
			CacheServiceUtil.setResult(key, (Serializable) result);
		}
	}

	@Override
	protected Object handleCheckServiceCache(final S criteria) {
		String key = getCacheKey(criteria);
		return CacheServiceUtil.getResult(key);
	}

	@Override
	protected void handleClearServiceCache(final S criteria) {
		String key = getCacheKey(criteria);
		CacheServiceUtil.clearResult(key);
	}

	@Override
	protected void handleExecuteServiceAction(final S criteria, final ResultHolder result) {
		String key = getCacheKey(criteria);
		Object cached = CacheServiceUtil.getResultWait(key);
		if (cached != null) {
			result.setResult(cached);
			return;
		}
		CacheServiceUtil.startProcessing(key);
		super.handleExecuteServiceAction(criteria, result);
	}

	protected String getCacheKey(final S criteria) {
		return criteria.toString();
	}

}
