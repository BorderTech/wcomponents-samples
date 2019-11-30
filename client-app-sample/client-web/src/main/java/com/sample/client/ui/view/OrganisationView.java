package com.sample.client.ui.view;

import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.WEmailField;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTextField;
import com.sample.client.model.ClientSummary;
import com.sample.client.model.ClientType;
import com.sample.client.model.OrganisationDetail;
import com.sample.client.services.ClientServices;
import com.sample.client.services.exception.ServiceException;
import com.sample.client.ui.application.ClientApp;
import com.sample.client.ui.common.AddressPanel;
import com.sample.client.ui.util.ClientServicesHelperFactory;

/**
 * Organisation view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class OrganisationView extends AbstractView<OrganisationDetail> {

	private static final ClientServices CLIENT_SERVICES = ClientServicesHelperFactory.getInstance();

	/**
	 * @param app the client app.
	 */
	public OrganisationView(final ClientApp app) {
		super("Organisation", app);

		// Ids
		setIdName("org");
		setNamingContext(true);

		setupContent();
		setupButtons();
	}

	private void setupContent() {

		WPanel content = getContent();

		content.add(new WHeading(HeadingLevel.H2, "Name"));

		WFieldLayout layout = new WFieldLayout(WFieldLayout.LAYOUT_FLAT);
		layout.setLabelWidth(20);
		content.add(layout);

		// Name
		WTextField txtName = new WTextField();
		txtName.setMandatory(true);
		txtName.setBeanProperty("name");
		layout.addField("Name", txtName);

		// ABN
		WTextField txtABN = new WTextField();
		txtABN.setMandatory(true);
		txtABN.setBeanProperty("abn");
		layout.addField("ABN", txtABN);

		// Email
		WEmailField email = new WEmailField();
		email.setBeanProperty("email");
		layout.addField("Email", email);

		// Address
		content.add(new WHeading(HeadingLevel.H2, "Address"));

		AddressPanel address = new AddressPanel();
		address.setBeanProperty("address");
		content.add(address);
	}

	@Override
	protected void doCreateServiceCall(final OrganisationDetail bean) throws ServiceException {
		String id = CLIENT_SERVICES.createOrganisation(bean).getClientId();
		getApp().getMessages().success("Organisation [" + id + "] created.");
	}

	@Override
	protected void doUpdateServiceCall(final OrganisationDetail bean) throws ServiceException {
		CLIENT_SERVICES.updateOrganisation(bean);
		getApp().getMessages().success("Organisation updated.");
	}

	@Override
	protected ClientSummary getSummary(final OrganisationDetail bean) {

		ClientSummary summary = new ClientSummary(bean.getClientId(), ClientType.ORGANISATION);
		summary.setAddress(bean.getAddress().toString());
		summary.setName(bean.getName());

		return summary;
	}

}
