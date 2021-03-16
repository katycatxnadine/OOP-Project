package nl.tudelft.oopp.demo.entities;

public class Moderator extends User {

    public Moderator(String username, Room room) {
        super(username, room);
    }

    @Override
    public String toString() {
        return "Moderator " + super.getNickname() + " in room " + super.getRoom().getRoomId();
    }

}
