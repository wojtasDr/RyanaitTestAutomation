Story - Perform flight booking with invalid data, check if payment declined.

Meta:
	@author Wojciech Pulawski
	@script 
	
Narrative:
In order to 
As a 
I want to 

Scenario: Perform flight booking with invalid credit card number, check if payment declined.
Given User is logged in
Given Passenger makes booking with following data:
|departureAirport|destinationAirport|departureDate|adultsNumber|teensNumber|
|Budapest|Barcelona|17-02-2018|2|1|
When Payment is done with card number 5188608900375138, card type MasterCard Debit, expiry month 9, expiry year 2020, security code 124, card owner Jan Kowalski
When Payment is done for billing address Al. Jerozolimskie, 32a, city Warszawa, postal code 00-000, country Poland
Then Payment is declined and following card error text is displayed: Card number is invalid
