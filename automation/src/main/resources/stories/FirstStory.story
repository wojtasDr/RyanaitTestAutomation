Story - Example.

Meta:
	@author Wojciech Pulawski
	@script 
	
Narrative:
In order to 
As a 
I want to 

Scenario: Example
Given User is logged in
Given Passenger makes booking with following data:
|departureAirport|destinationAirport|departureDate|adults|adultsNumber|
|Budapest|Barcelona|17-02-2018|Adults|3|
When Payment is done with card number 5188608900375138
Then Payment is declined and following card error text is displayed: Card number is invalid
