ated
====

[![Build Status](https://travis-ci.org/hmrc/ated.svg?branch=master)](https://travis-ci.org/hmrc/ated) [ ![Download](https://api.bintray.com/packages/hmrc/releases/ated/images/download.svg) ](https://bintray.com/hmrc/releases/ated/_latestVersion)

Microservice for Annual Tax on Enveloped Dwellings. This implements the main business logic for ATED, communicating with ETMP(HOD) and Mongo Database for storage/retrieval. The microservice is based on the RESTful API structure, receives and sends data using JSON to either from.

All data received is validated against the relevant schema to ensure correct format of the data being received.


The APIs listed below are invoked for different operations from the frontend micro service. They are grouped according to the functionality.

## Relief Return APIs

APIs for creating a relief return

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
|```/ated/:atedRefNo/ated/reliefs/save``` | POST | saves the draft relief |
| ```/ated/:atedRefNo/ated/reliefs/:periodKey``` | GET | retrieve the draft relief based on period |
|```/ated/:atedRefNo/ated/reliefs/submit/:periodKey``` | GET | submit the draft return |
|```/ated/:atedRefNo/ated/reliefs/drafts``` | DELETE | delete the draft relief |
|```/ated/:atedRefNo/ated/reliefs/drafts/:periodKey``` | DELETE | delete the draft relief by year |

where,

| parameters | description |
|------|-------------------|
| periodKey | starting year of tax year (e.g. 2016 for '16-'17) |
| atedRefNo | unique identfier for clients subscribed to ATED |


### usage with request and response

> retrieve the draft relief based on period
```GET /ated/ATED1223123/ated/reliefs/2017```

| Status | Message     |
|-------|-------------|
| 200   | Ok          |
| 404   | Not Found   |

**Response body**

[Relief Response](#relief-response)

> saves the draft relief
```POST /ated/ATED1223123/ated/reliefs/save```

| Status | Message     |
|-------|-------------|
| 200   | Ok          |

**Example request with a valid body**

```json
{
	"atedRefNo": "ATED1223123",
	"periodKey": 2017,
	"reliefs": {
		"periodKey": 2017,
		"rentalBusiness": false,
		"openToPublic": false,
		"propertyDeveloper": false,
		"propertyTrading": false,
		"lending": false,
		"employeeOccupation": false,
		"farmHouses": false,
		"socialHousing": false,
		"equityRelease": false
	},
	"taxAvoidance": {
		"rentalBusinessScheme": "Scheme1",
		"rentalBusinessSchemePromoter": "Promoter1",
		"openToPublicScheme": "Scheme2",
		"openToPublicSchemePromoter": "Scheme2",
		"propertyDeveloperScheme": "Scheme3",
		"propertyDeveloperSchemePromoter": "Scheme3",
		"propertyTradingScheme": "Scheme4",
		"propertyTradingSchemePromoter": "Promoter4",
		"lendingScheme": "Scheme5",
		"lendingSchemePromoter": "Promoter5",
		"employeeOccupationScheme": "Scheme6",
		"employeeOccupationSchemePromoter": "Promoter6",
		"farmHousesScheme": "Scheme7",
		"farmHousesSchemePromoter": "Promoter7",
		"socialHousingScheme": "Scheme8",
		"socialHousingSchemePromoter": "Promoter8",
		"equityReleaseScheme": "Scheme9",
		"equityReleaseSchemePromoter": "Promoter9"
	},
	"periodStartDate": "2017-04-01",
	"periodEndDate": "2018-03-31",
	"timeStamp": {
		"$date": 1508239198714
	}
}
```
**Response body**

[Relief Response](#relief-response)

> submit the draft return
```GET /ated/ATED1223123/ated/reliefs/submit/2017```

| Status | Message     |
|-------|-------------|
| 200   | Ok          |
| 400   | Bad Request |
| 404   | Not Found   |
| 500   | Internal Server Error |
| 503   | Service Unavailable |

**Response body**

```json
{
	"processingDate": "2001-12-17T09:30:47Z",
	"liabilityReturnResponse": [{
		"mode": "Post",
		"propertyKey": "aaaaaaaaaa",
		"liabilityAmount": 1234,
		"paymentReference": "aaaaaaaaaaaaaa",
		"formBundleNumber": "012345678912"
	}]
}
```

> delete draft relief
```DELETE /ated/ATED1223123/ated/reliefs/drafts``` 

| Status | Message     |
|-------|-------------|
| 200   | Ok          |
| 500   | Internal Server Error |

**Response body**

[Relief Response](#relief-response)

> delete draft relief by year
```DELETE /ated/ATED1223123/ated/reliefs/drafts/2017``` 

| Status | Message     |
|-------|-------------|
| 200   | Ok          |
| 500   | Internal Server Error |

**Response body**

[Relief Response](#relief-response)

## Chargeable Return APIs

APIs for creating a chargeable return

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:atedRefNo/property-details/create/:periodKey``` | POST | create the draft chargeable property |
| ```/ated/:atedRefNo/property-details/retrieve/:id``` | GET | retrieve the draft chargeable property |
| ```/ated/:atedRefNo/property-details/address/:id``` | POST | update the draft chargeable property with address ** |
| ```/ated/:atedRefNo/property-details/title/:id``` | POST | update the draft chargeable property with title ** |
| ```/ated/:atedRefNo/property-details/has-value-change/:id``` | POST | update the draft chargeable property with change in value |
| ```/ated/:atedRefNo/property-details/acquisition/:id``` | POST | update the draft chargeable property with acquisition details |
| ```/ated/:atedRefNo/property-details/revalued/:id``` | POST | update the draft chargeable property with revalued details ** |
| ```/ated/:atedRefNo/property-details/owned-before/:id``` | POST | update the draft chargeable property with owned-before details ** | 
| ```/ated/:atedRefNo/property-details/new-build/:id``` | POST | update the draft chargeable property with new-build details ** |
| ```/ated/:atedRefNo/property-details/valued/:id``` | POST | update the draft chargeable property with valued details ** |
| ```/ated/:atedRefNo/property-details/full-tax-period/:id``` | POST | update the draft chargeable property for full tax period |  
| ```/ated/:atedRefNo/property-details/in-relief/:id``` | POST | update the draft chargeable property with in relief details |
| ```/ated/:atedRefNo/property-details/dates-liable/:id``` | POST | update the draft chargeable property with dates liable |
| ```/ated/:atedRefNo/property-details/dates-liable/add/:id``` | POST | add liable dates in the draft chargeable property |
| ```/ated/:atedRefNo/property-details/dates-in-relief/add/:id``` | POST | add in relief dates in the draft chargeable property |
| ```/ated/:atedRefNo/property-details/period/delete/:id``` | POST | delete a period in the draft chargeable property |
| ```/ated/:atedRefNo/property-details/tax-avoidance/:id``` | POST | update the draft chargeable property with tax-avoidance ** |
| ```/ated/:atedRefNo/property-details/supporting-info/:id``` | POST | update the draft chargeable property with supporting-info ** |
| ```/ated/:atedRefNo/property-details/calculate/:id``` | GET | calculate the draft chargeable property for mode = Pre-Calculation |
| ```/ated/:atedRefNo/property-details/submit/:id``` | POST | submit the draft chargeable property for mode = Post |

** - Example request/response provided below

where,

| parameters | description |
|------|-------------------|
| id | chargeable return id |
| atedRefNo | unique identfier for clients subscribed to ATED |

### usage with request and response

> create the draft chargeable property

**Example request with a valid body**

```json
{
	"line_1": "addr1",
	"line_2": "addr2",
	"line_3": "addr3",
	"line_4": "addr4",
	"postcode": "XX1 1XX"
}
```
**Response body**

[Property Details Response With Status Code](#property-details-response-with-status-code)

> update the draft chargeable property with address

**Example request with a valid body**

```json
{
	"line_1": "addr1",
	"line_2": "addr2",
	"line_3": "addr3",
	"line_4": "addr4",
	"postcode": "XX1 1XX"
}
```
**Response body**

[Property Details Response With Status Code](#property-details-response-with-status-code)

> update the draft chargeable property with title

**Example request with a valid body**

```json
 {"titleNumber":"titleNo"}
```
**Response body**

[Property Details Response With Status Code](#property-details-response-with-status-code)

> update the draft chargeable property with tax-avoidance

**Example request with a valid body**

```json
 {"isTaxAvoidance":true,"taxAvoidancePromoterReference":"12345678"}
```
**Response body**

[Property Details Response With Status Code](#property-details-response-with-status-code)

> update the draft chargeable property with supporting-info

**Example request with a valid body**

```json
 {"supportingInfo":"additional info"}
```
**Response body**

[Property Details Response With Status Code](#property-details-response-with-status-code)

> update the draft chargeable property with revalued details

```json
 {
 	"isPropertyRevalued": true,
 	"revaluedValue": 12000,
 	"revaluedDate": "2017-11-11",
 	"partAcqDispDate": "2017-11-12"
 }
```
**Response body**

[Response With Status Code](#response-with-status-code)

> update the draft chargeable property with new-build details

**Example request with a valid body**

```json
 {
 	"isNewBuild": true,
 	"newBuildValue": 1000,
 	"newBuildDate": "2017-11-11",
 	"localAuthRegDate": "2017-11-12",
 	"notNewBuildValue": 1000000000,
 	"notNewBuildDate": "2017-11-13"
 }
```
**Response body**

[Response With Status Code](#response-with-status-code)

> update the draft chargeable property with owned-before details

**Example request with a valid body**

```json
 {"isOwnedBefore2012":true,"ownedBefore2012Value":990000000}

```
**Response body**

[Response With Status Code](#response-with-status-code)

> update the draft chargeable property with valued details

**Example request with a valid body**

```json
 {"isValuedByAgent":true}

```
**Response body**

[Response With Status Code](#response-with-status-code)

## Client and Agent Registration Details APIs

APIs for registering clients/agents 

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:atedRefNo/ated/details/:identifier/:identifierType``` | GET | retrieve ATED client registration details |
| ```/agent/:agentCode/ated/details/:identifier/:identifierType``` | GET | retrieve agent registration details |
| ```/ated/:atedRefNo/registration-details/:safeId``` | POST | update ATED client registration details |

where,

| parameters | description |
|------|-------------------|
| atedRefNo | unique identfier for clients subscribed to ATED |
| identifierType | arn or safeId or utr |
| identifier | the number for the above types |
| agentCode | agent code |

> update ATED client registration details

**Example request with a valid body**

```json
{
	"isAnIndividual": false,
	"organisation": {
		"organisationName": "testName"
	},
	"address": {
		"addressLine1": "",
		"addressLine2": "",
		"countryCode": "GB"
	},
	"contactDetails": {},
	"isAnAgent": false,
	"isAGroup": false
}
```
**Response body**

[Response With Status Code](#response-with-status-code)


## Client and Agent Subscription Details APIs

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:atedRefNo/subscription-data ``` | GET | retrieve subscription data for client |
| ```/ated/:atedRefNo/subscription-data``` | POST | update subscription data for client |
| ```/agent/:agentCode/ated/subscription-data/:atedRefno``` | GET | retrieve subscription data for agent |


## Form-bundle return API

APIs for retrieving return based on form-bundle number

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:atedRefNo/returns/form-bundle/:formBundleNumber``` | GET | retrieve submitted return details based on form-bundle number |

where,

| parameters | description |
|------|-------------------|
| formBundleNumber | return submission identifier from ETMP |

### Summary return APIs

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:atedRefNo/returns/partial-summary``` | GET | retrieve partial-summary submitted return details based on form-bundle number |
| ```/ated/:atedRefNo/returns/full-summary``` | GET | retrieve full-summary submitted return details based on form-bundle number |

## Edit submitted Chargeable Return APIs

## Change submitted chargeable return APIs

APIs for changing an already submitted return based on old form-bundle number

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:ated/liability-return/:oldFBNo``` | GET | retrieve chargeable return with old form bundle number for change |
| ```/ated/:ated/prev-liability-return/:oldFBNo/:period``` | GET | retrive chargeable return with old form bundle number and period |
| ```/ated/:ated/liability-return/:oldFBNo/update-has-bank``` | POST | update the chargeable return with has bank indicator |
| ```/ated/:ated/liability-return/:oldFBNo/update-bank``` | POST | update the chargeable return with has bank details |
| ```/ated/:ated/liability-return/calculate/:oldFBNo``` | GET | calculate the edited chargeable return |
| ```/ated/:ated/liability-return/:oldFBNo/submit``` | POST | submit the edited chargeable return |

where,

| parameters | description |
|------|-------------------|
| oldFBNo | last return submission identifier (from ETMP) |

## Dispose submitted chargeable return APIs

APIs for disposing an already submitted return based on old form-bundle number

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:ated/dispose-liability/:oldFBNo```| GET | retrieve chargeable return with old form bundle number for disposal |
| ```/ated/:ated/dispose-liability/:oldFBNo/update-date```| POST | update the chargeable return with date |
| ```/ated/:ated/dispose-liability/:oldFBNo/update-has-bank```| POST | update the chargeable return with has bank indicator |
| ```/ated/:ated/dispose-liability/:oldFBNo/update-bank``` | POST | update the chargeable return with has bank details |
| ```/ated/:ated/dispose-liability/:oldFBNo/calculate```|	GET	| calculate the edited chargeable return |
| ```/ated/:ated/dispose-liability/:oldFBNo/submit```|	POST | dispose the edited chargeable return |

where,

| parameters | description |
|------|-------------------|
| oldFBNo | last return submission identifier (from ETMP) |

#### Relief Response

```json
 [{
 	"atedRefNo": "ATED1223123",
 	"periodKey": 2017,
 	"reliefs": {
 		"periodKey": 2017,
 		"rentalBusiness": false,
 		"openToPublic": false,
 		"propertyDeveloper": false,
 		"propertyTrading": false,
 		"lending": false,
 		"employeeOccupation": false,
 		"farmHouses": false,
 		"socialHousing": false,
 		"equityRelease": false
 	},
 	"taxAvoidance": {
 		"rentalBusinessScheme": "Scheme1",
 		"rentalBusinessSchemePromoter": "Promoter1",
 		"openToPublicScheme": "Scheme2",
 		"openToPublicSchemePromoter": "Scheme2",
 		"propertyDeveloperScheme": "Scheme3",
 		"propertyDeveloperSchemePromoter": "Scheme3",
 		"propertyTradingScheme": "Scheme4",
 		"propertyTradingSchemePromoter": "Promoter4",
 		"lendingScheme": "Scheme5",
 		"lendingSchemePromoter": "Promoter5",
 		"employeeOccupationScheme": "Scheme6",
 		"employeeOccupationSchemePromoter": "Promoter6",
 		"farmHousesScheme": "Scheme7",
 		"farmHousesSchemePromoter": "Promoter7",
 		"socialHousingScheme": "Scheme8",
 		"socialHousingSchemePromoter": "Promoter8",
 		"equityReleaseScheme": "Scheme9",
 		"equityReleaseSchemePromoter": "Promoter9"
 	},
 	"periodStartDate": "2017-04-01",
 	"periodEndDate": "2018-03-31",
 	"timeStamp": {
 		"$date": 1508239198714
 	}
 }]
```

#### Property Details Response With Status Code



#### Response With Status Code

| Status | Message     |Body         |
|-------|-------------|-------------|
| 200   | Ok          |             |
| 400   | Bad Request | Invalid Request |

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").