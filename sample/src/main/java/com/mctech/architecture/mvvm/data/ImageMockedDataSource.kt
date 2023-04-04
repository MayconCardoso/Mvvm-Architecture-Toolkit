package com.mctech.architecture.mvvm.data

import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.domain.entities.ImageDetails
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class ImageMockedDataSource : ImageDataSource {

  override suspend fun getAllImages(): List<Image> {
    delay(3000)

    val mockedImages = mutableListOf<Image>()
    for (id in 1..100) {
      mockedImages.add(
        Image(
          id = id.toLong(),
          title = "Lorem ipsum: $id",
          date = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
          ).format(Calendar.getInstance().time),
          thumbnailUrlSource = "https://i.gzn.jp/img/2019/08/23/android-10/00.png"
        )
      )
    }
    return mockedImages
  }

  override suspend fun getImageDetails(image: Image): ImageDetails {
    delay(3000)

    return ImageDetails(
      image = image,
      description = mockedDescription,
      heightSize = 1024,
      widthSize = 520,
      bigImageUrlSource = "https://i.gzn.jp/img/2019/08/23/android-10/00.png"
    )
  }

  private val mockedDescription =
    "Lorem ipsum dolor sit amet consectetur adipiscing elit habitasse conubia, auctor quis quisque fusce enim montes aptent gravida, faucibus porta augue himenaeos cursus elementum eget suspendisse. Ad montes eleifend magnis nullam eget iaculis pharetra porttitor sit lectus primis pretium urna adipiscing, etiam tincidunt aptent ipsum luctus vestibulum elit nam ante arcu penatibus vulputate. Ligula litora neque orci tincidunt metus ad habitasse quis suspendisse ullamcorper malesuada facilisis ultrices, mattis amet habitant venenatis purus porttitor sollicitudin dictum ante scelerisque netus efficitur. Sodales iaculis at feugiat erat vitae facilisi nibh mi, nam orci lorem pharetra nisi a conubia. Nostra ex ullamcorper praesent rhoncus feugiat sodales suscipit lacinia, laoreet porttitor gravida venenatis lacus dignissim volutpat litora taciti, arcu viverra maecenas mattis ante parturient faucibus. Nisl semper egestas faucibus facilisi neque suscipit, felis convallis purus himenaeos venenatis, dui porttitor quam amet ipsum. Aptent taciti morbi phasellus volutpat ultrices convallis cubilia, mi tortor sodales risus iaculis porta aenean, ridiculus dictumst commodo ante et pharetra. Adipiscing dictum massa quam nullam elementum integer ad phasellus curae vehicula molestie volutpat nibh commodo, potenti eu inceptos feugiat himenaeos torquent fames condimentum justo dolor dictumst pharetra primis. Venenatis interdum finibus nulla vulputate arcu accumsan viverra class auctor placerat est, ante adipiscing felis tempus eget ipsum molestie ultricies cubilia elit pellentesque consequat, aliquet erat potenti massa phasellus mollis neque fringilla velit sodales.\n" +
        "\n" +
        "Etiam cursus litora vivamus diam pharetra pretium aptent accumsan, metus id faucibus ornare eleifend lacus potenti quisque, pellentesque vehicula class facilisis ultricies viverra proin. Curae mi volutpat sapien odio lectus, sodales eget himenaeos iaculis parturient hendrerit, cursus lacus cras sollicitudin. Torquent elit sollicitudin ipsum vivamus quis aptent egestas blandit, augue pellentesque sodales eu placerat phasellus dignissim, eleifend vehicula ligula mauris habitasse tempus varius. Nunc mollis integer sapien congue class quam augue laoreet dictumst vel, nascetur lacus lorem non ut phasellus commodo praesent felis, nam accumsan ornare convallis mauris neque ligula blandit suspendisse. Eu vel donec rutrum accumsan ullamcorper duis semper nec curae, orci lacus quam class senectus litora egestas phasellus, praesent pretium per dapibus diam erat ipsum eros. Commodo blandit hendrerit velit phasellus inceptos potenti cubilia, ipsum tellus elementum in purus maecenas, a iaculis integer nec erat habitasse. Pulvinar lobortis enim lacus ornare vivamus tortor quisque conubia faucibus justo, cursus ultricies id nisl phasellus ut mattis sodales tristique praesent pellentesque, urna mauris dolor ante placerat sapien suspendisse pretium iaculis. Fermentum per neque amet dictumst venenatis, magnis felis dis malesuada morbi leo, efficitur arcu ligula diam. Dictum justo lobortis ipsum dis blandit porttitor gravida phasellus, ridiculus semper in maecenas egestas parturient montes lectus, mauris penatibus integer class nam congue nascetur. Efficitur sapien platea eu ornare cras ultricies, rutrum nam vitae ligula conubia dignissim tortor, urna dui donec iaculis lobortis."
}