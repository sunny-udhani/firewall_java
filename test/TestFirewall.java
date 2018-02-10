import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestFirewall {

    Firewall fw;

    @Before
    public void setUp() throws Exception {
        fw = new Firewall("./rules.csv");
    }

    @After
    public void tearDown() throws Exception {
        fw = null;
    }

    @Test
    public void packetTest() {
        boolean test1 = fw.accept_packet("inbound", "tcp", 80, "192.168.1.2");
        boolean test2 = fw.accept_packet("inbound", "tcp", 81, "192.168.1.2");

        assertEquals(true, test1);
        assertEquals(false, test2);

    }
}