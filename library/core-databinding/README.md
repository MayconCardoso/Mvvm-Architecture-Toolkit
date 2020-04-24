# Core DataBinding Library

This is a simple DataBinding adapter library that create some adapters to work with our [ComponentState](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/blob/master/library/core/src/main/java/com/mctech/architecture/mvvm/core/ComponentState.kt). Those adapters just change the component (View) visibility. But believe me, it helps a lot! It helps you to avoid writing boilerplate on your view.

Check all adapters available [here](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/blob/master/library/core-databinding/src/main/java/com/mctech/architecture/mvvm/core/databinding/ViewStateBindingAdapter.kt)

## Simple example

You can use the adapters on your XML file if your are using DataBinding

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="viewModel"
            type="com.mctech.architecture.mvvm.presentation.ImageViewModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.recyclerview.widget.RecyclerView
            ...
            app:showOnSuccess="@{viewModel.imageListComponent}" />

        <ProgressBar
            ...
            app:showOnLoadingOrInitializing="@{viewModel.imageListComponent}" />
      
        <TextView
            ...
            app:showOnError="@{viewModel.imageListComponent}" />
    </androidx.constraintlayout.widget.ConstraintLayout>

</layout>
```

### Setup

Do not forget to enabled DataBinding support on the module you are using this library.

```groovy
apply plugin: 'kotlin-kapt'

android {
    ...

    dataBinding {
        enabled = true
        enabledForTests = true
    }
}
```
