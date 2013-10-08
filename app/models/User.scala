package models

import securesocial.core._
import securesocial.core.providers.Token
import securesocial.core.IdentityId
import play.api.Play.current
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import com.mongodb.casbah.query.dsl._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.json.Json.JsValueWrapper
import mongoContext._

case class User(id: IdentityId, identityId: IdentityId, name: String, firstName: String, lastName: String, fullName: String, email: Option[String],
                avatarUrl: Option[String], authMethod: AuthenticationMethod,
                oAuth1Info: Option[OAuth1Info] = None,
                oAuth2Info: Option[OAuth2Info] = None,
                passwordInfo: Option[PasswordInfo] = None) extends Identity

object User extends ModelCompanion[User, IdentityId] {
  def apply(i: Identity): User = {
    User(i.identityId, i.identityId, i.firstName+" "+i.lastName, i.firstName, i.lastName, i.fullName,
      i.email, i.avatarUrl, i.authMethod, i.oAuth1Info,
      i.oAuth2Info, i.passwordInfo
    )
  }
  val dao = new SalatDAO[User, IdentityId](collection = mongoCollection("users")) {}

  def findById(id:IdentityId):Option[User] = dao.findOne(MongoDBObject("_id.userId" -> id.userId, "_id.providerId" -> id.providerId))

  def findOneByEmailAndProvider(email: String, providerId:String): Option[User] = dao.findOne(MongoDBObject("email" -> email, "authMethod.method" -> providerId))
}


object TokenDAO extends ModelCompanion[Token, String] {
  val dao = new SalatDAO[Token, String](collection = mongoCollection("tokens")) {}

  def findToken(uuid:String):Option[Token] = dao.findOne(MongoDBObject("uuid" -> uuid))
  def deleteExpiredTokens() {
    val now = new _root_.java.util.Date()
    dao.find("expirationTime" $lte now).foreach(
      TokenDAO.remove(_)
    )
  }
}


