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
import java.util.Iterator;
@Service
public class APIDataExtraction{
    private String theUrl;
@Autowired
 private BigBasketRepository basketRepository;
        public void startFetchingData() throws IOException {
            // write your code here

            String output  = getUrlContents("https://www.bigbasket.com/custompage/sysgenpd/?type=pc&slug=fruits-vegetables&sid=cknWHY2ibWQBoWMBqHNrdV9saXN0kKJuZsOiY2OjNDg5qWJhdGNoX2lkeACiYW_ConVywqJhcMOibHTNARuhb6pwb3B1bGFyaXR5pXNyX2lkAaNtcmnNDi4=");
            System.out.println(output);

            ObjectMapper objectmapper=new ObjectMapper();
            JsonNode rootnode=objectmapper.readTree(output);
            JsonNode childnode=rootnode.path("tab_info");
            Iterator<JsonNode> it=childnode.elements();
            while(it.hasNext())
            {
                JsonNode m=it.next();
                JsonNode product_info= m.path("product_info");
                JsonNode product_node=product_info.path("products");
                Iterator<JsonNode> products=product_node.elements();
                while(products.hasNext())
                {
                    JsonNode each_product=products.next();
                    BigBasketEntity new_entity=new BigBasketEntity();
                    new_entity.setSp(Double.parseDouble(each_product.path("sp").asText()));
                    new_entity.setW(String.valueOf(each_product.path("w")));
                    new_entity.setP_desc(String.valueOf(each_product.path("p_desc")));
                    new_entity.setSku(Long.parseLong(String.valueOf(each_product.path("sku"))));
                    basketRepository.save(new_entity);
                    System.out.print(each_product.path("p_desc").asText()+" "+each_product.path("sp")+" "+each_product.path("w")+" "+each_product.path("sku"));
                    System.out.println();
                }
            }

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
