# MVVM Architecture Toolkit
This is only a personal implementation of MVVM architecture using the concept of "State Machine" to keep your screen components independently. It also has a concept of "interaction" defining exactly what the user can do on your screen turning the testing process extremely easier. Because now you are able to test the "state" of your system.

It is extremely simple to use it and to test it. But again, it is only a personal implementation.

## Download (Not available yet. Uploading to Jcenter)
```groovy
// [REQUIRED] Core library. With the architecture components.
implementation 'com.mctech.architecture.mvvm:core:1.0.0'

// [OPTIONAL] Android Views extentios. 
implementation 'com.mctech.architecture.mvvm:view-ktx:1.0.0'

// [OPTIONAL] DataBinding library. If you wanna use the 'States' DataBinding adapters.
// [REQUIRES] 'com.mctech.architecture.mvvm:view-ktx:x.x.x' library
implementation 'com.mctech.architecture.mvvm:core-databinding:1.0.0'

// [OPTIONAL] Testing library. To test your architecture easily
testImplementation 'com.mctech.architecture.mvvm:core-testing:1.0.0'
``` 

## Documentation
* [Core Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core)
* [Core DataBinding Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core-databinding)
* [Core Extentions Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core-ktx)
* [Core Testing Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core-testing)
* [View Extetions Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/view-extentions)

## Sample

Here is a simple [sample](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/sample) to show a little bit what we can do if this library. 
