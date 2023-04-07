# Simple RecyclerView Library

We all know how boring it is to create a simple list of items on the screen. We usually need to setup a lot of boilerplate code to: 
* Create the Adapter 
* Create the Adapter View Holder
* Bind the View Holder with our view
* Etc. 

Those few steps usually requires us to create a few classes with many lines of code.

That's where the Simple RecyclerView Library comes for. 
It tries to remove all of that required boilerplate and allows you to focus on the only important piece, which is Bind your Data and your View.

Below here you can see a simple example of its usage and then a more complex example where you need to handle animations or other customizations. 
The usage is self explanatory, but feel free to check all available functions.

### Files created to make it work

Activity/Fragment XML with your recycler view.
```xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/country_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:itemCount="5"
        tools:listitem="@layout/item_list_row" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

Item XML defining the list item layout.
```xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">

    <TextView
        android:id="@+id/country"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:padding="40dp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:text="Ireland" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

Then the code to create your recycler view and render your data on it.
```kotlin

/**
 * The default implementation is always a vertical list.
 */
private fun setupSimpleRecyclerView(items: List<String>) {
  binding.countryList.prepareRecyclerView(
    items = items,
    bindView = { country, binding ->
      binding.country.text = country
    },
    viewHolderFactory = { parent, inflater ->
      ItemListRowBinding.inflate(inflater, parent, false)
    },
  )
}

/**
 * Customizing recycler view, just add the function setupRecyclerView and make any change you need
 * You can change the LinearLayoutManager to make it horizontal or have a grid instead.
 *
 * Basically setupRecyclerView is used to setup any customization you need on your list.
 */
private fun setupComplexRecyclerView(items: List<String>) {
  binding.countryList.prepareRecyclerView(
    items = items,
    setupRecyclerView = { recyclerView ->
      recyclerView.setHasFixedSize(true)
      recyclerView.itemAnimator = DefaultItemAnimator()
      recyclerView.layoutManager = LinearLayoutManager(context).apply {
        orientation = RecyclerView.HORIZONTAL
      }
    },
    bindView = { country, binding ->
      binding.country.text = country
    },
    viewHolderFactory = { parent, inflater ->
      ItemListRowBinding.inflate(inflater, parent, false)
    },
    diffCallbackFactory = {
      object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
          return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
          return oldItem == newItem
        }

      }
    }
  )
}
```