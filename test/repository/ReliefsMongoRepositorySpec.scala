/*
 * Copyright 2017 HM Revenue & Customs
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

package repository


import builders.ReliefBuilder
import models.{Reliefs, TaxAvoidance}
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach}
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.{OneServerPerSuite, PlaySpec}
import play.api.test.Helpers._
import reactivemongo.api.DB
import reactivemongo.api.indexes.CollectionIndexesManager
import reactivemongo.json.collection.JSONCollection
import uk.gov.hmrc.mongo.{Awaiting, MongoSpecSupport}

import scala.concurrent.Future

class ReliefsMongoRepositorySpec extends PlaySpec
  with OneServerPerSuite
  with MongoSpecSupport
  with Awaiting
  with MockitoSugar
  with BeforeAndAfterEach {


  def repository(implicit mongo: () => DB) = new ReliefsReactiveMongoRepository

  val atedRefNo1 = "atedRef123"
  val periodKey = 2015
  val relief1 = ReliefBuilder.reliefTaxAvoidance(atedRefNo1, periodKey, Reliefs(periodKey = periodKey, rentalBusiness = true), TaxAvoidance(rentalBusinessScheme = Some("scheme123")))
  val relief2 = ReliefBuilder.reliefTaxAvoidance(atedRefNo1, periodKey, Reliefs(periodKey = periodKey, employeeOccupation = true), TaxAvoidance(rentalBusinessScheme = Some("scheme123")))
  val relief3 = ReliefBuilder.reliefTaxAvoidance(atedRefNo1, 2016, Reliefs(periodKey = 2016, rentalBusiness = true, farmHouses = true), TaxAvoidance(farmHousesScheme = Some("scheme999")))

  "ReliefsMongoRepository" must {
    "save relief draft" should {
      "save a new relief and then fetch" in {
        await(repository.cacheRelief(relief1))
        await(repository.fetchReliefs(atedRefNo1)).size must be(1)
      }

      "overwrite old relief when saving new one and fetch" in {
        await(repository.cacheRelief(relief1))
        await(repository.cacheRelief(relief2))
        await(repository.fetchReliefs(atedRefNo1)).size must be(1)
        await(repository.fetchReliefs(atedRefNo1)).head.reliefs.rentalBusiness must be(false)
        await(repository.fetchReliefs(atedRefNo1)).head.reliefs.employeeOccupation must be(true)
      }
    }

    "delete a relief" in {
      await(repository.cacheRelief(relief1))
      await(repository.deleteReliefs(atedRefNo1))
      await(repository.fetchReliefs(atedRefNo1)).size must be(0)
    }

    "delete a relief by year" in {
      await(repository.cacheRelief(relief1))
      await(repository.cacheRelief(relief3))
      await(repository.fetchReliefs(atedRefNo1)).size must be(2)
      await(repository.deleteDraftReliefByYear(atedRefNo1, periodKey))
      await(repository.fetchReliefs(atedRefNo1)).size must be(1)
    }

  }

  val mockCollection = mock[JSONCollection]

  override def beforeEach(): Unit = {
    await(repository.drop)
    reset(mockCollection)
    setupIndexesManager
  }

  private def setupIndexesManager: CollectionIndexesManager = {
    val mockIndexesManager = mock[CollectionIndexesManager]
    when(mockCollection.indexesManager).thenReturn(mockIndexesManager)
    when(mockIndexesManager.dropAll) thenReturn Future.successful(0)
    mockIndexesManager
  }

  class TestMandateRepository extends ReliefsReactiveMongoRepository {
    override lazy val collection = mockCollection
  }

}
