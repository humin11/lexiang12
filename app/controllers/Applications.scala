package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import models._
import securesocial.core._

object Applications extends Controller with securesocial.core.SecureSocial {
  
  def index = Action { implicit request =>
	  Ok(views.html.index("Your new application is ready."))
  }
  
  def userAjax = SecuredAction(ajaxCall = true) { implicit request =>
      // return some json
	  
	  val obj = request.user match {
	  	case user: User => Json.obj("username" -> user.fullName,
				  "email" -> user.email)
		case _ =>Json.obj("authenticate" -> "failed")
	  }
	  
	  Ok(obj)

  }
}