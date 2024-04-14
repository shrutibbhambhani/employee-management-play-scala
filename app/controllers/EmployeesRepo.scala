package controllers

import scalikejdbc._
import scalikejdbc.config._

class EmployeesRepo {
  DBs.setupAll()
  implicit val session = AutoSession

  def getAllEmployees(): List[Employee] = {
    sql"select * from employees"
      .map(rs => Employee(rs.int("id"), rs.string("name"), rs.string("designation")))
      .list.apply
  }
}
case class Employee(id: Int, name: String, designation: String)
case class EmployeeForm(name: String, designation: String)
