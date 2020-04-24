# Android Views Extensions Library

A lot of [extensions](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/view-extentions/src/main/java/com/mctech/library/view/ktx) to help you to avoid writing boilerplate. 

### [Recycler View](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/blob/master/library/view-extentions/src/main/java/com/mctech/library/view/ktx/RecyclerViewBinding.kt)

This is the most difficult to understand. So I will put same example here. Here we have two important methods to create and update a recycler view without creating adapters and view holders.

```kotlin
private fun createImageList(recyclerView: RecyclerView, images: List<Image>) =
    recyclerView.createBindingRecyclerView(
        items = images,
        viewBindingCreator = { parent, inflater ->
            ItemImageBinding.inflate(inflater, parent, false)
        },
        prepareHolder = { item, viewBinding, viewHolder ->
            viewBinding.item = item
            viewBinding.root.setOnClickListener {
                viewModel.interact(ImageInteraction.OpenDetails(item))
            }
        }
    )
    
 private fun updateImageList(recyclerView: RecyclerView, images: List<Image>) {
    recyclerView.refreshBindingItems(
        newItems = images,
        callback = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(left: Image, right: Image) = left.id == right.id

            override fun areContentsTheSame(left: Image, right: Image): Boolean {
                return left.title == right.title && left.date == right.date
            }
        })
}

```
