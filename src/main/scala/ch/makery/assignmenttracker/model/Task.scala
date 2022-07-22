package ch.makery.assignmenttracker.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
import java.time.LocalDate;
import ch.makery.assignmenttracker.util.Database
import ch.makery.assignmenttracker.util.DateUtil._
import scalikejdbc._
import scala.util.{ Try, Success, Failure }

class Task ( taskS : String, subjectS : String) extends Database {
    def this() = this(null, null)
    val id = ObjectProperty[Long](-1)
    var taskName = new StringProperty(taskS)
    var taskSubject = new StringProperty(subjectS)
    var taskDescription = new StringProperty("")
    var taskRemark = new StringProperty("")
    var date = ObjectProperty[LocalDate](LocalDate.of(1999, 2, 21))

    def save() : Try[Long] = {
		if (!(isExist)) {
			Try(DB autoCommit { implicit session =>
				id.value = sql"""
					insert into task (taskName, taskSubject,
						taskDescription, taskRemark, date) values
						(${taskName.value}, ${taskSubject.value}, ${taskDescription.value},
							${taskRemark.value}, ${date.value.asString})
				""".updateAndReturnGeneratedKey().apply()
                id.value
			})
		} else {
			Try(DB autoCommit { implicit session =>
				sql"""
				update task
				set
				taskName  = ${taskName.value} ,
				taskSubject   = ${taskSubject.value},
				taskDescription     = ${taskDescription.value},
				taskRemark = ${taskRemark.value},
				date       = ${date.value.asString}
				 where id = ${id.value}
				""".update.apply().toLong
			})
		}
	}
	def delete() : Try[Int] = {
		if (isExist) {
			Try(DB autoCommit { implicit session =>
			sql"""
				delete from task where
					id = ${id.value}
				""".update.apply()
			})
		} else
			throw new Exception("Task not Exists in Database")
	}
	def isExist : Boolean =  {
		DB readOnly { implicit session =>
			sql"""
				select * from task where
				id = ${id.value}
			""".map(rs => rs.string("taskName")).single.apply()
		} match {
			case Some(x) => true
			case None => false
		}

	}
}

object Task extends Database {
	def apply (
        _id: Long,
		taskNameS : String,
		taskSubjectS : String,
		taskDescriptionS : String,
		taskRemarkS : String,
		dateS : String
		) : Task = {

		new Task(taskNameS, taskSubjectS) {
            id.value = _id
			taskDescription.value     = taskDescriptionS
			taskRemark.value = taskRemarkS
			date.value       = dateS.parseLocalDate
		}

	}
	def initializeTable() = {
		DB autoCommit { implicit session =>
			sql"""
			create table task (
			  id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
			  taskName varchar(64),
			  taskSubject varchar(64),
			  taskDescription varchar(200),
			  taskRemark varchar(200),
			  date varchar(64)
			)
			""".execute.apply()
		}
	}

	def allTasks : List[Task] = {
		DB readOnly { implicit session =>
			sql"select * from task".map(rs => Task(rs.long("id"), rs.string("taskName"),
				rs.string("taskSubject"),rs.string("taskDescription"),
				rs.string("taskRemark"), rs.string("date") )).list.apply()
		}
	}
}
