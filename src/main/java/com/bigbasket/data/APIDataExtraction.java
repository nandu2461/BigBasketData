package com.bigbasket.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
@Service
public class APIDataExtraction{
    private String theUrl;
@Autowired
 private BigBasketRepository basketRepository;
        public void startFetchingData() throws IOException {
            // write your code here
            Integer totalPages = 11;
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(date);
            basketRepository.deleteAllByDateEquals(strDate);
            String output = getUrlContents("https://www.bigbasket.com/custompage/sysgenpd/?type=pc&slug=fruits-vegetables&sid=cknWHY2ibWQBoWMBqHNrdV9saXN0kKJuZsOiY2OjNDg5qWJhdGNoX2lkeACiYW_ConVywqJhcMOibHTNARuhb6pwb3B1bGFyaXR5pXNyX2lkAaNtcmnNDi4=");
            ObjectMapper objectmapper = new ObjectMapper();
            JsonNode rootnode = objectmapper.readTree(output);
            JsonNode childnode = rootnode.path("tab_info");
            Iterator<JsonNode> it = childnode.elements();
            while (it.hasNext()) {
                JsonNode m = it.next();
                JsonNode product_info = m.path("product_info");
                totalPages = product_info.path("tot_pages").asInt();
                JsonNode product_node = product_info.path("products");
                Iterator<JsonNode> products = product_node.elements();
                while (products.hasNext()) {
                    JsonNode each_product = products.next();
                    BigBasketEntity new_entity = new BigBasketEntity();
                    new_entity.setSp(Double.parseDouble(each_product.path("sp").asText()));
                    new_entity.setW(String.valueOf(each_product.path("w")));
                    new_entity.setP_desc(String.valueOf(each_product.path("p_desc")));
                    new_entity.setSku(Long.parseLong(String.valueOf(each_product.path("sku"))));
                    new_entity.setDate(strDate);
                    basketRepository.save(new_entity);
                    System.out.print(each_product.path("p_desc").asText() + " " + each_product.path("sp") + " " + each_product.path("w") + " " + each_product.path("sku"));
                    System.out.println();
                }
            }
            System.out.println("Converted String: " + strDate);
            for (int i = 0; i < totalPages; i++) {
                String pageNo = Integer.toString(i);
                String response = getUrlContents("https://www.bigbasket.com/product/get-products/?slug=fruits-vegetables&page=" + pageNo + "&tab_type=[%22all%22]&sorted_on=popularity&listtype=pc");
                System.out.println(response);
                ObjectMapper objectmapper2=new ObjectMapper();
                JsonNode rootnode2=objectmapper2.readTree(response);
                JsonNode childnode2=rootnode2.path("tab_info");
                JsonNode childnode3=childnode2.path("product_map");
                JsonNode childnode4=childnode3.path("all");
                JsonNode childnode5=childnode4.path("prods");
                Iterator<JsonNode> products2 =childnode5.elements();
                while (products2.hasNext())
                {
                    JsonNode each_product2 = products2.next();
                    BigBasketEntity new_entity2 = new BigBasketEntity();
                    new_entity2.setSp(Double.parseDouble(each_product2.path("sp").asText()));
                    new_entity2.setW(String.valueOf(each_product2.path("w")));
                    new_entity2.setP_desc(String.valueOf(each_product2.path("p_desc")));
                    new_entity2.setSku(Long.parseLong(String.valueOf(each_product2.path("sku"))));
                    new_entity2.setDate(strDate);
                    basketRepository.save(new_entity2);
                    System.out.print(each_product2.path("p_desc").asText() + " " + each_product2.path("sp") + " " + each_product2.path("w") + " " + each_product2.path("sku"));
                    System.out.println();
                }
            }
            System.out.println("Converted String: " + strDate);
        }

        private static String getUrlContents(String theUrl)
        {
            StringBuilder content = new StringBuilder();

            // many of these calls can throw exceptions, so i've just
            // wrapped them all in one try/catch statement.
            try
            {
                java.net.CookieManager cm = new java.net.CookieManager();
                java.net.CookieHandler.setDefault(cm);

                // create a url object
                URL url = new URL(theUrl);

                // create a urlconnection object
                URLConnection urlConnection = url.openConnection();

                // wrap the urlconnection in a bufferedreader
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;

                // read from the urlconnection via the bufferedreader
                while ((line = bufferedReader.readLine()) != null)
                {
                    content.append(line + "\n");
                }
                bufferedReader.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return content.toString();
        }
    }
