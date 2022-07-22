package ch.makery.assignmenttracker.view
import scalafxml.core.macros.sfxml
import scalafx.application.Platform
import ch.makery.assignmenttracker.MainApp

@sfxml
class RootLayoutController() {
    def handleQuitApplication() {
        Platform.exit()
    }
    def handleAbout() {
        MainApp.setPage("About")
    }
    def handleMainMenu() {
        MainApp.setPage("Welcome")
    }
}
