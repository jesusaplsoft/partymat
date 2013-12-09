package com.aplsoftware.partymat.client;

import com.aplsoftware.partymat.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PartyMat
    implements EntryPoint {

    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
        + "attempting to contact the server. Please check your network "
        + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final GreetingServiceAsync greetingService = GWT.create(
            GreetingService.class);

    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
        final Button sendButton = new Button("Ok");
        sendButton.setText("Ok");

        final TextBox nameField = new TextBox();
        nameField.setDirection(Direction.LTR);

        // We can add style names to widgets
        sendButton.addStyleName("sendButton");

        // Add the nameField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        final RootPanel rootPanel = RootPanel.get("nameFieldContainer");

        final Image image = new Image(
                "WEB-INF/classes/com/aplsoftware/partymat/client/logo.jpg");
        rootPanel.add(image, 20, 10);
        image.setSize("100px", "100px");

        final HTML htmlFormularioDeIngreso = new HTML(
                "Formulario de Ingreso a PartyMat", true);
        htmlFormularioDeIngreso.setStyleName("h1");
        htmlFormularioDeIngreso.setHorizontalAlignment(
            HasHorizontalAlignment.ALIGN_CENTER);
        htmlFormularioDeIngreso.setDirectionEstimator(false);
        rootPanel.add(htmlFormularioDeIngreso, 156, 27);
        htmlFormularioDeIngreso.setSize("200", "128");

        final Label lblNombreDeUsuario = new Label("Nombre de Usuario");
        lblNombreDeUsuario.setDirection(Direction.RTL);
        rootPanel.add(lblNombreDeUsuario, 91, 145);
        rootPanel.add(nameField, 208, 129);

        final Label errorLabel = new Label();
        RootPanel.get("errorLabelContainer").add(errorLabel);

        final TextBox pwField = new TextBox();
        pwField.setDirection(Direction.LTR);
        pwField.setFocus(true);
        rootPanel.add(pwField, 208, 177);
        RootPanel.get("sendButtonContainer").add(sendButton, 201, 241);
        sendButton.setSize("60", "37");

        // Focus the cursor on the name field when the app loads
        nameField.setFocus(true);

        final Label lblClaveDeAcceso = new Label("Clave de Acceso");
        lblClaveDeAcceso.setDirection(Direction.RTL);
        rootPanel.add(lblClaveDeAcceso, 105, 191);
        nameField.selectAll();

        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);

        final Button closeButton = new Button("Close");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");

        final Label textToServerLabel = new Label();
        final HTML serverResponseLabel = new HTML();
        final VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(final ClickEvent event) {
                    dialogBox.hide();
                    sendButton.setEnabled(true);
                    sendButton.setFocus(true);
                }
            });

        // Create a handler for the sendButton and nameField
        class MyHandler
            implements ClickHandler, KeyUpHandler {

            /**
             * Fired when the user clicks on the sendButton.
             */
            @Override
            public void onClick(final ClickEvent event) {
                this.sendNameToServer();
            }

            /**
             * Fired when the user types in the nameField.
             */
            @Override
            public void onKeyUp(final KeyUpEvent event) {

                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    this.sendNameToServer();
                }
            }

            /**
             * Send the name from the nameField to the server and wait for a
             * response.
             */
            private void sendNameToServer() {
                // First, we validate the input.
                errorLabel.setText("");

                final String textToServer = nameField.getText();

                if (!FieldVerifier.isValidName(textToServer)) {
                    errorLabel.setText("Please enter at least four characters");
                    return;
                }

                // Then, we send the input to the server.
                sendButton.setEnabled(false);
                textToServerLabel.setText(textToServer);
                serverResponseLabel.setText("");
                PartyMat.this.greetingService.greetServer(textToServer,
                    new AsyncCallback<String>() {
                        @Override
                        public void onFailure(final Throwable caught) {
                            // Show the RPC error message to the user
                            dialogBox.setText(
                                "Remote Procedure Call - Failure");
                            serverResponseLabel.addStyleName(
                                "serverResponseLabelError");
                            serverResponseLabel.setHTML(PartyMat.SERVER_ERROR);
                            dialogBox.center();
                            closeButton.setFocus(true);
                        }

                        @Override
                        public void onSuccess(final String result) {
                            dialogBox.setText("Remote Procedure Call");
                            serverResponseLabel.removeStyleName(
                                "serverResponseLabelError");
                            serverResponseLabel.setHTML(result);
                            dialogBox.center();
                            closeButton.setFocus(true);
                        }
                    });
            }
        }

        // Add a handler to send the name to the server
        final MyHandler handler = new MyHandler();
        sendButton.addClickHandler(handler);
        nameField.addKeyUpHandler(handler);
    }
}
