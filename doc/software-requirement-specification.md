# Software Requirement Specification Document: Muse Monitor
## Introduction
### Purpose
The purpose of the document is to allow the developers and stakeholders to introduce, describe, and provide detailed analysis about Muse Monitor. For technical developers, the document will provide in-depth knowledge on the various technical aspects of the project such as architecture, data sources, external libraries and APIs, etc. For application users, it provides a description of functionalities that the application contains. For stakeholders, it provides functionality and the scope of the application.
### Product Scope
Muse Monitor is a multi-tier application and medical alert system which retrieves EEG, accelerometer, and gyroscope data from the Muse Headband to ultimately detect if the inidividual wearing the headband is having a fall or a seizure.
## Overall Description
### Product Perspective
Muse Monitor is unique as it takes advantage of portable monitoring devices (and specifically portable EEG device) to make significant determinations on the well-being of an individual. Only recently has cost-effective and portable EEG device entered the market, hence, the opportunity to utilize these emerging devices for healthcare is profound. We find this application valuable in the hospital setting by monitoring the well-being of patients.

Currently, Muse Monitor utilizes the following available data from the Muse Headband: EEG (TP9, Fp1, Fp2, TP10, DRL, REF), Accelerometer (ACC_X, ACC_Y, ACC_Z), and Gyroscope (GYRO_X, GYRO_Y, GYRO_Z). The stated data is used to determine the following indicators: Epileptic Health (Normal EEG, Interictal EEG, and Ictal EEG), and Falls (Fall, non-Fall).
### Product Functions
In the Android PatientFormActivity, the user will enter the patient information to be stored in a database and used for data identification. In the PatientStreamActivity, the user will connect the Muse Headband to the Android device via Bluetooth LE. Once connected, the Muse Headband will stream all necessary data to the Android device. Simultaneously, the Android device will stream the data to a socket service using STOMP websockets. The socket service will then analyze the data and determine the indicators which are to be sent to the clients.

The user can then login to the Muse Monitor web client and view the list of all connected patients. The stated page will retrieve the indicators from the socket service and display the indicators on the patient list page. The user can then click on a patient in the list to open the patient details page. The patient details page will retrieve both the indicators and the raw data from the socket service to ultimately display the indicators in a table and visualize the data in a graph.
### User Classes and Characteristics
The users of the application are primarily directed towards nurses and patients at hospitals and other clinical environments.

### Operating Environment
The mobile application currently runs on an Android device (with minimum OS version: Android 4.4). The socket service, database, and patient REST service currently runs on Heroku. The web client currently runs on any web browser compatible with websocket communication, JavaScript, and jQuery.

