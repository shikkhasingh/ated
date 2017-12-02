import models.{DisposeLiability, DisposeLiabilityReturn, EditLiabilityReturnsResponseModel}
import org.joda.time.{DateTime, LocalDate}
import play.api.libs.json.Json

val x =  DisposeLiability(Some(new LocalDate("2017-09-01")), 1234)
Json.prettyPrint(Json.toJson(x))