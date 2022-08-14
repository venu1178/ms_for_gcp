package com.order.controller;

import com.order.model.Order;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.google.cloud.storage.*;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
public class OrderController {

    private static HashMap<String,Order> orderMap = new HashMap<>();
    private Order order;
    private String fileName;

    @GetMapping("/order/{bucketName}/{fileName}")
    public String getOrder(@PathVariable("bucketName") String bucketName,
                           @PathVariable("fileName") String fileName)
                            {
       // String bucketName = "order_placement_bucket";
       // String filenName = "my_blob_name";
        this.fileName =fileName;
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Blob blob = storage.get(bucketName, fileName);
        String fileContent = new String(blob.getContent());
        String[] orderParams = fileContent.split("\\|");
        System.out.println("--------------------------------------------"+orderParams.length);
        System.out.println("customerId "+orderParams[0]);
        System.out.println("orderName  "+orderParams[1]);
        System.out.println("orderID  "+orderParams[2]);
        System.out.println("orderQty  "+orderParams[3]);
        System.out.println("orderPrice  "+orderParams[4]);
        System.out.println("reserveCash "+orderParams[5]);
        System.out.println("--------------------------------------------");
        Order order = new Order(orderParams, Order.Status.pending);
        orderMap.put(fileName,order);
        return order.getCustomerId()+"/"+order.getReserveCash()+"/"+order.getOrderPrice();
    }

    @GetMapping("/health")
    public String health(){
        return "Order Service is Healthy";
    }


    @GetMapping("/approve/{bucketName}/{fileName}")
    public String approve(@PathVariable("bucketName") String bucketName,
                          @PathVariable("fileName") String fileName ){

            Storage storage = StorageOptions.getDefaultInstance().getService();
            String deliveryBucketName = "order_delivery";
            BlobId blobId = BlobId.of(deliveryBucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
            String fileData = "Order Status="+"Approved"+
                    "|order is ="+fileName;
            Blob blob = storage.create(blobInfo, fileData.getBytes(UTF_8));

        return "approved";
    }
    /**
     */
    @GetMapping("/reject/{bucketName}/{fileName}")
    public String reject(@PathVariable("bucketName") String bucketName,
                         @PathVariable("fileName") String fileName){
        return "rejected";
    }
    @GetMapping("/pending/{bucketName}/{fileName}")
    public String pending(@PathVariable("bucketName") String bucketName,
                          @PathVariable("fileName") String fileName){
        return "pending";
    }

   /*public static void main(String str[]){
        String fileContent = "customerId=12|orderId=12|orderName=Phone|orderQty=4|orderPrice=2000.00|customerId=12|orderId=12|orderName=Phone|orderQty=4|orderPrice=2000.00";

        System.out.println("file content "+fileContent);
        String[] orderParams = fileContent.split("\\|");
        System.out.println("String array  after : "+orderParams.length);
        System.out.println("customerId "+orderParams[0]);
        System.out.println(" -------"+(String)(orderParams[0].split("="))[1]);
        System.out.println("orderName  "+orderParams[1]);
        System.out.println("orderID  "+orderParams[2]);
        System.out.println("orderQty  "+orderParams[3]);
        System.out.println("orderPrice  "+orderParams[4]);
       Order order = new Order(orderParams, Order.Status.pending);

       System.out.println("custer ID=----form order "+order.getCustomerId());
       System.out.println("order  ID=----form order "+order.getOrderId());
       System.out.println("order  Name=----form order "+order.getOrderName());
       System.out.println("order  Qty=----form order "+order.getOrderQty());
       System.out.println("order  price=----form order "+order.getOrderPrice());
       System.out.println("order  status=----form order "+order.getStatus().name());
       System.out.println("---------------------------------------------------");

       order.setStatus(Order.Status.rejected);
       System.out.println("order  status=----form order after "+order.getStatus().name());

    }*/

}
