package controllers

import dao.EmployeesRepo
import models.{Employee, EmployeeForm, UpdateEmployeeForm}
import play.api._
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText, number}
import play.api.mvc._

import javax.inject._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class EmployeeController @Inject()(val controllerComponents: ControllerComponents, val employeeRepo: EmployeesRepo) extends BaseController with play.api.i18n.I18nSupport {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request =>
    val result = employeeRepo.getAllEmployees()
    Ok(views.html.index(result))
  }

  val updateForm = Form(
    mapping(
      "id" -> number,
      "Name" -> nonEmptyText,
      "Designation" -> nonEmptyText,
    )(UpdateEmployeeForm.apply)(UpdateEmployeeForm.unapply)
  )

  val createForm = Form(mapping("Name" -> nonEmptyText, "Designation" -> nonEmptyText)(EmployeeForm.apply)(EmployeeForm.unapply))

  def createEmployeeInit = Action { implicit request =>
    Ok(views.html.createEmployee(createForm))
  }
  def createEmployee() = Action { implicit request =>
    createForm.bindFromRequest.fold(
      errors => BadRequest(views.html.createEmployee(errors)),
      employee => {
        employeeRepo.insertEmployee(employee)
        Redirect(routes.EmployeeController.index())
      }
    )
  }

  def getEmployeeById(id: Int): Option[Employee] = employeeRepo.getEmployeeById(id)
  def updateEmployeeInit(id: Int, name: String, designation: String) = Action { implicit request =>
    val empOption = getEmployeeById(id)
    empOption match {
      case Some(emp) => Ok(views.html.updateEmployee(updateForm.fill(UpdateEmployeeForm(emp.id, emp.name, emp.designation))))
      case None => Redirect(routes.EmployeeController.index())
    }
  }

  def updateEmployee() = Action { implicit request =>
    updateForm.bindFromRequest.fold(errors => BadRequest(views.html.updateEmployee(errors)), employee => {
      employeeRepo.updateEmployee(employee)
      Redirect(routes.EmployeeController.index())
    })
  }
  def deleteEmployee(id: Int) = Action { implicit request =>
    employeeRepo.deleteEmployee(id)
    Redirect(routes.EmployeeController.index())
  }
}
