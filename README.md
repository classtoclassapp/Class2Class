![C2C Logo](/app/src/main/res/mipmap-xhdpi/c2c_icon_round.png)

# Class to Class APP
Public repository including the source code of C2C App. This application is currently being developed for ETSI Bilbao - EHU. If you want more information about our app, feel free to enter into our webpage [C2C App](http://classtoclassapp.github.io).

## Main Functions
Our application consists of two important functions:

- **Class Locator using AR and Google Maps API:** Students can use their mobile phone to locate different classrooms. They only have to introduce the classroom "code" and read a QR marker to calculate the route from their actual position to their destination.
- **Social Space:** Users can rapidly post announcements related to the student field. This kind of _forum_ allows the users to have more interaction with other people from their same university.

>This applicaction is in an early stage of development. The functions previously described work properly, but they can be improved in order to offer a better user experience. Those improvements will me made progressively.

## Implementation and Use

This app is currently being developed for Android devices. This means that anyone can clone the project, make the corresponding changes in Android Studio and run the application. In order to clone the project use the following command:

`git clone https://github.com/classtoclassapp/Class2Class.git`

The second step you must follow is creating a Google Maps API Key. The key used in this implementation is linked to our project, so, if you want to create a new project with your own modifications, you will need a new one. First of all, go to this file and open it:

`-> Class2Class\app\src\debug\res\values`

Inside, you will see a link where you can generate an API Key. Copy that key inside the file:

`<string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">YOUR_KEY_HERE</string>`

The last step is building the project. Android Studio IDE will make it automatically, and you can generate an APK File to test the application in your mobile device.

## Libraries and Technologies Used
The application may seem simple. However, it makes use of a great deal of libraries and different technologies:

 - QR Code Handler: [ZXing Project](https://github.com/zxing/zxing) 
 - ARTookit for JavaScript : [AR.js](https://github.com/jeromeetienne/AR.js)
 - 3D Model Loader for JavaScript: [A-Frame Library](https://aframe.io/)
 - WebView for Android: [Webview](https://developer.android.com/reference/android/webkit/WebView)
 - Google Maps API for Android: [GMaps API](https://developers.google.com/maps/android/?hl=es-419)
 - MongoDB Hosting: [mLab](https://mlab.com/)
 - Android Developments Tools and Languages (Android Studio IDE, Java, JavaScript, HTML)

## Screenshots
You can see some captures at our webpage [C2C App](http://classtoclassapp.github.io).


## Developers
Our organization is formed by these members: 

- Alberto de la Cruz
- Alicia Fernández
- Jose Luis González
- Leticia Ortiz

If you want to be a collaborator, contact us at [classtoclassapp@gmail.com](mailto://classtoclassapp@gmail.com).
