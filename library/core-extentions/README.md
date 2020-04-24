# Core Extension Library

This is a simple Extension library to help you 'Observe' your ComponentState on your Activities and Fragments without that boilerplate.

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
    bindState(viewModel.imageListComponent) { 
        // Your code
    }
}

```
