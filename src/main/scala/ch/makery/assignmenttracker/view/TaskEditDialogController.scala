package ch.makery.assignmenttracker.view

import ch.makery.assignmenttracker.model.Task
import ch.makery.assignmenttracker.MainApp
import scalafx.scene.control.{TextField, TableColumn, Label, Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import ch.makery.assignmenttracker.util.DateUtil._
import scalafx.event.ActionEvent

@sfxml
class TaskEditDialogController (
	private val taskNameField : TextField,
	private val taskSubjectField : TextField,
	private val taskDescriptionField : TextField,
	private val taskRemarkField : TextField,
	private val dateField : TextField
){
	var dialogStage : Stage  = null
	private var _task : Task = null
	var okClicked = false

	def task = _task
	def task_=(x : Task) {
        _task = x

        taskNameField.text = _task.taskName.value
        taskSubjectField.text  = _task.taskSubject.value
        taskDescriptionField.text    = _task.taskDescription.value
        taskRemarkField.text= _task.taskRemark.value
        dateField.text  = _task.date.value.asString
        dateField.setPromptText("dd.mm.yyyy");
  	}

	def handleOk(action : ActionEvent){
		if (isInputValid()) {
			_task.taskName <== taskNameField.text
			_task.taskSubject <== taskSubjectField.text
			_task.taskDescription <== taskDescriptionField.text
			_task.taskRemark <== taskRemarkField.text
			_task.date.value       = dateField.text.value.parseLocalDate;

			okClicked = true;
			dialogStage.close()
		}
	}

	def handleCancel(action :ActionEvent) {
		dialogStage.close();
	}
	def nullChecking (x : String) = x == null || x.length == 0

	def isInputValid() : Boolean = {
		var errorMessage = ""

		if (nullChecking(taskNameField.text.value))
			errorMessage += "No valid task name!\n"
		if (nullChecking(taskSubjectField.text.value))
			errorMessage += "No valid task subject!\n"
		if (nullChecking(taskDescriptionField.text.value))
			errorMessage += "No valid task description!\n"
		if (nullChecking(taskRemarkField.text.value))
			errorMessage += "No valid task remark!\n"
		if (nullChecking(dateField.text.value))
			errorMessage += "No valid due date!\n"
		else {
			if (!dateField.text.value.isValid) {
				errorMessage += "No valid due date. Use the format dd.mm.yyyy!\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			val alert = new Alert(Alert.AlertType.Error){
			initOwner(dialogStage)
			title = "Invalid Fields"
			headerText = "Please correct invalid fields"
			contentText = errorMessage
			}.showAndWait()

			return false;
		}
   }
}
