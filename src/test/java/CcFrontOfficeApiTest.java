
import com.example.httprequest.CcConfigKeyList;
import com.example.httprequest.CcFrontOfficeApi;
import java.io.IOException;
import java.net.MalformedURLException;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexandr
 */
public class CcFrontOfficeApiTest {
    
    private final String apiKey = ".fUHNtshXuuPfPoFSlm4gyZBhIVOiNZL";
    private final String gatewayRegion = "us-west-1";
    private final String gatewayApiId = "ew2yseij13";
    private final String envName = "dev";
    
    public void sendRequestWithPathAndApiKey(String configPath) throws IOException, MalformedURLException, ParseException{

//        JSONArray response = getConfigKeys(
//                "https://ew2yseij13.execute-api.us-west-1.amazonaws.com/dev/config" + configPath, apiKey);
//        Iterator i = response.iterator();
//
//        while (i.hasNext()) {
//            JSONObject innerObj = (JSONObject) i.next();
//            System.out.println("path " + innerObj.get("path")
//                    + " dataType " + innerObj.get("dataType")
//                    + " value " + innerObj.get("value"));
//        }
    }
    
    @Test
    public void getApiKeyWithEmptyConfigPath() throws IOException, MalformedURLException, ParseException {
        CcConfigKeyList list = new CcConfigKeyList();
        CcFrontOfficeApi api = new CcFrontOfficeApi(gatewayRegion, gatewayApiId, envName, apiKey);
        api.getConfigKeys("",list);
        Assert.assertEquals(4, list.size());
        
    }
    
    @Test
    public void getApiKeyWithFullConfigPath() throws IOException, MalformedURLException, ParseException {

        CcConfigKeyList list = new CcConfigKeyList();
        CcFrontOfficeApi api = new CcFrontOfficeApi(gatewayRegion, gatewayApiId, envName, apiKey);
        api.getConfigKeys("/sigma/bgcolor",list);
        Assert.assertEquals(1, list.size());
        
    }
    
    @Test
    public void getApiKeyByNamespace() throws IOException, MalformedURLException, ParseException {
        
        CcConfigKeyList list = new CcConfigKeyList();
        CcFrontOfficeApi api = new CcFrontOfficeApi(gatewayRegion, gatewayApiId, envName, apiKey);
        api.getConfigKeys("/sigma/",list);
        Assert.assertEquals(4, list.size());
        
    }

}
