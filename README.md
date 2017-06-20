# java-magento-image-uploader

Java utility tool which allows user to upload images for each product into a magento site using token-based access

# Install

```xml

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
 uploader.uploadJpeg(page, product -> {
    long productId = product.getId();
    long imageId = (productId % 6 + 1);
    String imageName = "/tmp/images/" + imageId + ".jpg";
    return Arrays.asList(imageName);
 }, false);
}
```

The code first login to magento using the admin account, it then obtain the first 10 products from the Magento site, for each product
its product id is used to retrieve the image file stored locally in the /tmp/images folder. The /tmp/images folder has the following images

* /tmp/images/1.jpg
* /tmp/images/2.jpg
* /tmp/images/3.jpg
* /tmp/images/4.jpg
* /tmp/images/5.jpg
* /tmp/images/6.jpg

The mapping between each product and each page is done by (product) -> (product.productId % 6 + 1).jpg

To upload png images, use uploadPng(...) instead of uploadJpeg(...)


