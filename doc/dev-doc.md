# Development Documentation
The project is divided into four main components
- Muse Monitor: Android
- Muse Monitor: Patient Service
- Muse Monitor: Socket Server
- Muse Monitor: Socket Client (Web)

This components can be seen by the label below

## Muse Monitor: Android
The "Muse Monitor: Android" project contains the code to run the Android application which interacts with the patient's Muse Headband. "Muse Monitor: Android" directly communicates with the Muse Headband, "Muse Monitor: Patient Service", and the "Muse Monitor: Socket Server" components. The Android application communicates with the "Muse Monitor: Patient Service" component to register and retrieve patient information. The Android application communicates with the "Muse Monitor: Socket Server" component to stream the Muse Headband data.

To edit the code for the "Muse Monitor: Android" application, Android Studio IDE is recommended.

## Muse Monitor: Patient Service
The "Muse Monitor: Patient Service" project contains code to run the Patient Management REST Service which provides the web service to store and retrieve patient information. "Muse Monitor: Patient Service" directly communicates with the Patient Mongo DB, "Muse Monitor: Android", and the "Muse Monitor: Socket Client (Web)" MVC Service. The web service communicates with the Patient Mongo DB to persist patient data. The web service communicates with "Muse Monitor: Android" to provide registration and data retrieval functionality for the app. The web service communicates with "Muse Monitor: Socket Client (Web)" MVC Service to allow dynamic fields to be filled with patient information.

To edit the code for the "Muse Monitor: Patient Service" application, Spring Tool Suite 3.8.4 is recommended. To test the web service, it is recommended to use a web service client tester such as SoapUI or Postman (recommended).

## Muse Monitor: Socket Server
The "Muse Monitor: Socket Server" project contains code to run the websocket server to stream the patient data and indicators. "Muse Monitor: Socket Server" directly communicates with the "Muse Monitor: Android" and the 'Muse Monitor: Socket Client (Web)" front-end pages. The websocket server communicates with "Muse Monitor: Android" to retrieve the verbose Muse data (EEG, ACCEL, GYRO). The websocket service communicates with the "Muse Monitor: Socket Client (Web)" front-end pages to serve the verbose Muse data (EEG, ACCEL, GYRO) and the patient indicators (Seizure, Falls).

To edit the code for the "Muse Monitor: Socket Server" application, Spring Tool Suite 3.8.4 is recommended. The "Muse Monitor: Socket Server Tester" project is provided to test the websocket server which requires a browser compatible with websocket communication (i.e. latest version of Google Chrome).

## Muse Monitor: Socket Client (Web)
The "Muse Monitor: Socket Client (Web)" project contains code to run the web client to visualize the patient information and interface the medical alert system. "Muse Monitor: Socket Client (Web)" directly communicates with the "Muse Monitor: Patient Service" and the "Muse Monitor: Socket Server". The web client communicates with the "Muse Monitor: Patient Service" to dynamically fill in any patient information in the web page. The web client communicates with the "Muse Monitor: Socket Server" to retrieve patient data (EEG, ACCEL, GYRO) and indicators (Seizure, Falls).

To edit the code for the "Muse Monitor: Socket Client (Web)" application, Spring Tool Suite 3.8.4 is recommended. To test the web client, a compatible browser with websocket communication is recommended (i.e. latest version of Google Chrome).
