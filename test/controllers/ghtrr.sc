import builders.PropertyDetailsBuilder
import models._
import org.joda.time.LocalDate
import play.api.libs.json.Json


val x = PropertyDetailsAddress("addr1", "addr2", Some("addr3"), Some("addr4"), Some("XX1 1XX"))
val y = PropertyDetailsTitle("titleNo")
val z = PropertyDetailsTaxAvoidance(Some(true), taxAvoidancePromoterReference = Some("12345678"))

val d = PropertyDetailsRevalued(Some(true),Some(12000), Some(new LocalDate("2017-11-11")), Some(new LocalDate("2017-11-12")))

val h = PropertyDetailsNewBuild(
  Some(true),
  Some(1000),
  Some(new LocalDate("2017-11-11")),
  Some(new LocalDate("2017-11-12")),
  Some(1000000000),
  Some(new LocalDate("2017-11-13"))
)
val f = PropertyDetailsOwnedBefore(Some(true), Some(990000000))
                                     // ownedBefore2012Value: Option[BigDecimal] = None)

val registeredDetails = RegisteredAddressDetails(addressLine1 = "", addressLine2 = "", countryCode = "GB")
val k = UpdateRegistrationDetailsRequest(None, false, None, Some(Organisation("testName")), registeredDetails, ContactDetails(), false, false)

Json.toJson(k)
