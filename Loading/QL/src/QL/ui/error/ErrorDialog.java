package QL.ui.error;

import QL.errorhandling.Error;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

public class ErrorDialog extends FaultDialog {

	private final List<Error> errors;
	private final Alert dialog;
	
	public ErrorDialog(List<Error> errors) {

		this.errors = errors;
		this.dialog = new Alert(AlertType.ERROR);
		dialog.setTitle("Error Dialog");
		
		dialog.setHeaderText(null);
		
	}

	@Override
	public void show() {
		super.show(errors, dialog);
	}
}
