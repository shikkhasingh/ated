import models._
import play.api.libs.json.Json

val x = IsFullTaxPeriod(isFullPeriod = false, datesLiable = None)
val addressDetails = AddressDetails("Correspondence", "line1", "line2", None, None, Some("postCode"), "GB")
val updatedData = UpdateSubscriptionDataRequest(true, ChangeIndicators(), List(Address(addressDetails = addressDetails)))

Json.prettyPrint(Json.toJson(updatedData))