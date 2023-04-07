# Core Library

In this module we are going to see the core MVVM architecture. There are only [a few classes](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core/src/main/java/com/mctech/architecture/mvvm/core) here, but they are very important

### [ComponentState](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/blob/master/library/core/src/main/java/com/mctech/architecture/mvvm/x/core/ComponentState.kt)

This is the coolest one to me. We know how difficult is to handle many different components on our screen, don't we? Thinking about that, I have been using this simple sealed class that defines all states my component can be. 
So now, I do not need to care about the lifecycle of my component ever since I use this class attached to your view lifecycle. 

### [UserInteraction](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/blob/master/library/core/src/main/java/com/mctech/architecture/mvvm/x/core/UserInteraction.kt)

This represents every single event the user performs on your screen/component. Those events will be stored in a Stack on your BaseViewModel to help you track the user flow and make it possible to test your code.

### [ViewCommand](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/blob/master/library/core/src/main/java/com/mctech/architecture/mvvm/x/core/ViewCommand.kt)

It is basically the same as UserInteraction but it is called inside your ViewModel to send same event that will be consumed only once by your view. 

### [BaseViewModel](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/blob/master/library/core/src/main/java/com/mctech/architecture/mvvm/x/core/BaseViewModel.kt)

Last but not least important, the BaseViewModel. I do not like base classes overall, to be honest. But I use this BaseViewModel class a lot. Because it has only a few methods that help us to follow the architecture patters. Please take a look at this class to understand it better.

## Simple example:

On my ViewModel class, I have a LiveData of my generic ComponentState.
```kotlin
class ImageViewModel() : BaseViewModel(){

  private val _state by lazy {
    MutableStateFlow<ComponentState<List<Image>>>(ComponentState.Loading.FromEmpty)
  }
  val state: StateFlow<ComponentState<List<Image>>> by lazy { _state }

  private val _detailState by lazy {
    MutableStateFlow<ComponentState<ImageDetails>>(ComponentState.Loading.FromEmpty)
  }
  val detailState: StateFlow<ComponentState<ImageDetails>> by lazy { _detailState }
  
}
```

I can change the state of my component any time inside a coroutine flow on my ViewModel, for example.
```kotlin
class ImageViewModel() : BaseViewModel(){
    
  // ... rest of your code

  private suspend fun loadImagesInteraction() {
    when (val listResult = loadImageListCase.execute()) {
      // Set the list component with 'Success' state.
      is InteractionResult.Success -> {
        _state.value = ComponentState.Success(listResult.result)
      }

      // Set the list component with 'Error' state.
      is InteractionResult.Error -> {
        _state.value = ComponentState.Error(listResult.error)
      }
    }
  }

  // ... rest of your code
    
}
```

I can also send commands to my view and make it navigate to another screen, for example
```kotlin
class ImageViewModel() : BaseViewModel(){

  // ... rest of your code

  @OnInteraction(ImageInteraction.OpenDetails::class)
  private suspend fun openImageDetailsInteraction(interaction: ImageInteraction.OpenDetails) {
    // Set the details component with 'loading' state.
    _detailState.value = ComponentState.Loading.FromEmpty

    // Open the details screen.
    sendCommand(ImageCommands.OpenImageDetails)

    // Load image's details.
    when (val detailsResult = loadImageDetailsCase.execute(interaction.image)) {
      // Set the details component with 'Success' state.
      is InteractionResult.Success -> {
        _detailState.value = ComponentState.Success(detailsResult.result)
      }

      // Set the details component with 'Error' state.
      is InteractionResult.Error -> {
        _detailState.value = ComponentState.Error(detailsResult.error)
      }
    }
  }

  // ... rest of your code
    
}
```

On my screen, I just need to observe this state to handle my component state

```kotlin
@AndroidEntryPoint
class ImageListFragment : Fragment(R.layout.fragment_list_of_images) {

  private val viewModel by viewModels<ImageViewModel>()
  private val binding by viewBinding(FragmentListOfImagesBinding::bind)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    // Start view model flow
    viewModel.initialize()

    // Observes image component state.
    bindState(viewModel.state, ::consumeComponentState)
  }

  private fun consumeComponentState(state: ComponentState<List<Image>>) = when (state) {
    is ComponentState.Error -> renderErrorState()
    is ComponentState.Loading -> renderLoadingState()
    is ComponentState.Success -> renderSuccessState(state.result)
  }

  private fun renderLoadingState() {
    binding.recyclerList.isVisible = false
    binding.progressState.isVisible = true
    binding.errorComponent.isVisible = false
  }

  private fun renderErrorState() {
    binding.recyclerList.isVisible = false
    binding.progressState.isVisible = false
    binding.errorComponent.isVisible = true
  }

  private fun renderSuccessState(images: List<Image>) {
    binding.recyclerList.isVisible = true
    binding.progressState.isVisible = false
    binding.errorComponent.isVisible = false
    binding.recyclerList.prepareRecyclerView(
      items = images,
      bindView = this::renderImageItem,
      viewHolderFactory = this::createViewHolder,
      diffCallbackFactory = this::createImageDiffAlgorithm,
    )
  }

  private fun renderImageItem(item: Image, binding: ItemImageBinding) {
    binding.tvTitle.text = item.title
    binding.tvDate.text = item.date

    binding.root.setOnClickListener {
      viewModel.interact(ImageInteraction.OpenDetails(item))
    }
  }

  // ... rest of your code
}
```

Every interaction that the user is sending to the ViewModel is handled here
```kotlin
class ImageViewModel() : BaseViewModel(){

  // ... rest of your code

  override suspend fun handleUserInteraction(interaction: UserInteraction) {
    when(interaction){
        is ImageInteraction.OpenDetails -> openImageDetailsInteraction(interaction.image)
    }
  }

  // ... rest of your code

}
```

Here are my 'ViewCommand' and 'UserInteraction' classes used on this example:

```kotlin
sealed class ImageCommands : ViewCommand {
  object OpenImageDetails : ImageCommands()
}

sealed class ImageInteraction : UserInteraction {
  data class OpenDetails(val image: Image) : ImageInteraction()
}
```
