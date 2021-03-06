/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package connectors

import config.WSHttp
import models.SendEmailRequest
import play.api.Logger
import play.api.http.Status._
import play.api.libs.json.Json
import uk.gov.hmrc.http._
import uk.gov.hmrc.play.config.ServicesConfig

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


sealed trait EmailStatus
case object EmailSent extends EmailStatus
case object EmailNotSent extends EmailStatus

trait EmailConnector extends ServicesConfig with RawResponseReads {

  val sendEmailUri: String = "hmrc/email"
  val serviceUrl = baseUrl("email")

  def http: CoreGet with CorePost with CorePut

  def sendTemplatedEmail(emailAddress: String, templateName: String, params: Map[String, String])(implicit hc: HeaderCarrier): Future[EmailStatus] = {

    val sendEmailReq = SendEmailRequest(List(emailAddress), templateName, params, force = true)

    val postUrl = s"$serviceUrl/$sendEmailUri"
    val jsonData = Json.toJson(sendEmailReq)

    http.POST(postUrl, jsonData).map { response =>
      response.status match {
        case ACCEPTED => {
          EmailSent
        }
        case status => {
          Logger.warn("email failed")
          EmailNotSent
        }
      }
    }
  }
}

object EmailConnector extends EmailConnector {
  // $COVERAGE-OFF$
  val http: CoreGet with CorePost with CorePut = WSHttp
  // $COVERAGE-OFF$
}
