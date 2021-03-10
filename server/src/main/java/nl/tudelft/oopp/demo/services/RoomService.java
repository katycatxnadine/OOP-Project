package nl.tudelft.oopp.demo.services;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    /** Constructor for RoomService.
     * @param roomRepository - retrieves rooms from database.
     */
    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /** Called by RoomController.
     * @return a List of rooms.
     */
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    /** Called by RoomController.
     * @param id the id of room.
     * @return the room itself.
     */
    public Room getRoomById(long id) {
        return roomRepository.findById(id);
    }

    /** Called by RoomController.
     * @param room new Room object to be stored in the database
     */
    public Room addNewRoom(Room room) throws MalformedURLException {

        String roomName = room.getRoomName();
        LocalDateTime startingTime = room.getStartingTime();
        boolean active = room.isActive();

        Room updatedRoom = new Room(startingTime, roomName, active);

        roomRepository.save(updatedRoom);

        return updatedRoom;
    }

    /** Called by RoomController.
     * @param code the code (link?) of room.
     * @return the room itself.
     */
    public Room getRoomByCode(String code) {
        // The next 10 lines are just because we use URL instead of String
        URI uri = null;
        try {
            uri = new URI(code);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Check if the code is for a student or a moderator (probably will get changed later)
        if (code.contains("M")) {
            return roomRepository.findFirstByModeratorLink(url);
        } else {
            return roomRepository.findFirstByStudentsLink(url);
        }
    }

}
