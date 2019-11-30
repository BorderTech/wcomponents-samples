package com.sample.fileupload.validating;

import com.github.bordertech.taskmaster.service.ResultHolder;
import com.github.bordertech.taskmaster.service.ServiceAction;
import com.github.bordertech.taskmaster.service.exception.ServiceException;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WMultiFileWidget;
import com.github.bordertech.wcomponents.addons.polling.PollingServicePanel;

/**
 * Polling service that validates the file.
 */
public class ValidatingPollingPanel extends PollingServicePanel<WMultiFileWidget.FileWidgetUpload, ValidatingResult> {

	private final WMessages messages = new WMessages(true);

	/**
	 * Construct the panel.
	 */
	public ValidatingPollingPanel() {
		super(267);
		getContentResultHolder().add(messages);
		setMargin(new Margin(0, 0, 6, 0));
		setServiceAction(new ServiceAction<WMultiFileWidget.FileWidgetUpload, ValidatingResult>() {
			@Override
			public ValidatingResult service(final WMultiFileWidget.FileWidgetUpload criteria) throws Exception {
				return doServiceCall(criteria);
			}
		});
		setUseCachedResult(false);
	}

	/**
	 * @return true if file is valid
	 */
	public boolean isValidFile() {
		if (getServiceResult() == null) {
			return false;
		}
		return messages.getErrorMessages().isEmpty();
	}

	@Override
	protected void handleResult(ResultHolder<WMultiFileWidget.FileWidgetUpload, ValidatingResult> resultHolder) {
		// TODO This fix needs to be applied to ADDONS to handle the response returning before starting polling
		handleSaveServiceResult(resultHolder);
		super.handleResult(resultHolder);
	}

	@Override
	protected void handleInitResultContent(final Request request) {
		ValidatingResult response = getServiceResult().getResult();

		WMultiFileWidget.FileWidgetUpload file = getServiceCriteria();
		if (response.getMessages().isEmpty()) {
			messages.info("File [" + file.getFile().getFileName() + "] is valid");
		} else {
			for (String msg : response.getMessages()) {
				messages.error("File [" + file.getFile().getFileName() + "] is not valid");
				messages.error(msg);
			}
		}
	}

	private ValidatingResult doServiceCall(final WMultiFileWidget.FileWidgetUpload recordId) throws ServiceException {

		// Pretend Service Calls
		String fileName = recordId.getFile().getFileName();
		ValidatingResult result = new ValidatingResult();

		if (fileName.contains("bad")) {
			try {
				Thread.currentThread().sleep(5000);
			} catch (Exception e) {
				throw new ServiceException("Exception while doing a mock delay.", e);
			}
			result.addMessage("Bad file name");
		} else if (fileName.contains("long")) {
			try {
				Thread.currentThread().sleep(10000);
			} catch (Exception e) {
				throw new ServiceException("Exception while doing a mock delay.", e);
			}
		} else if (fileName.contains("error")) {
			try {
				Thread.currentThread().sleep(3000);
			} catch (Exception e) {
				throw new ServiceException("Exception while doing a mock delay.", e);
			}
			throw new ServiceException("Exception validating file");
		}
		return result;
	}

}