### Design and Implementation Constraints
The main design constraint is requiring the web client browser to be compatible with websocket communication. Currently, only high-end web browsers are compatible with websocket as the communication protocol is a relatively emerging technology. An idea to support more web browsers is by implementing SockJS, a socket emulator for extending streaming communication to other protocols instead of just websockets depending on the compatability of the browser.
### User Documentation
Optionally, an online user manual and a video tutorial will be available on the website for application users.
## External Interface Requirements
### User Interfaces
Muse Monitor requires the user to have a Muse Headband (2014 or 2016), a compatible Android device, and a compatible web browser.
### Software Interfaces
Muse Monitor will be dependent on the following third party software interfaces for its functionality:
- Muse SDK – For utilizing the Muse Headband interface, [source]( http://www.choosemuse.com/developer-kit/)
- Android SDK – For building the Android application, [source](https://developer.android.com/index.html)
- StompProtocolAndroid - For connecting to the STOMP protocol and the Android device, [source](https://github.com/NaikSoftware/StompProtocolAndroid)
- Spring Android - Framework that provides Spring functionality to the Android SDK, [source](http://projects.spring.io/spring-android/)
- Spring MVC - For serving the front-end pages for the web client, [source](https://spring.io/guides/gs/serving-web-content/)
- Spring Boot - Framework for building modern Spring applications, [source](https://projects.spring.io/spring-boot/)
- Spring Websockets - For creating a Spring websocket server, [source](https://spring.io/guides/gs/messaging-stomp-websocket/)
- Spring Rest - For creating a Spring REST web service, [source](https://spring.io/guides/gs/rest-service/)
- Spring Data (Mongo) - For connecting to a MongoDB database, [source](http://projects.spring.io/spring-data-mongodb/)
- Spring Security (LDAP) - For securing a Spring application, [source](https://docs.spring.io/spring-security/site/docs/3.1.x/reference/ldap.html)
- MongoDB - NoSQL DBMS for creating a document-based database, [source](https://www.mongodb.com/)
- Bootstrap – An open source HTML, CSS, and JavaScript framework for responsive website development, [source](http://getbootstrap.com/)
- jQuery – A JavaScript library containing many features such as HTML document traversal, manipulation, etc, [source](http://jquery.com/)
- Datatables – A table plugin for the jQuery library that includes pagination, filtration, etc, [source](https://datatables.net/)
- Plotly.js - For creating responsive charts in JavaScript using SVG maps, [source](https://plot.ly/javascript/)
- webstomp-client - For connecting to a socketserver in a JavaScript environment, [source](https://github.com/JSteunou/webstomp-client)
### Communication Interfaces
Muse Monitor requires Bluetooth-LE communication between the Muse headband and Android device, STOMP websocket communication between all socket service connections, HTTPS communication between all web service connections.

## System Features
### Patient Registration
#### Status
Required
#### Description
The user must be able to register a patient in the PatientFormActivity of the Android application. The user must also be able to sign in as a registered patient using the patient's name or ID.
#### Technical Overview
The PatientFormActivity should include appropriate fields to enter the described information. The information should asynchronously be sent to the PatientManagementService through the REST/HTTP protocol. The PatientManagementService muse provide endpoints to retrieve a patient via ID (GET), retrieve a patient via name (GET), and register a patient (POST). The PatientManagementSystem will retrieve and store data in a Mongo database.
### Data Streaming
#### Status
Required
#### Description
The nurse must be able to visualize the verbose patient data on the web client. The visualization must include the acceleration magnitude, angular velocity magnitude, and all EEG channels (TP9, Fp1, Fp2, TP10, DRL, REF).
#### Technical Overview
The data must be streamed from the Muse Headband to the Android, from the Android to the socket server, then from the socket server to the client. The protocol in which the data is streamed from the Muse Headband to the Android application is done through Bluetooth-LE (Muse 2016) or Bluetooth (Muse 2014). The protocol in which the data is streamed from the Android to the socket server is done through websockets over the STOMP protocol. Preferably, SockJS should be used for compatibility among several interfaces. The protocol in which the data is streamed from the socket server to the web client is done through websockets over the STOMP protocol. As said, SockJS is the preferable method for implementing STOMP communication. The web client will visualize the data using Plotly.js.
### Fall Detection
#### Status
Required
#### Description
The web client must give an alert whenever a fall is detected from the patient.
#### Technical Overview
The analysis for the fall detection is done at the socket server level. The logic to implement the fall detection is described [here](https://www.hindawi.com/journals/js/2015/452078/). Once the fall detection determines a new status (from negative to positive, or positive to negative), the indicator must be streamed to the web client. The socket connection should be separate from the connection which streams the verbose data. The web client will notify the users via notify.js. All emergency indicators will also be placed in a table using Datatables.
### Seizure Detection
#### Status
Required
#### Description
The web client must give an alert whenever a seizure is detected from the patient.
#### Technical Overview
The analysis for the seizure detection is done at the socket server level. The logic to implement the seizure detection is described [here](http://liu.diva-portal.org/smash/get/diva2:746664/FULLTEXT01.pdf). Once the seizure detection determines a new status (from negative to positive, or positive to negative), the indicator must be streamed to the web client. The socket connection should be separate from the connection which streams the verbose data. The web client will notify the users via notify.js. All emergency indicators will also be placed in a table using Datatables.

### Appendix A: Glossary
- EEG – Test/data to detect or represent the electrical activity in the brain, [source](http://www.mayoclinic.org/tests-procedures/eeg/basics/definition/prc-20014093)
- Accelerometer – Device to measure the acceleration of an object, [source](https://en.wikipedia.org/wiki/Accelerometer)
- Gyroscope – Device to measure the orientation and angular velocity of an object, [source](https://en.wikipedia.org/wiki/Gyroscope)
- Interictal EEG – EEG data of an individual who has previous history of epilepsy. This is precisely described as the period between seizures, stroke, etc.  [source](https://en.wikipedia.org/wiki/Ictal)
- Ictal EEG – EEG data of an individual who is currently undergoing a seizure, [source](https://en.wikipedia.org/wiki/Ictal)
