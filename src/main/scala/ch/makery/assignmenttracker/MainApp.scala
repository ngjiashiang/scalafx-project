package ch.makery.assignmenttracker
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import ch.makery.assignmenttracker.model.Task
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import ch.makery.assignmenttracker.util.Routable
import ch.makery.assignmenttracker.view.TaskEditDialogController
import scalafx.stage.{ Stage, Modality }
import ch.makery.assignmenttracker.util.Database

object MainApp extends JFXApp with Routable {
	//initialize database
	Database.setupDB()

	/**
		* The data as an observable list of Tasks.
		*/
	val taskData = new ObservableBuffer[Task]()

	taskData ++= Task.allTasks

	// transform path of RootLayout.fxml to URI for resource location.
	val rootResource = getClass.getResourceAsStream("view/RootLayout.fxml")
	// initialize the loader object.
	val loader = new FXMLLoader(null, NoDependencyResolver)
	// Load root layout from fxml file.
	loader.load(rootResource);
	// retrieve the root component BorderPane from the FXML
	val roots = loader.getRoot[jfxs.layout.BorderPane]
	// initialize stage
	stage = new PrimaryStage {
		title = "Personal Assignment Task Tracker"
		scene = new Scene {
			root = roots
		}
	}

  	setPage("Welcome")

  	def showTaskEditDialog(task: Task): Boolean = {
		val resource = getClass.getResourceAsStream("view/TaskEditDialog.fxml")
		val loader = new FXMLLoader(null, NoDependencyResolver)
		loader.load(resource);
		val roots2  = loader.getRoot[jfxs.Parent]
		val control = loader.getController[TaskEditDialogController#Controller]

		val dialog = new Stage() {
			initModality(Modality.APPLICATION_MODAL)
			initOwner(stage)
			scene = new Scene {
				root = roots2
			}
		}
		control.dialogStage = dialog
		control.task = task
		dialog.showAndWait()
		control.okClicked
	}
}
