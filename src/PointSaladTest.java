import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PointSaladTest {

    private PointSalad pointSalad;
    private PointSalad.Player player;
    private Socket mockSocket;
    private ObjectInputStream mockInputStream;
    private ObjectOutputStream mockOutputStream;

    @BeforeEach
    public void setUp() {
        String[] args = {};
        pointSalad = new PointSalad(args);
//        mockSocket = null; // Assuming null for offline player
//        mockInputStream = null; // Mock or actual streams can be used
//        mockOutputStream = null; // Mock or actual streams can be used
//        player = pointSalad.new Player(1, false, mockSocket, mockInputStream, mockOutputStream);
    }

//    @Test
//    public void TestCriteria1_1Player() throws Exception{
//        assertEquals(,pointSalad.server(1,0));
//    }
    @Test
    public void testPlayerInitialization() {
        assertEquals(1, player.playerID);
        assertFalse(player.isBot);
        assertFalse(player.online);
        assertNotNull(player.hand);
        assertEquals(0, player.score);
    }

    @Test
    public void testSendMessageOffline() {
        player.sendMessage("Test Message");
        // Since the player is offline, we expect no exception and no output to client
        // This test is more about ensuring no exceptions are thrown
    }

    @Test
    public void testSendMessageOnline() {
        // Setup player as online
        mockSocket = new Socket();
        player = pointSalad.new Player(1, false, mockSocket, mockInputStream, mockOutputStream);
        player.online = true;

        // Mock the output stream to verify message sending
        // This part would require a mocking framework like Mockito to properly test
        // For simplicity, we assume the stream works and no exception is thrown
        player.sendMessage("Test Message");
    }
}