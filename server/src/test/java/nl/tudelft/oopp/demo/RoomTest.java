package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.time.LocalDateTime;

import java.time.Month;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomTest {


    @Autowired
    private RoomRepository roomRepository;

    private Room roomOne;
    private Room roomTwo;

    @Test
    public void saveAndRetrieveRoomTest() throws MalformedURLException {

        Room expected = new Room(
                1,
                LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 0),
                "Reasoning and Logic");
        roomRepository.save(expected);

        Room output = roomRepository.getOne((long) 1);
        assertEquals(expected, output);
    }

    /**
     * Generating two rooms to be tested.
     */
    public RoomTest() {
        try {
            roomOne = new Room(LocalDateTime.of(2021, Month.MAY, 19, 10, 45, 0),
                    "Linear Algebra");
            roomTwo = new Room(1, LocalDateTime.of(2022, Month.OCTOBER, 22, 10, 30, 0),
                    "CSE1200");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull(roomOne);
        assertNotNull(roomTwo);
        assertEquals("Linear Algebra", roomOne.getRoomName());
        assertNotNull(roomOne.getStudentsLink());
        assertNotNull(roomOne.getModeratorLink());
        assertEquals(1, roomTwo.getRoomId());
        assertEquals("CSE1200", roomTwo.getRoomName());
        assertNotNull(roomTwo.getStudentsLink());
        assertNotNull(roomTwo.getModeratorLink());
    }

    @Test
    public void testLinkGenerator() {
        assertTrue(roomOne.getStudentsLink().toString().contains("http://localhost:8080/rooms/"));
        assertTrue(roomOne.getModeratorLink().toString().contains("http://localhost:8080/rooms/M"));
        assertTrue(roomTwo.getStudentsLink().toString().contains("http://localhost:8080/rooms/"));
        assertTrue(roomTwo.getModeratorLink().toString().contains("http://localhost:8080/rooms/M"));
        assertNotEquals(roomTwo.getStudentsLink(), roomOne.getStudentsLink());
        assertNotEquals(roomTwo.getModeratorLink(), roomOne.getModeratorLink());
    }

    @Test
    public void testToString() {
        assertEquals("Linear Algebra\n(10:45)\n2021/05/19", roomOne.toString());
        assertEquals("CSE1200\n(10:30)\n2022/10/22", roomTwo.toString());
    }
}

