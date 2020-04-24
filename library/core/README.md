# Core Library

In this module we are going to see the core MVVM architecture. There are only [a few classes](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core/src/main/java/com/mctech/architecture/mvvm/core) here, but they are very important

### [ComponentState](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/blob/master/library/core/src/main/java/com/mctech/architecture/mvvm/core/ComponentState.kt)

This is the coolest one to me. We know how difficult is to handle many different components on our screen, don't we? Thinking about that, I have been using this simple sealed class that defines all states my component can be. 
So now, I do not need to care about the lifecycle of my component ever since I use this class attached to your view lifecycle. 

### [UserInteraction](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/blob/master/library/core/src/main/java/com/mctech/architecture/mvvm/core/UserInteraction.kt)

This represents every single event the user performs on your screen/component. Those events will be stored in a Stack on your BaseViewModel to help you track the user flow and make it possible to test your code.

### [ViewCommand](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/blob/master/library/core/src/main/java/com/mctech/architecture/mvvm/core/ViewCommand.kt)

It is basically the same as UserInteraction but it is called inside your ViewModel to send same event that will be consumed only once by your view. 

### [BaseViewModel](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/blob/master/library/core/src/main/java/com/mctech/architecture/mvvm/core/BaseViewModel.kt)

Last but not least important, the BaseViewModel. I do not like base classes overall, to be honest. But I use this BaseViewModel class a lot. Because it has only a few methods that help us to follow the architecture patters. Please take a look at this class to understand it better.

## Simple example:

On my ViewModel class, I have a LiveData of my generic ComponentState.
```kotlin
class ImageViewModel() : BaseViewModel(){

    private val _imageListComponent = MutableLiveData<ComponentState<List<Image>>>(ComponentState.Initializing)
    val imageListComponent : LiveData<ComponentState<List<Image>>> = _imageListComponent

    private val _imageDetailsComponent = MutableLiveData<ComponentState<ImageDetails>>()
    val imageDetailsComponent : LiveData<ComponentState<ImageDetails>> = _imageDetailsComponent
}
```

I can change the state of my component any time inside a coroutine flow on my ViewModel, for example.
```kotlin
class ImageViewModel() : BaseViewModel(){
    
    ...
    
    private suspend fun loadImagesInteraction() {
        // Set the list component with 'loading' state.
        _imageListComponent.value = ComponentState.Loading.FromEmpty

        // Load image's details.
        when(val listResult = loadImageListCase.execute()){
            // Set the list component with 'Success' state.
            is InteractionResult.Success -> {
                _imageListComponent.value = ComponentState.Success(listResult.result)
            }

            // Set the list component with 'Error' state.
            is InteractionResult.Error -> {
                _imageListComponent.value = ComponentState.Error(listResult.error)
            }
        }
    }
    
    ...
    
}
```

I can also send commands to my view and make it navigate to another screen, for example
```kotlin
class ImageViewModel() : BaseViewModel(){
    
    ...
    
    private suspend fun openImageDetailsInteraction(image: Image) {
        // Set the details component with 'loading' state.
        _imageDetailsComponent.value = ComponentState.Loading.FromEmpty

        // Open the details screen.
        sendCommand(ImageCommands.OpenImageDetails)

        // Load image's details.
        when(val detailsResult = loadImageDetailsCase.execute(image)){
            // Set the details component with 'Success' state.
            is InteractionResult.Success -> {
                _imageDetailsComponent.value = ComponentState.Success(detailsResult.result)
            }

            // Set the details component with 'Error' state.
            is InteractionResult.Error -> {
                _imageDetailsComponent.value = ComponentState.Error(detailsResult.error)
            }
        }
    }
    
    ...
    
}
```

On my screen, I just need to observe this live data to handle my component state

```kotlin
class ImageListFragment : Fragment() {
    private val viewModel   : ImageViewModel by sharedViewModel()
    private var binding     : FragmentListOfImagesBinding? = null

    ...

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindCommand(viewModel) { command ->
            handleCommand(command)
        }

        bindState(viewModel.imageListComponent) {
            renderImageList(it)
        }
    }

    private fun handleCommand(command: ViewCommand) {
        when (command) {
            is ImageCommands.OpenImageDetails -> {
                openDetailScreen()
            }
        }
    }

    private fun renderImageList(listState: ComponentState<List<Image>>) {
        when (listState) {
            is ComponentState.Initializing -> {
                // The component has not been initialized yet.
                // It is probably the first time opening this screen.
                // So we are gonna fetch all images.
                // But, if you rotate the screen. The ViewModel will keep the same.
                // So the state of this component will also keep the same and will not init again.
                viewModel.interact(ImageInteraction.LoadImages)
            }
            is ComponentState.Loading -> {
                // If you are not using the DataBinding feature
                // you could handle your views here when your list is loading.
                // Check 'ViewStateBindingAdapter#showOnLoading' method.
            }
            is ComponentState.Error -> {
                // If you are not using the DataBinding feature
                // you could handle your views here when your list is loading.
                // Check 'ViewStateBindingAdapter#showOnError' method.
            }
            is ComponentState.Success -> {
                // I am using the DataBinding feature, so I do not need to 'show' the list visibility
                // I just need to setup my recycler view here.
                setUpList(listState.result)
            }
        }
    }

    ...

}
```

Here are my 'ViewCommand' and 'UserInteraction' classes used on this example:

```kotlin
sealed class ImageCommands : ViewCommand{
    object OpenImageDetails : ImageCommands()
}

sealed class ImageInteraction : UserInteraction{
    object LoadImages : ImageInteraction()
    data class OpenDetails(val image: Image) : ImageInteraction()
}
```
