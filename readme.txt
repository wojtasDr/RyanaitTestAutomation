Project RyanairTestAutoation is using following tools:
Java SE, Selenium WebDriver, JBehave. Mentioned project is classic Maven project.

In order to run it please  make:
I. Environment Setup:
1. Install maven on your pc.
2. Install Java on your pc.
3. Clone project from github https://github.com/wojtasDr/RyanaitTestAutomation to your local disc area.
4. Download chromedriver.exe (https://chromedriver.storage.googleapis.com/index.html?path=2.33/)
suitable for your os and copy it (windows 64bit version available in cloned project) to any location on your disc area.
5. Open pom.xml of mentioned project.
6. Go to <profiles> and find active profile <activeByDefault>true</activeByDefault>
(by default "Ryanair Booking Site GB is active").
6. Set <chromedriver.url></chromedriver.url> param of active profile. 
Put url of chromedriver.exe location on your disc area
e.g. <chromedriver.url>C:/Users/wojtas_dr/Desktop/Projects/RyanaitTestAutomation/chromedriver.exe</chromedriver.url>

II. Test Execution:
1. Open git bash console, navigate to directory with pom.xml and run following maven command
mvn clean install -DskipTests=true
2.Test was started. After ca. 80s it should be done and finished with success (Build success, result passed).
Log file can be found in \automation\src\test\resultFiles\testLogs
After each run app_timestamp.log file is created.
Example of log file is attached to this project: app-2017-11-26_18_03_34.log
There is also recording TestExecution.mp4 from mentioned test execution.
Normally test execution takes ca. 70-80s, however recording has 3 minutes.
When screen capturer is active test is going pretty slow. Strange but true. :)
Test was developed using PC with Windows installed.
I did not have chance to execute it on MacOs, I hope it goes without any problems.


TestCase description:
Automated test case was designed in Gherkin syntax (Given When Then).
It contais following structure:

Given User is logged in
Given Passenger makes booking with following data:
|departureAirport|destinationAirport|departureDate|adultsNumber|teensNumber|
|Budapest|Barcelona|17-02-2018|2|1|
When Payment is done with card number 5188608900375138, card type MasterCard Debit, expiry month 9, expiry year 2020, security code 124, card owner Jan Kowalski
When Payment is done for billing address Al. Jerozolimskie, 32a, city Warszawa, postal code 00-000, country Poland
Then Payment is declined and following card error text is displayed: Card number is invalid

Test Steps description:
1. Given User is logged in
Login user to Ryanair booking site and verify if it was logged in.
2. Given Passenger makes booking with following data:
Fills in flight details data for (From, To, Date, Passangers)
Check if fields were fillec correctly.
And goes To payment page.
3. When Payment is done with card number 5188608900375138, card type MasterCard Debit, expiry month 9, expiry year 2020, security code 124, card owner Jan Kowalski
Fills in Passenger Data Form and Credit Card Form on Payment Page
4. When Payment is done for billing address Al. Jerozolimskie, 32a, city Warszawa, postal code 00-000, country Poland
Following When step fills in Billing Address Form.
5. Then Payment is declined and following card error text is displayed: Card number is invalid
Following Then checks accept policy checkbox and set it if not set. It also tries to make payment. Payment is declined because of invalid card
number. Step check if invalid card number is visible and payment is declined.


