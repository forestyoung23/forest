package test.com.forest.ser;

import com.forest.ser.Decoder;
import com.forest.ser.Encoder;
import com.forest.ser.JsonDecoder;
import com.forest.ser.JsonEncoder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * JsonDecoder Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>6月 30, 2022</pre>
 */
public class JsonDecoderTest {

    /**
     * Method: decode(byte[] bytes, Class<T> clazz)
     */
    @Test
    public void testDecode() throws Exception {
        Decoder decoder = new JsonDecoder();
        Encoder encoder = new JsonEncoder();
        TestBean bean = new TestBean();
        bean.setName("123");
        byte[] encode = encoder.encode(bean);
        TestBean decode = decoder.decode(encode, TestBean.class);
        assertNotNull(decode);
        assertEquals("123", decode.getName());
    }
} 
