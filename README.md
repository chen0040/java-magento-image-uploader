# java-magento-image-uploader

Java utility tool which allows user to upload images for each product into a magento site using token-based access

# Install

```xml
<dependency>
  <groupId>com.github.chen0040</groupId>
  <artifactId>java-magento-image-uploader</artifactId>
  <version>1.0.2</version>
</dependency>
```

# Usage

The sample code below shows how to upload a set of images for each product at the Magento site.

```java
String url = "http://magento.ll";
String username = "admin";
String password = "chen0040@change.me";
ImageUploader uploader = new ImageUploader(url);
String token = uploader.loginAsAdmin(username, password);

if(token != null && !token.equals("")) {
 int pageIndex = 0;
 int pageSize = 10;
 ProductPage page = uploader.page(pageIndex, pageSize);
 boolean overwrite = true;
 uploader.uploadJpeg(page, product -> {
    long productId = product.getId();
    long imageId = (productId % 6 + 1);
    String imageName = "/tmp/images/" + imageId + ".jpg";
    return Arrays.asList(imageName);
 }, overwrite);
}
```

The code first login to Magento site using the admin account (to login as a client, call uploader.loginAsClient(...) instead). 

Next, it then obtain the first 10 products from the Magento site, for each product
its product id is used to retrieve the image file stored locally in the /tmp/images folder. The /tmp/images folder has the following images

* /tmp/images/1.jpg
* /tmp/images/2.jpg
* /tmp/images/3.jpg
* /tmp/images/4.jpg
* /tmp/images/5.jpg
* /tmp/images/6.jpg

The mapping between each product and each page is done by (product) -> (product.productId % 6 + 1).jpg. Note that it is feasible to
upload multiple images for each product (as evidenced by the line 'Arrays.asList...' in the above code).

The 'overwrite' flag if set to true will delete all images originally associated with the product before upload the new images for the product. 
If set to false, it will not upload any images if there already been images associated with the product.
 

To upload png images, use uploadPng(...) instead of uploadJpeg(...)



