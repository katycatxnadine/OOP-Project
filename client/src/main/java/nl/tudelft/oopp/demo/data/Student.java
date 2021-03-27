package nl.tudelft.oopp.demo.data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class Student extends User {

    private String ipAddress;
    private boolean banned;

    /** Constructor for the object Student with the following attributes.
     * Used by the ServerCommunication getStudent method.
     * @param id the id of the student generated by the DB
     * @param nickname the nickname of the student
     * @param room the room the student is in
     * @param ipAddress the IP address of the student
     * @param banned true if the user is banned, false otherwise
     */
    public Student(Long id, String nickname, Room room, String ipAddress, boolean banned) {
        super(id, nickname, room);
        this.ipAddress = ipAddress;
        this.banned = banned;
    }

    /** Constructor for the object Student used in SplashController ..
     * .. to create a new instance of Student with only those 2 attributes.
     * @param username nickname of the student
     * @param room the room the student is in
     */
    public Student(String username, Room room) {
        super(username, room);
        try {
            this.ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.banned = false;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public boolean isBanned() {
        return this.banned;
    }

    @Override
    public String getRole() {
        return super.getRole();
    }

    @Override
    public String toString() {
        return "Student " + super.getNickname() + " in lecture " + super.getRoom().getRoomName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Student student = (Student) o;
        return isBanned() == student.isBanned() && getIpAddress().equals(student.getIpAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIpAddress(), isBanned());
    }
}

