package dao

import models.{Employee, EmployeeForm, UpdateEmployeeForm}
import models.Employee.toEmployee
import scalikejdbc._
import scalikejdbc.config._

//TODO: Use binding to prevent sql injection
class EmployeesRepo {
  DBs.setupAll()
  implicit val session = AutoSession

  def getAllEmployees(): List[Employee] = {
    sql"select * from employees"
      .map(toEmployee)
      .list.apply
  }

  def getEmployeeById(id: Int): Option[Employee] = {
    sql"select * from employees where id = ${id}"
      .map(rs => Employee(rs.int("id"), rs.string("name"), rs.string("designation")))
      .single.apply()
  }
  def insertEmployee(form: EmployeeForm): Unit = {
    sql"insert into employees(name, designation) values(${form.name}, ${form.designation})".update.apply()
  }

  def updateEmployee(form: UpdateEmployeeForm): Unit = {
    sql"update employees set name = ${form.name}, designation = ${form.designation} where id = ${form.id}".update.apply()
  }

  def deleteEmployee(id: Int): Int = {
    sql"delete from employees where id = $id".update.apply()
  }
}

