package com.github.chen0040.magento;


import com.github.chen0040.magento.models.Product;
import com.github.chen0040.magento.models.ProductMedia;
import com.github.chen0040.magento.models.ProductPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Created by xschen on 20/6/2017.
 */
public class ImageUploader {
   private static final Logger logger = LoggerFactory.getLogger(ImageUploader.class);

   private MagentoClient client;
   public ImageUploader(MagentoClient client) {
      this.client = client;
   }

   public String loginAsAdmin(String username, String password){
      return this.client.loginAsAdmin(username, password);
   }

   public String loginAsClient(String username, String password) {
      return this.client.loginAsClient(username, password);
   }

   public ProductPage page(int pageIndex, int pageSize) {
      return this.client.products().page(pageIndex, pageSize);
   }

   public void uploadPng(ProductPage page, Function<Product, List<String>> imageNameFunc, boolean forceOverwrite) throws IOException {
      List<Product> products = page.getItems();

      for(int i=0; i < products.size(); ++i){
         Product product = products.get(i);

         String sku = product.getSku();
         List<String> images =imageNameFunc.apply(product);

         List<ProductMedia> mediaList = client.media().getProductMediaList(sku);

         if(forceOverwrite) {
            for(int k = 0; k < mediaList.size(); ++k) {
               client.media().deleteProductMedia(sku, mediaList.get(k).getId());
            }
         } else {
            if(mediaList.size() > 0) {
               continue;
            }
         }

         long productId = product.getId();
         for(int j = 0; j < images.size(); ++j) {
            String filename = "/m/b/mb" + productId + "-blue-" + j + ".png";
            int position = 1;
            String type = "image/png";

            String imageFileName = images.get(j); //"new_image.png";

            InputStream inputStream = new FileInputStream(imageFileName);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int length;
            byte[] bytes = new byte[1024];
            while((length = inputStream.read(bytes, 0, 1024)) > 0) {
               baos.write(bytes, 0, length);
            }
            bytes = baos.toByteArray();

            long uploadedId = client.media().uploadProductImage(sku, position, filename, bytes, type, imageFileName);

            logger.info("uploaded {} for product {}: {}", imageFileName, sku, uploadedId);
         }
      }
   }

}
