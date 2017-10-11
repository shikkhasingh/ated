ated
====

Microservice for Annual Tax on Enveloped Dwellings. This implements the main business logic for ATED, communicating with ETMP(HOD) and Mongo Database for storage/retrieval. The microservice is based on the RESTful API structure, receives and sends data using JSON to either from.

All data received is validated against the relevant schema to ensure correct format of the data being received.

[![Build Status](https://travis-ci.org/hmrc/ated.svg?branch=master)](https://travis-ci.org/hmrc/ated) [ ![Download](https://api.bintray.com/packages/hmrc/releases/ated/images/download.svg) ](https://bintray.com/hmrc/releases/ated/_latestVersion)

The APIs listed below are invoked for different operations from the frontend micro service. They are grouped according to the functionality.

## Relief Return APIs

APIs for creating a relief return where,
    periodKey = starting year of tax year (e.g. 2016 for '16-'17)
    atedRefNo = unique identfier for clients subscribed to ATED

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
|```/ated/:atedRefNo/ated/reliefs/save``` | POST | saves the draft relief |
| ```/ated/:atedRefNo/ated/reliefs/:periodKey``` | GET | retrieve the draft relief based on period |
|```/ated/:atedRefNo/ated/reliefs/submit/:periodKey``` | GET | submit the draft return |
|```/ated/:atedRefNo/ated/reliefs/drafts``` | DELETE | delete the draft relief |
|```/ated/:atedRefNo/ated/reliefs/drafts/:periodKey``` | DELETE | delete the draft relief by year |


## Chargeable Return APIs

APIs for creating a chargeable return where,
    id = chargeable return id
    atedRefNo = unique identfier for clients subscribed to ATED

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:atedRefNo/property-details/create/:periodKey``` | POST | create the draft chargeable property |
| ```/ated/:atedRefNo/property-details/retrieve/:id``` | GET | retrieve the draft chargeable property |
| ```/ated/:atedRefNo/property-details/address/:id``` | POST | update the draft chargeable property with address |
| ```/ated/:atedRefNo/property-details/title/:id``` | POST | update the draft chargeable property with title |
| ```/ated/:atedRefNo/property-details/has-value-change/:id``` | POST | update the draft chargeable property with change in value |
| ```/ated/:atedRefNo/property-details/acquisition/:id``` | POST | update the draft chargeable property with acquisition details |
| ```/ated/:atedRefNo/property-details/revalued/:id``` | POST | update the draft chargeable property with revalued details |
| ```/ated/:atedRefNo/property-details/owned-before/:id``` | POST | update the draft chargeable property with owned-before details | 
| ```/ated/:atedRefNo/property-details/new-build/:id``` | POST | update the draft chargeable property with new-build details |
| ```/ated/:atedRefNo/property-details/valued/:id``` | POST | update the draft chargeable property with valued details |
| ```/ated/:atedRefNo/property-details/full-tax-period/:id``` | POST | update the draft chargeable property for full tax period |  
| ```/ated/:atedRefNo/property-details/in-relief/:id``` | POST | update the draft chargeable property with in relief details |
| ```/ated/:atedRefNo/property-details/dates-liable/:id``` | POST | update the draft chargeable property with dates liable |
| ```/ated/:atedRefNo/property-details/dates-liable/add/:id``` | POST | add liable dates in the draft chargeable property |
| ```/ated/:atedRefNo/property-details/dates-in-relief/add/:id``` | POST | add in relief dates in the draft chargeable property |
| ```/ated/:atedRefNo/property-details/period/delete/:id``` | POST | delete a period in the draft chargeable property |
| ```/ated/:atedRefNo/property-details/tax-avoidance/:id``` | POST | update the draft chargeable property with tax-avoidance |
| ```/ated/:atedRefNo/property-details/supporting-info/:id``` | POST | update the draft chargeable property with supporting-info |
| ```/ated/:atedRefNo/property-details/calculate/:id``` | GET | calculate the draft chargeable property for mode = Pre-Calculation |
| ```/ated/:atedRefNo/property-details/submit/:id``` | POST | submit the draft chargeable property for mode = Post |

## Client and Agent Registration Details APIs

APIs for registering clients/agents where,
    atedRefNo = unique identfier for clients subscribed to ATED
    identifierType = arn or safeId or utr
    identifier = the number for the above types
    agentCode = agent code

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:atedRefNo/ated/details/:identifier/:identifierType``` | GET | retrieve ATED client registration details |
| ```/agent/:agentCode/ated/details/:identifier/:identifierType``` | GET | retrieve agent registration details |
| ```/ated/:atedRefNo/registration-details/:safeId``` | POST | update ATED client registration details |

## Client and Agent Subscription Details APIs

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:atedRefNo/subscription-data ``` | GET | retrieve subscription data for client |
| ```/ated/:atedRefNo/subscription-data``` | POST | update subscription data for client |
| ```/agent/:agentCode/ated/subscription-data/:atedRefno``` | GET | retrieve subscription data for agent |


## Form-bundle return API

APIs for retrieving return based on form-bundle number where,
    formBundleNumber = return submission identifier from ETMP

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:atedRefNo/returns/form-bundle/:formBundleNumber``` | GET | retrieve submitted return details based on form-bundle number |


### Summary return APIs

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:atedRefNo/returns/partial-summary``` | GET | retrieve partial-summary submitted return details based on form-bundle number |
| ```/ated/:atedRefNo/returns/full-summary``` | GET | retrieve full-summary submitted return details based on form-bundle number |

## Edit submitted Chargeable Return APIs

## Change submitted chargeable return APIs

APIs for changing an already submitted return based on old form-bundle number where,
    oldFBNo = last return submission identifier (from ETMP)

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:ated/liability-return/:oldFBNo``` | GET | retrieve chargeable return with old form bundle number for change |
| ```/ated/:ated/prev-liability-return/:oldFBNo/:period``` | GET | retrive chargeable return with old form bundle number and period |
| ```/ated/:ated/liability-return/:oldFBNo/update-has-bank``` | POST | update the chargeable return with has bank indicator |
| ```/ated/:ated/liability-return/:oldFBNo/update-bank``` | POST | update the chargeable return with has bank details |
| ```/ated/:ated/liability-return/calculate/:oldFBNo``` | GET | calculate the edited chargeable return |
| ```/ated/:ated/liability-return/:oldFBNo/submit``` | POST | submit the edited chargeable return |

## Dispose submitted chargeable return APIs

APIs for disposing an already submitted return based on old form-bundle number where,
    oldFBNo = last return submission identifier (from ETMP)

| PATH | Supported Methods | Description |
|------|-------------------|-------------|
| ```/ated/:ated/dispose-liability/:oldFBNo```| GET | retrieve chargeable return with old form bundle number for disposal |
| ```/ated/:ated/dispose-liability/:oldFBNo/update-date```| POST | update the chargeable return with date |
| ```/ated/:ated/dispose-liability/:oldFBNo/update-has-bank```| POST | update the chargeable return with has bank indicator |
| ```/ated/:ated/dispose-liability/:oldFBNo/update-bank``` | POST | update the chargeable return with has bank details |
| ```/ated/:ated/dispose-liability/:oldFBNo/calculate```|	GET	| calculate the edited chargeable return |
| ```/ated/:ated/dispose-liability/:oldFBNo/submit```|	POST | dispose the edited chargeable return |

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
