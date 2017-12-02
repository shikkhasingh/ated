import models.{IsFullTaxPeriod, PropertyDetailsDatesLiable}
import play.api.libs.json.Json

val x = IsFullTaxPeriod(isFullPeriod = false, datesLiable = None)

Json.prettyPrint(Json.toJson(x))