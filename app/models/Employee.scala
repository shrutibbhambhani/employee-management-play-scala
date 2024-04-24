package models

import scalikejdbc.WrappedResultSet

case class Employee(id: Int, name: String, designation: String)

object Employee {
  def toEmployee(resultSet: WrappedResultSet): Employee = {
    Employee(resultSet.int("id"), resultSet.string("name"), resultSet.string("designation"))
  }
}

case class EmployeeForm(name: String, designation: String)
object EmployeeForm {
  def unapply(emp: EmployeeForm): Option[(String, String)] = Some((emp.name, emp.designation))
}