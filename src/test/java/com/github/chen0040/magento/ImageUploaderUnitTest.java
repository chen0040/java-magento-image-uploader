package com.github.chen0040.magento;


import com.github.chen0040.magento.models.ProductPage;
import com.github.chen0040.magento.utils.StringUtils;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;


/**
 * Created by xschen on 20/6/2017.
 */
public class ImageUploaderUnitTest {

   @Test
   public void test_image_upload(){
      ImageUploader uploader = new ImageUploader(Mediator.url);
      String token = uploader.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);

      if(token != null && !token.equals("")) {
         ProductPage page = uploader.page(0, 10);
         uploader.uploadJpeg(page, product -> {
            long productId = product.getId();
            long imageId = (productId % 6 + 1);
            String imageName = "/tmp/images/" + imageId + ".jpg";
            return Arrays.asList(imageName);
         }, false);
      }
   }

   @Test
   public void test_image_upload_overwrite(){
      ImageUploader uploader = new ImageUploader(Mediator.url);
      String token = uploader.loginAsAdmin(Mediator.adminUsername, Mediator.adminPassword);

      if(token != null && !token.equals("")) {
         ProductPage page = uploader.page(0, 10);
         uploader.uploadJpeg(page, product -> {
            long productId = product.getId();
            long imageId = (productId % 6 + 1);
            String imageName = "/tmp/images" + imageId + ".jpg";
            return Arrays.asList(imageName);
         }, true);
      }
   }
}
