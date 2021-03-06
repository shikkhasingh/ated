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

package controllers

import play.api.mvc.Action
import services.FormBundleService
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.ExecutionContext.Implicits.global

trait FormBundleReturnsController extends BaseController {

  def formBundleService: FormBundleService

  def getFormBundleReturns(accountRef: String, formBundle: String) = Action.async { implicit request =>
    formBundleService.getFormBundleReturns(accountRef, formBundle) map { responseReceived =>
      responseReceived.status match {
        case OK => Ok(responseReceived.body)
        case NOT_FOUND => NotFound(responseReceived.body)
        case BAD_REQUEST => BadRequest(responseReceived.body)
        case SERVICE_UNAVAILABLE => ServiceUnavailable(responseReceived.body)
        case _ => InternalServerError(responseReceived.body)
      }
    }
  }

}

object FormBundleReturnsController extends FormBundleReturnsController {

  val formBundleService: FormBundleService = FormBundleService

}
