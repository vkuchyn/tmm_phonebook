package ua.com.kuchyn.tmm.phonebook.web.gwt.client;

import java.util.ArrayList;
import java.util.List;

import ua.com.kuchyn.tmm.phonebook.web.gwt.model.GwtUser;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Phonebook implements EntryPoint {
    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final PhonebookServiceAsync phonebookService = GWT
	    .create(PhonebookService.class);

    private VerticalPanel mainPanel = new VerticalPanel();
    private FlexTable userFlexTable = new FlexTable();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private TextBox newLoginTextBox = new TextBox();
    private TextBox newPhoneTextBox = new TextBox();
    private Button addUserButton = new Button("Add");
    private Label errorLabel = new Label();
    private TextBox searchInput = new TextBox();
    private Button searchButton = new Button("Search");
    private HorizontalPanel searchPanel = new HorizontalPanel();

    private List<GwtUser> userList = new ArrayList<GwtUser>();

    /**
     * Entry point method.
     */
    public void onModuleLoad() {

	searchPanel.add(searchInput);
	searchPanel.add(searchButton);

	searchButton.addClickHandler(new ClickHandler() {

	    @Override
	    public void onClick(ClickEvent event) {
		phonebookService.findByKey(searchInput.getText(),
			new AsyncCallback<List<GwtUser>>() {

			    @Override
			    public void onFailure(Throwable caught) {
				errorLabel.setText(caught.getMessage());
				searchInput.setText("");
			    }

			    @Override
			    public void onSuccess(List<GwtUser> result) {
				userList = result;
				printUserList(userList);
				searchInput.setText("");
				errorLabel.setText("");
			    }
			});
	    }
	});

	userFlexTable.setText(0, 0, "Login");
	userFlexTable.setText(0, 1, "Phone");
	userFlexTable.setText(0, 2, "Remove");

	phonebookService.getAllUsers(new AsyncCallback<List<GwtUser>>() {

	    @Override
	    public void onSuccess(List<GwtUser> result) {
		errorLabel.setText("");
		userList = result;
		printUserList(result);
	    }

	    @Override
	    public void onFailure(Throwable caught) {
		errorLabel.setText(caught.getMessage());
	    }
	});

	// Assemble Add Stock panel.
	addPanel.add(newLoginTextBox);
	addPanel.add(newPhoneTextBox);
	addPanel.add(addUserButton);

	addUserButton.addClickHandler(new ClickHandler() {
	    @Override
	    public void onClick(ClickEvent event) {
		phonebookService.createUser(newLoginTextBox.getText(),
			newPhoneTextBox.getText(), new AsyncCallback<String>() {

			    @Override
			    public void onFailure(Throwable caught) {
				errorLabel.setText(caught.getMessage());
			    }

			    @Override
			    public void onSuccess(String result) {
				GwtUser newUser = new GwtUser(result,
					newLoginTextBox.getText(),
					newPhoneTextBox.getText());
				userList.add(newUser);
				printUserAsRow(newUser, userList.size() - 1);
				newLoginTextBox.setText("");
				newPhoneTextBox.setText("");
			    }
			});
	    }
	});

	mainPanel.add(searchPanel);
	mainPanel.add(userFlexTable);
	mainPanel.add(addPanel);
	mainPanel.add(errorLabel);

	RootPanel.get().add(mainPanel);

	newLoginTextBox.setFocus(true);

    }

    private void printUserList(List<GwtUser> userList) {
	int row = 0;
	clearFlexTable(userFlexTable);
	for (GwtUser user : userList) {
	    printUserAsRow(user, row++);
	}
    }

    private void printUserAsRow(GwtUser user, int row) {
	userFlexTable.setText(row + 1, 0, user.getLogin());
	userFlexTable.setText(row + 1, 1, user.getPhone());
	Button removeUserButton = new Button("x");
	final GwtUser userToDelete = user;
	removeUserButton.addClickHandler(new ClickHandler() {

	    @Override
	    public void onClick(ClickEvent event) {
		phonebookService.deleteUser(userToDelete.getId(),
			new AsyncCallback<Void>() {

			    @Override
			    public void onFailure(Throwable caught) {
				errorLabel.setText(caught.getMessage());
			    }

			    @Override
			    public void onSuccess(Void result) {

				userFlexTable
					.removeRow(getTableRowById(userToDelete
						.getId()) + 1);
				Phonebook.this.userList.remove(userToDelete);

			    }
			});
	    }
	});
	userFlexTable.setWidget(row + 1, 2, removeUserButton);

    }

    private void clearFlexTable(FlexTable table) {
	table.removeAllRows();
    }

    private int getTableRowById(String id) {
	int row = 0;
	for (GwtUser user : userList) {
	    if (user.getId().equals(id)) {
		return row;
	    }
	    row++;
	}
	return row;
    }
}
