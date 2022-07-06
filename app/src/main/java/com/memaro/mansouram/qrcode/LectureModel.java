package com.memaro.mansouram.qrcode;

public class LectureModel {
    String lectureName, lectureId;

    public LectureModel() {
    }

    public LectureModel(String lectureName, String lectureId) {
        this.lectureName = lectureName;
        this.lectureId = lectureId;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }
}
