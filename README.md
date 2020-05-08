# MVVM Architecture Toolkit
This is only a personal implementation of MVVM architecture that makes your life easier by helping you to keep your screen components independently. It also has a concept of "interaction" defining exactly what the user can do on your screen turning the testing process extremely easier, once now you are able to test the "state" of your app.

It is extremely simple to use it and to test it. But again, it is only a personal implementation. However, if this library help you anyway, please give me a star :)

## Download
```groovy
// [REQUIRED] Core library. With the architecture components.
implementation 'com.mctech.architecture.mvvm:x-core:1.5.1'

// [OPTIONAL] Core Estension library. With the architecture components extentions.
implementation 'com.mctech.architecture.mvvm:x-core-ktx:1.5.1'

// [OPTIONAL] Android Views extentios. 
implementation 'com.mctech.architecture.mvvm:x-view-ktx:1.5.1'

// [OPTIONAL] DataBinding library. If you wanna use the 'States' DataBinding adapters.
// [REQUIRES] 'com.mctech.architecture.mvvm:x-view-ktx:x.x.x' library
implementation 'com.mctech.architecture.mvvm:x-core-databinding:1.5.1'

// [OPTIONAL] Networking library. To help you create your APIs easily
implementation 'com.mctech.architecture.mvvm:x-core-networking:1.5.1'

// [OPTIONAL] Testing library. To test your architecture easily
testImplementation 'com.mctech.architecture.mvvm:x-core-testing:1.5.1'

``` 

## Related Library

[Architecture Boilerplate Generator]() - It is a personal code generator to create new features and to avoid writing a lot of boilerplate.

## Documentation
* [Core Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core)
* [Core Extensions Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core-extentions)
* [Core DataBinding Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core-databinding)
* [Core Testing Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core-testing)
* [Core Networking Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core-networking)
* [View Extensions Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/view-extentions)

## Sample

Here is a [Real Android App](https://github.com/MayconCardoso/StockTradeTracking) implementing this library to define its architecture.
