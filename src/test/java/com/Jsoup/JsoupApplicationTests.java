package com.Jsoup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Jsoup.main.DealerMain;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsoupApplicationTests {

    @Autowired
    private DealerMain dealerMain;
	@Test
	public void contextLoads() {
	        int i = 1;
	        while (i<1675) {
	
	            try {
	                dealerMain.save("china", i);
	                Thread.sleep(1000L);
	            } catch (Exception e) {
	            }
	            i++;
            }
	}

}
