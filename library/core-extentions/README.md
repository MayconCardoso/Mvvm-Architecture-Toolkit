# Core Extension Library

This is a simple Extension library to help you 'Observe' your ComponentState on your Activities and Fragments without that boilerplate.

## Binding View 
Just introduces an easier way to bind your view with ViewBinding for Fragments and Activities.

### Fragment 
```kotlin
class ImageListFragment : Fragment(R.layout.fragment_list_of_images) {
  private val binding by viewBinding(FragmentListOfImagesBinding::bind)
}
```

### Activity
```kotlin
class MainActivity : AppCompatActivity() {
  private val binding by viewBinding(ActivityMainBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }
}
```

## Binding View Model States with your Fragment or Activity

### Without this library
```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  lifecycleScope.launch {
    viewModel.imageDetailsComponent.observe(this, Observer {
      // Your code
    })
  }
}
```

### Using this library
```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  // Observes image component state.
  bindState(viewModel.state, ::consumeComponentState)
}

```
