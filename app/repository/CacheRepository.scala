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

import models.{Cache, Id}
import play.api.libs.json._
import play.modules.reactivemongo.MongoDbConnection
import reactivemongo.api.DB
import uk.gov.hmrc.mongo._
// $COVERAGE-OFF$
trait CacheRepository extends Repository[Cache, Id] {}

object CacheRepository extends MongoDbConnection {


  def apply(collectionNameProvidedBySource: String, cacheFormats: Format[Cache]): CacheRepository =
    new CacheMongoRepository(collectionNameProvidedBySource, cacheFormats)
}

class CacheMongoRepository(collName: String, cacheFormats: Format[Cache] = Cache.mongoFormats)(implicit mongo: () => DB)
  extends ReactiveRepository[Cache, Id](collName, mongo, cacheFormats, Id.idFormats)
  with CacheRepository {

  import scala.concurrent.ExecutionContext.Implicits.global

  collection.drop()

}
// $COVERAGE-ON$