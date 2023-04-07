# Core Networking Library

This is a networking module to create APIs easily without boilerplate. 
It also maps all network issues and translates it to another exception to make it possible to handle network error.

All you have to do is surround your network call with the function ```secureRequest``` and then you avoid crashes related to network.
With that you also get a predefined and high level mapped exception where you can decided what to do based on your retry police, etc. 

```kotlin
class YourRemoteDataSource(
  private val yourRetrofitOrAnyOtherApi: YourApi,
) {
  
  suspend fun fetchYourData(): List<YourData> = secureRequest { 
    yourRetrofitOrAnyOtherApi.fetchYourData()
  }
  
}
```

If anything unexpected happened, one of those [pre mapped exceptions](io.github.mayconcardoso.networking/NetworkErrorTransformer.kt) will be thrown and you can decide what to do. 

