package com.sample.client.ui.view;

import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.SimpleBeanBoundTableModel;
import com.github.bordertech.wcomponents.WDateField;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WEmailField;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.WTableColumn;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import com.sample.client.model.ClientSummary;
import com.sample.client.model.ClientType;
import com.sample.client.model.CodeOption;
import com.sample.client.model.IndividualDetail;
import com.sample.client.model.PassportDetail;
import com.sample.client.services.ClientServicesHelper;
import com.sample.client.services.exception.ServiceException;
import com.sample.client.ui.application.ClientApp;
import com.sample.client.ui.common.AddressPanel;
import com.sample.client.ui.common.Constants;
import com.sample.client.ui.common.ViewMode;
import com.sample.client.ui.common.WTextCountryCodeDesc;
import com.sample.client.ui.common.WTextPassportNumber;
import com.sample.client.ui.util.ClientServicesHelperFactory;
import java.util.Arrays;
import java.util.List;

/**
 * Individual view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class IndividualView extends AbstractView<IndividualDetail> {

	private static final ClientServicesHelper CLIENT_SERVICES = ClientServicesHelperFactory.getInstance();

	private final WTextField txtPassportNumber = new WTextField();
	private final WDropdown drpPassportCountry = new WDropdown(Constants.COUNTRY_TABLE_WITH_NULL);

	/**
	 * @param app the client app.
	 */
	public IndividualView(final ClientApp app) {
		super("Individual", app);
		// Ids
		setIdName("ind");
		setNamingContext(true);

		setupContent();
		setupButtons();
	}

	@Override
	protected void setupContent() {
		setupNameDetails();
		setupPassportDetails();
		setupAddressDetails();

	}

	private void setupNameDetails() {

		WPanel content = getContent();

		content.add(new WHeading(HeadingLevel.H2, "Name"));

		WFieldLayout layout = new WFieldLayout(WFieldLayout.LAYOUT_FLAT);
		layout.setLabelWidth(20);
		content.add(layout);

		// First name
		WTextField txtFirstName = new WTextField();
		txtFirstName.setMandatory(true);
		txtFirstName.setBeanProperty("firstName");
		layout.addField("First name", txtFirstName);

		// Last name
		WTextField txtLastName = new WTextField();
		txtLastName.setMandatory(true);
		txtLastName.setBeanProperty("lastName");
		layout.addField("Last name", txtLastName);

		// Email
		WEmailField email = new WEmailField();
		email.setBeanProperty("email");
		layout.addField("Email", email);

		// DOB
		WDateField dob = new WDateField();
		dob.setBeanProperty("dateOfBirth");
		layout.addField("Date of birth", dob);

	}

	private void setupPassportDetails() {

		WPanel content = getContent();

		content.add(new WHeading(HeadingLevel.H2, "Passport"));

		// Existing (Reaonly/Update)
		WTable table = new WTable() {
			@Override
			public boolean isVisible() {
				return getViewMode() != ViewMode.Create;
			}
		};
		content.add(table);

		table.setMargin(Constants.SOUTH_MARGIN_LARGE);
		table.addColumn(new WTableColumn("Country", new WTextCountryCodeDesc()));
		table.addColumn(new WTableColumn("Idenitification", new WTextPassportNumber()));
		table.setStripingType(WTable.StripingType.ROWS);
		table.setNoDataMessage("No identifications.");
		SimpleBeanBoundTableModel model = new SimpleBeanBoundTableModel(new String[]{".", "."});
		table.setTableModel(model);
		table.setBeanProperty("identifications");

		// Create/Update
		WFieldLayout layout = new WFieldLayout(WFieldLayout.LAYOUT_FLAT) {
			@Override
			public boolean isVisible() {
				return getViewMode() != ViewMode.Readonly;
			}

			@Override
			protected void validateComponent(final List<Diagnostic> diags) {
				super.validateComponent(diags);

				if (txtPassportNumber.getValue() != null && drpPassportCountry.getSelected() == null) {
					diags.add(createErrorDiagnostic(drpPassportCountry, "Passport country must also be provided."));
				}

				if (txtPassportNumber.getValue() == null && drpPassportCountry.getSelected() != null) {
					diags.add(createErrorDiagnostic(txtPassportNumber, "Passport number must also be provided."));
				}

			}

		};
		layout.setLabelWidth(20);
		content.add(layout);

		// Passport number
		layout.addField("Passport number", txtPassportNumber);

		// Passport Country
		layout.addField("Passport country", drpPassportCountry);

	}

	private void setupAddressDetails() {

		WPanel content = getContent();

		content.add(new WHeading(HeadingLevel.H2, "Address"));

		AddressPanel address = new AddressPanel();
		address.setBeanProperty("address");
		content.add(address);
	}

	@Override
	protected void doUpdateDetailBean() {
		super.doUpdateDetailBean();
		if (drpPassportCountry.getSelected() != null && txtPassportNumber.getValue() != null) {
			PassportDetail passport = new PassportDetail();
			passport.setCountryCode(((CodeOption) drpPassportCountry.getSelected()).getCode());
			passport.setPassportNumber(txtPassportNumber.getValue());
			switch (getViewMode()) {

				case Create:
					getDetail().setIdentifications(Arrays.asList(passport));
					break;

				case Update:
					if (!getDetail().getIdentifications().contains(passport)) {
						getDetail().getIdentifications().add(passport);
					}
			}

		}
	}

	@Override
	protected void doCreateServiceCall(final IndividualDetail bean) throws ServiceException {
		String id = CLIENT_SERVICES.createIndividual(bean);
		getApp().getMessages().success("Individual [" + id + "] created.");
	}

	@Override
	protected void doUpdateServiceCall(final IndividualDetail bean) throws ServiceException {
		CLIENT_SERVICES.updateIndividual(bean);
		getApp().getMessages().success("Individual updated.");
	}

	@Override
	protected ClientSummary getSummary(final IndividualDetail bean) {

		ClientSummary summary = new ClientSummary(bean.getClientId(), ClientType.Individual);
		summary.setAddress(bean.getAddress().toString());
		summary.setIdentifications(bean.getIdentifications());
		summary.setName(bean.getFirstName() + " " + bean.getLastName());

		return summary;
	}

}
