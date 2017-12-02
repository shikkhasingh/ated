import models.EditLiabilityReturnsResponseModel
import org.joda.time.DateTime
import play.api.libs.json.Json

val x =  EditLiabilityReturnsResponseModel(DateTime.now(), liabilityReturnResponse = Seq(), accountBalance = BigDecimal(0.00))
Json.prettyPrint(Json.toJson(x))