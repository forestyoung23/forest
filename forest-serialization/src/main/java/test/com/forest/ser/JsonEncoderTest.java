package test.com.forest.ser;

import com.forest.ser.Encoder;
import com.forest.ser.JsonEncoder;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * JsonEncoder Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>6月 30, 2022</pre>
 */
public class JsonEncoderTest {

    /**
     * Method: encode(Object obj)
     */
    @Test
    public void testEncode() throws Exception {
        Encoder encoder = new JsonEncoder();
        TestBean bean = new TestBean();
        bean.setName("123");
        byte[] encode = encoder.encode(bean);
        assertNotNull(encode);
    }
} 
