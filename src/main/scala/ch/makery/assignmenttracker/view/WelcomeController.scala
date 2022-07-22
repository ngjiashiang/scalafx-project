package ch.makery.assignmenttracker.view
import scalafxml.core.macros.sfxml
import ch.makery.assignmenttracker.MainApp

@sfxml
class WelcomeController {
    def handleTaskTracking() {
        MainApp.setPage("TaskOverview")
    }
    def handleAbout() {
        MainApp.setPage("About")
    }
    def handleCatDog() {
        MainApp.setPage("CatDogGame")
    }
}
