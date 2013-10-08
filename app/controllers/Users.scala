package controllers

import play.api._
import play.api.mvc._
import views._

object Users extends Controller with securesocial.core.SecureSocial{
  
	def getUser(id: String) = Action { implicit request =>
		Ok(views.html.index("Your new application is ready."))
	}
  
	def profile = UserAwareAction { implicit request =>
		val userName = request.user match {
		        case Some(user) => user.fullName
		        case _ => "guest"
		}
		Ok(views.html.users.profile(userName))
	}
}