# Core Testing Library

It makes your ComponentState and live data testing extremely easier. There are a lot of [extensions](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core-testing/src/main/java/com/mctech/architecture/mvvm/core/testing/extentions) to help you.


```kotlin
internal class ImageViewModelTest : BaseViewModelTest(){
    private lateinit var viewModel: ImageViewModel

    private val loadImageDetailsCase    = mock<LoadImageDetailsCase>()
    private val loadImageListCase       = mock<LoadImageListCase>()
    private val expectedList            = mutableListOf<Image>()

    @Before
    fun `before each test`() {
        viewModel = ImageViewModel(
            loadImageListCase,
            loadImageDetailsCase
        )
    }

    @After
    fun `after each test`() {
        viewModel.viewModelScope.cancel()
    }

    @Test
    fun `should initialize components`() = testLiveDataScenario{

        assertLiveDataFlow(viewModel.imageListComponent){
            it.assertFlow(ComponentState.Initializing)
        }

        assertLiveDataFlow(viewModel.imageDetailsComponent){
            it.assertEmpty()
        }

    }

    @Test
    fun `should show data on list component`() = testLiveDataScenario{
        whenThisScenario {
            whenever(loadImageListCase.execute()).thenReturn(
                InteractionResult.Success(expectedList)
            )
        }

        onThisAction {
            viewModel.interact(ImageInteraction.LoadImages)
        }

        assertLiveDataFlow(viewModel.imageListComponent){
            it.assertFlow(
                ComponentState.Initializing,
                ComponentState.Loading.FromEmpty,
                ComponentState.Success(expectedList)
            )
            verify(loadImageListCase, times(1)).execute()
        }
    }

    @Test
    fun `should show error on list component`() = testLiveDataScenario{
        whenThisScenario {
            whenever(loadImageListCase.execute()).thenReturn(
                InteractionResult.Error(ImageException.CannotFetchImages)
            )
        }

        onThisAction {
            viewModel.interact(ImageInteraction.LoadImages)
        }

        assertLiveDataFlow(viewModel.imageListComponent){
            it.assertFlow(
                ComponentState.Initializing,
                ComponentState.Loading.FromEmpty,
                ComponentState.Error(ImageException.CannotFetchImages)
            )
            verify(loadImageListCase, times(1)).execute()
        }
    }
}
```
