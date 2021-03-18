package nl.tudelft.oopp.demo.controllers;

import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.data.Question;
import nl.tudelft.oopp.demo.data.Room;
import nl.tudelft.oopp.demo.data.User;
import nl.tudelft.oopp.demo.views.ModeratorView;

public class ModeratorRoomController {

    @FXML
    private Button endLecture;

    @FXML
    private Label lectureName;

    @FXML
    private  Label tooSlowLabel;

    @FXML
    private Label tooFastLabel;

    private User moderator;
    private Room room;
    private ModeratorView moderatorView;

    /** Used in SplashController to pass the user and the room object.
     * This method should be called after the fetch request so that it updates the information.
     * @param moderator the moderator that is using the window
     * @param room the room corresponding to the code entered
     * @param moderatorView - corresponding view to this controller (to add questions)
     */
    public void setData(User moderator, Room room, ModeratorView moderatorView) {
        this.moderator = moderator;
        this.room = room;
        this.moderatorView = moderatorView;
        this.lectureName.setText(this.room.getRoomName());
        setFeedback();

        // Next 3 lines are to execute the question refreshing every X seconds
        Timer t = new Timer();
        QuestionRefresher st = new QuestionRefresher();
        t.schedule(st,0,5000);
    }



    // Used just by the timer to refresh the questions every X seconds
    public class QuestionRefresher extends TimerTask {

        public void run() {
            moderatorView.updateAnsweredList();
        }
    }

    /** Updates the feedback for the moderators.
     * For it to be done in real time it needs the fetch request.
     */
    public void setFeedback() {
        tooSlowLabel.setText(Math.round(
                this.room.getPeopleThinkingLectureIsTooSlow() * 100
                        / this.room.getParticipants().size()
                        + this.room.getPeopleThinkingLectureIsTooSlow() * 100
                        % this.room.getParticipants().size()) + "%");

        if (Integer.parseInt(tooSlowLabel.getText().replace("%","")) < 10) {
            tooSlowLabel.setTextFill(Paint.valueOf("DARKGREEN"));
        } else {
            tooSlowLabel.setTextFill(Paint.valueOf("RED"));
        }

        tooFastLabel.setText(Math.round(
                this.room.getPeopleThinkingLectureIsTooFast() * 100
                        / this.room.getParticipants().size()
                        + this.room.getPeopleThinkingLectureIsTooFast() * 100
                        % this.room.getParticipants().size()) + "%");

        if (Integer.parseInt(tooFastLabel.getText().replace("%","")) < 10) {
            tooFastLabel.setTextFill(Paint.valueOf("DARKGREEN"));
        } else {
            tooFastLabel.setTextFill(Paint.valueOf("RED"));
        }
    }

    /** The method that is executed when the End lecture button is clicked.
     */
    public void endLecture() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to end the lecture?");
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            if (room == null || !room.isActive()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("The room does not exist or has ended already!");
                error.show();
            }
            ServerCommunication.updateRoom(room.getModeratorLink().toString());
            room.end();
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setContentText("The lecture has ended successfully!");
            success.show();
            endLecture.setDisable(true);
        }
    }


    /**
     * Deletes this question upon pressing "delete" or "mark as answered" buttons.
     * Based on id of this question.
     * @param questionToRemove - Question to be removed from database.
     */
    public boolean deleteQuestion(Question questionToRemove) {

        if (!ServerCommunication.deleteQuestion(questionToRemove.getId())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Server error!");
            alert.show();
            return false;
        }
        return true;

    }

    /**
     * Edits this question according to new text entered upon pressing:
     *  - "edit answer" button in QuestionCell
     *  - "edit answer" button in AnsweredCell
     * Based on id of this question.
     * @param questionToEdit - Question to edit content of in database.
     */
    public boolean editQuestion(Question questionToEdit, String update) {

        if (update.length() > 0) {

            questionToEdit.setText(update);

            if (!ServerCommunication.editQuestion(questionToEdit.getId(), update)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Server error!");
                alert.show();
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Sets answer to this question in db.
     * Based on id of this question.
     * @param question - Question to set answer of content of in database.
     */
    public boolean setAnswer(Question question, String answer) {

        if (answer.length() > 0) {

            if (!ServerCommunication.setAnswer(question.getId(), answer)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Server error!");
                alert.show();
                return false;
            }
            return true;
        }
        return false;
    }




}
