# MVVM Architecture Toolkit
This is only a personal implementation of MVVM architecture that makes your life easier by helping you to keep your screen components independently. It also has a concept of "interaction" defining exactly what the user can do on your screen turning the testing process extremely easier, once now you are able to test the "state" of your app.

It is extremely simple to use it and to test it. But again, it is only a personal implementation. However, if this library help you anyway, please give me a star :)

## Download
```groovy
ext {
    MVVM_ARC_VERSION = '2.1.0'
}

// [REQUIRED] Core library. With the architecture components.
implementation "io.github.mayconcardoso:mvvm-core:${MVVM_ARC_VERSION}"

// [OPTIONAL] Core Extension library. With the architecture components extensions to bind states and so on.
implementation "io.github.mayconcardoso:mvvm-core-ktx:${MVVM_ARC_VERSION}"

// [OPTIONAL] Testing library. To test your architecture easily with contextual functions to make your tests cleaner.
testImplementation "io.github.mayconcardoso:mvvm-core-testing:${MVVM_ARC_VERSION}"

// [OPTIONAL] Networking library. To help you create your APIs easily with mapped errors to better handle business logic and avoid crashes.
implementation "io.github.mayconcardoso:networking:${MVVM_ARC_VERSION}"

// [OPTIONAL] Simpler recyclerview li. To help you create your APIs easily with mapped errors to better handle business logic and avoid crashes.
implementation "io.github.mayconcardoso:simple-recyclerview:${MVVM_ARC_VERSION}"

``` 

## Related Library

[Architecture Boilerplate Generator](https://github.com/MayconCardoso/ArchitectureBoilerplateGenerator) - It is a personal code generator to create new features and to avoid writing a lot of boilerplate.

## Documentation
* [Core Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core)
* [Core Extensions Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core-extentions)
* [Core Testing Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core-testing)
* [Core Networking Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/networking)
* [Core Networking Library](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/simple-recyclerview)

## Sample

Here are a couple of real android Apps implementing this library to define their architecture.
* [Poker Grinder](https://github.com/MayconCardoso/poker-grinder)
* [StockTradeTracking](https://github.com/MayconCardoso/StockTradeTracking)
