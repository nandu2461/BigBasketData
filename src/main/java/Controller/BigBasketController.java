package Controller;

import com.bigbasket.data.APIDataExtraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(path="/customdata")
public class BigBasketController {
    @Autowired
    private APIDataExtraction obj;

    @RequestMapping
    public String saveBigBasketData( ) throws Exception {
        obj.startFetchingData();
        return "done";
    }
}