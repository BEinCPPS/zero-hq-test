package it.eng.zerohqt;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by ascatox on 06/03/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ZeroHqRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private String contextJsonObj = "{\n" +
            "  \"subscriptionId\" : \"58b80214078a7e4a433ee24d\",\n" +
            "  \"originator\" : \"mock\",\n" +
            "  \"contextResponses\" : [\n" +
            "    {\n" +
            "      \"contextElement\" : {\n" +
            "        \"type\" : \"teststation\",\n" +
            "        \"isPattern\" : \"false\",\n" +
            "        \"id\" : \"teststation:TestStation1_1\",\n" +
            "        \"attributes\" : [\n" +
            "          {\n" +
            "            \"name\" : \"acknowledge\",\n" +
            "            \"type\" : \"string\",\n" +
            "            \"value\" : \"null\",\n" +
            "            \"metadatas\" : [\n" +
            "              {\n" +
            "                \"name\" : \"description\",\n" +
            "                \"type\" : \"string\",\n" +
            "                \"value\" : \"null\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"name\" : \"serverTimestamp\",\n" +
            "                \"type\" : \"typestamp\",\n" +
            "                \"value\" : \"2017-03-06T09:02:10.180Z\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"name\" : \"sourceTimestamp\",\n" +
            "                \"type\" : \"typestamp\",\n" +
            "                \"value\" : \"null\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\" : \"ipAddress\",\n" +
            "            \"type\" : \"string\",\n" +
            "            \"value\" : \"100.100.100.100\",\n" +
            "            \"metadatas\" : [\n" +
            "              {\n" +
            "                \"name\" : \"description\",\n" +
            "                \"type\" : \"string\",\n" +
            "                \"value\" : \"null\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"name\" : \"serverTimestamp\",\n" +
            "                \"type\" : \"typestamp\",\n" +
            "                \"value\" : \"2017-03-06T09:02:11.249Z\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"name\" : \"sourceTimestamp\",\n" +
            "                \"type\" : \"typestamp\",\n" +
            "                \"value\" : \"null\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\" : \"state\",\n" +
            "            \"type\" : \"string\",\n" +
            "            \"value\" : \"400\",\n" +
            "            \"metadatas\" : [\n" +
            "              {\n" +
            "                \"name\" : \"description\",\n" +
            "                \"type\" : \"string\",\n" +
            "                \"value\" : \"Rule Execution\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"name\" : \"serverTimestamp\",\n" +
            "                \"type\" : \"typestamp\",\n" +
            "                \"value\" : \"2017-03-06T09:02:11.153Z\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"name\" : \"sourceTimestamp\",\n" +
            "                \"type\" : \"typestamp\",\n" +
            "                \"value\" : \"2017-03-06T09:02:05.118Z\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\" : \"statePayload\",\n" +
            "            \"type\" : \"string\",\n" +
            "            \"value\" : \"-1|-1\",\n" +
            "            \"metadatas\" : [\n" +
            "              {\n" +
            "                \"name\" : \"description\",\n" +
            "                \"type\" : \"string\",\n" +
            "                \"value\" : \"null\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"name\" : \"serverTimestamp\",\n" +
            "                \"type\" : \"typestamp\",\n" +
            "                \"value\" : \"2017-03-06T09:02:11.246Z\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"name\" : \"sourceTimestamp\",\n" +
            "                \"type\" : \"typestamp\",\n" +
            "                \"value\" : \"null\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\" : \"stationInfo\",\n" +
            "            \"type\" : \"string\",\n" +
            "            \"value\" : \"Macchina 1 Box 1\",\n" +
            "            \"metadatas\" : [\n" +
            "              {\n" +
            "                \"name\" : \"description\",\n" +
            "                \"type\" : \"string\",\n" +
            "                \"value\" : \"null\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"name\" : \"serverTimestamp\",\n" +
            "                \"type\" : \"typestamp\",\n" +
            "                \"value\" : \"2017-03-06T09:02:11.155Z\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"name\" : \"sourceTimestamp\",\n" +
            "                \"type\" : \"typestamp\",\n" +
            "                \"value\" : \"2017-03-06T09:01:59.121Z\"\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"statusCode\" : {\n" +
            "        \"code\" : \"200\",\n" +
            "        \"reasonPhrase\" : \"OK\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void simulateBELoop() throws Exception {
        for (; ; ) {
            Thread.sleep(5000);
            this.mockMvc.perform(post("/notify")
                    .contentType(contentType)
                    .content(contextJsonObj))
                    .andExpect(status().isOk());
        }
    }

    //@Test
    public void simulateBE() throws Exception {

        this.mockMvc.perform(post("/notify")
                .contentType(contentType)
                .content(contextJsonObj))
                .andExpect(status().isOk());

    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
