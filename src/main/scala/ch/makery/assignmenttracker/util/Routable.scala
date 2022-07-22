package ch.makery.assignmenttracker.util
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import ch.makery.assignmenttracker.MainApp

trait Routable {
    def setPage(pageName : String): Unit = {
        val pagePath = "view/"+pageName+".fxml"
        val resource = getClass.getResourceAsStream(pagePath)
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource);
        val roots = loader.getRoot[jfxs.layout.AnchorPane]
        MainApp.roots.setCenter(roots)
    }
}
