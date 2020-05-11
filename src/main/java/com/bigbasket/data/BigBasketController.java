package com.bigbasket.data;

import com.bigbasket.data.APIDataExtraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping(path="/customdata")
public class BigBasketController {
    @Autowired
    private APIDataExtraction obj;

    @Transactional
    @RequestMapping
    public String saveBigBasketData( ) throws Exception {
        obj.startFetchingData();
        return "done";
    }
}