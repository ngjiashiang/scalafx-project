package ch.makery.assignmenttracker.view
import ch.makery.assignmenttracker.model.Task
import ch.makery.assignmenttracker.util.DateUtil._
import ch.makery.assignmenttracker.MainApp
import scalafx.scene.control.{TableView, TableColumn, Label}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.{StringProperty}
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.Alert
import scalafx.stage.FileChooser
import scala.util.{Failure, Success}

@sfxml
class TaskOverviewController (
    private val taskTable : TableView[Task],
    private val taskNameColumn : TableColumn[Task, String],
    private val taskSubjectColumn : TableColumn[Task, String],
    private val taskDescriptionColumn : TableColumn[Task, String],
    private val taskNameLabel : Label,
    private val taskSubjectLabel : Label,
    private val taskDescriptionLabel : Label,
    private val taskRemarkLabel : Label,
    private val dateLabel : Label,
    ) {
    // initialize Table View display contents model
    taskTable.items = MainApp.taskData
    taskNameColumn.cellValueFactory = {_.value.taskName}
    taskSubjectColumn.cellValueFactory  = {_.value.taskSubject}
    taskDescriptionColumn.cellValueFactory  = {_.value.taskDescription}
    showTaskDetails(None);

    taskTable.selectionModel().selectedItem.onChange(
        (_, _, newValue) => showTaskDetails(Some(newValue))
    )

    private def showTaskDetails (task : Option[Task]) = {
        task match {
            case Some(task) =>
            // Fill the labels with info from the task object.
            taskNameLabel.text <== task.taskName
            taskSubjectLabel.text <== task.taskSubject
            taskDescriptionLabel.text <== task.taskDescription
            taskRemarkLabel.text <== task.taskRemark;
            dateLabel.text = task.date.value.asString

            case None =>
            // Person is null, remove all the text.
            taskNameLabel.text = ""
            taskSubjectLabel.text = ""
            taskDescriptionLabel.text = ""
            taskRemarkLabel.text = ""
            dateLabel.text = ""
        }
    }

    def handleImport(action : ActionEvent) = {
        val fileChooser = new FileChooser {
            title = "Open Resource File"
            extensionFilters ++= Seq(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
            )
        }
        val taskDataSet = io.Source.fromFile(fileChooser.showOpenDialog(null))
        for (line <- taskDataSet.getLines ) {
            val cols = line.split(",").map(_.trim)
            if(cols(0) != "Task Name" && (cols(0) != null && cols(1) != null) && (cols(4) == "" || cols(4) == null || cols(4).isValid)){
                val task = new Task(cols(0),cols(1))
                task.taskDescription.value = cols(2)
                task.taskRemark.value = cols(3)
                task.date.value = cols(4).parseLocalDate
                task.save() match {
                    case Success(x) =>
                        MainApp.taskData += task
                    case Failure(e) =>
                        val alert = new Alert(Alert.AlertType.Warning) {
                            initOwner(MainApp.stage)
                            title = "Failed to Save"
                            headerText = "Database Error"
                            contentText = "Database problem filed to save changes"
                        }.showAndWait()
                }
            }
        }
    }
    /**
    * Called when the user clicks on the delete button.
    */
    def handleDeleteTask(action : ActionEvent) = {
        val selectedTask = taskTable.selectionModel().selectedItem.value
        val selectedIndex = taskTable.selectionModel().selectedIndex.value
        if (selectedIndex >= 0) {
            selectedTask.delete() match {
            case Success(x) =>
                taskTable.items().remove(selectedIndex);
            case Failure(e) =>
                val alert = new Alert(Alert.AlertType.Warning) {
                initOwner(MainApp.stage)
                title = "Failed to Save"
                headerText = "Database Error"
                contentText = "Database problem filed to save changes"
                }.showAndWait()
            }
        } else {
            // Nothing selected.
            val alert = new Alert(AlertType.Warning){
                initOwner(MainApp.stage)
                title       = "No Selection"
                headerText  = "No Task Selected"
                contentText = "Please select a task in the table."
            }.showAndWait()
        }
    }

    def handleNewTask(action : ActionEvent) = {
        val task = new Task("","")
        val okClicked = MainApp.showTaskEditDialog(task);
        if (okClicked) {
            task.save() match {
                case Success(x) =>
                    MainApp.taskData += task
                case Failure(e) =>
                    val alert = new Alert(Alert.AlertType.Warning) {
                        initOwner(MainApp.stage)
                        title = "Failed to Save"
                        headerText = "Database Error"
                        contentText = "Database problem filed to save changes"
                    }.showAndWait()
            }
        }
    }

    def handleEditTask(action : ActionEvent) = {
        val selectedTask = taskTable.selectionModel().selectedItem.value
        if (selectedTask != null) {
            val okClicked = MainApp.showTaskEditDialog(selectedTask)

            if (okClicked) {
                selectedTask.save() match {
                    case Success(x) =>
                        showTaskDetails(Some(selectedTask))
                    case Failure(e) =>
                        val alert = new Alert(Alert.AlertType.Warning) {
                            initOwner(MainApp.stage)
                            title = "Failed to Save"
                            headerText = "Database Error"
                            contentText = "Database problem filed to save changes"
                        }.showAndWait()
                }
            }
        } else {
            // Nothing selected.
            val alert = new Alert(Alert.AlertType.Warning){
                initOwner(MainApp.stage)
                title       = "No Selection"
                headerText  = "No Task Selected"
                contentText = "Please select a task in the table."
            }.showAndWait()
        }
    }
}
