package ch.makery.assignmenttracker.util
import scalikejdbc._
import ch.makery.assignmenttracker.model.Task

trait Database {
	// val pgsqlDriverClassname = "org.postgresql.Driver"
	// val dbURL = "jdbc:postgresql:postgres://ec2-3-222-74-92.compute-1.amazonaws.com:5432/d8546mo94b12ev";
	// cant structure url string properly

	// initialize JDBC driver & connection pool
	val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
  	val dbURL = "jdbc:derby:myDB;create=true;";
	Class.forName(derbyDriverClassname)

	ConnectionPool.singleton(dbURL, "me", "mine")

	// ad-hoc session provider on the REPL
	implicit val session = AutoSession
}

object Database extends Database {
  	def setupDB() = {
		if (!hasDBInitialize)
			Task.initializeTable()
	}
  	def hasDBInitialize : Boolean = {
		DB getTable "Task" match {
		case Some(x) => true
		case None => false
		}
  	}
}
