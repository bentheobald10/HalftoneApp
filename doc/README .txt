OVERVIEW OF PROJECT
The Halftone application allows the user to either take a photo or choose an image from their gallery, and apply a Halftone filter to the image. The applicaiton can also let the user add a text caption above or below the image.
The second iteration allows the user to select which shape they would like to use for the halftone image, either a circle, rectang or diamond. The user can also select to rotate the halftone grid by an angle from 0 to 45 degrees, and get the resulting image.
We have also added a new filter, a negative filter which is a grayscale image that has had the colours reversed. So all dark shades will now be ligt and the light shade will become dark, just like camera file negative.

RESPONSIBLE HUMANS
The people responsible for the development of the application are Ben Theobald and Jake Spicer. They will design, implement and test the application, and get it into a state where the client will be able to add it to the GOogle Play Store. The clients are Robert Merkel and Robyn McNamara.

WHAT HAS BEEN IMPLEMENTED
We have been able to implement all basic user stories. Our application can take a photo with the camera or get an image form the gallery. From there, we can halftone the image or apply a grayscale filter to the image, and add a caption. Lastly, the image can be saved to the internal storage or shared to social media appliacitons.
We have also been able to implement the new user stories of allowing the user to select the halftone shape to use and to rotate the grid by a specified angle. We been able to implement the negatve filter also.

GETTING STARTED
To get started using the application, the home screen will ask you where you would liek to get oyur image from, either take a photo or from the gallery. Once you have selected an image, you will then be shown that image with no filter. We have a "Halftone Options" button that allows the user to select the halftone shape and rotation angle. You can select a Halftone, Grayscale  or Negative filter. You also have the option to add a text caption above or below the image. Once the image has a filter and caption, you can then share it to capable applications on the device, like social media.

BUGS AND LIMITATIONS
A limitation of the applicaiton is that it can't handle high quality images. If the image is too large to fit the image space, then it isn't added. Another limitation is that the caption is only one size and fint so far.

TESTING
For our unit tests, we have created a new package called test.com.monash.halftone.model that has two java JUnit classes contating our tests. These are for the Cpation and the Halftone classes, and can be run using Java JUnit.