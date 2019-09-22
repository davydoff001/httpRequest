
import com.example.httprequest.CcConfigKey;
import com.example.httprequest.CcConfigKeyList;
import com.example.httprequest.CcConfigKeyMap;
import com.example.httprequest.CcConfigKeyString;
import com.example.httprequest.CcDataType;
import com.example.httprequest.CcFrontOfficeApi;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 * @author Alexandr
 */

public class CcFrontOfficeApiTest {
    
    private final String apiKey = ".fUHNtshXuuPfPoFSlm4gyZBhIVOiNZL";
    private final String gatewayRegion = "us-west-1";
    private final String gatewayApiId = "ew2yseij13";
    private final String envName = "dev";
       
    @Test
    public void testJsonReader() throws Exception{
        Class aClass = CcFrontOfficeApiTest.class; 
        InputStream resourceAsStream = aClass.getResourceAsStream("test.json");
        
        CcConfigKeyMap actualMap = new CcConfigKeyMap();
        CcFrontOfficeApi api = new CcFrontOfficeApi(gatewayRegion, gatewayApiId, envName, apiKey);
        api.parseJson(resourceAsStream, actualMap);
        
        Assert.assertEquals(4, actualMap.size());
    }
    
    @Test
    public void getApiKeyWithEmptyConfigPath() throws IOException, MalformedURLException, ParseException, Exception {
        CcConfigKeyMap actualMap = new CcConfigKeyMap();
        CcFrontOfficeApi api = new CcFrontOfficeApi(gatewayRegion, gatewayApiId, envName, apiKey);
        api.getConfigKeysJackson("", actualMap);
        Assert.assertEquals(4, actualMap.size());

//        CcConfigKey server = actualMap.get("sigma/server");
//        Assert.assertEquals(CcDataType.String, server.getDataType());
//        CcConfigKeyString strKey = (CcConfigKeyString) server;
//        boolean equals = server.equals(strKey);
//        Assert.assertEquals("192.168.0.1",strKey.getValue());
        CcConfigKeyMap expectedMap = new CcConfigKeyMap();
        expectedMap.consumeString("sigma/bgcolor", "white");
        expectedMap.consumeBool("sigma/active", Boolean.FALSE);
        expectedMap.consumeString("sigma/server", "192.168.0.1");
        expectedMap.consumeInt("sigma/omega/LsaPid", 1);
        
        Assert.assertEquals(actualMap, expectedMap);

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
        
        CcConfigKeyMap map = new CcConfigKeyMap();
        CcFrontOfficeApi api = new CcFrontOfficeApi(gatewayRegion, gatewayApiId, envName, apiKey);
        api.getConfigKeys("/sigma/",map);
        Assert.assertEquals(4, map.size());
        
        
              
    }

}
