package com.memaro.mansouram.chatbot;

public class ChatBotModel {
    private String message;
    private boolean speakerState ;
    private boolean yesState ;
    private boolean noState ;
    private boolean specialtiesState ;
    private boolean lecturesState ;

    public ChatBotModel() {
    }

    public ChatBotModel(String message, boolean speakerState, boolean yesState, boolean noState, boolean specialtiesState, boolean lecturesState) {
        this.message = message;
        this.speakerState = speakerState;
        this.yesState = yesState;
        this.noState = noState;
        this.specialtiesState = specialtiesState;
        this.lecturesState = lecturesState;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSpeakerState() {
        return speakerState;
    }

    public boolean isYesState() {
        return yesState;
    }

    public boolean isNoState() {
        return noState;
    }

    public boolean isSpecialtiesState() {
        return specialtiesState;
    }

    public boolean isLecturesState() {
        return lecturesState;
    }

    public ChatBotModel setMessage(String message) {
        this.message = message;
        return this;

    }

    public ChatBotModel setSpeakerState(boolean speakerState) {
        this.speakerState = speakerState;
        return this;

    }

    public ChatBotModel setYesState(boolean yesState) {
        this.yesState = yesState;
        return this;

    }

    public ChatBotModel setNoState(boolean noState) {
        this.noState = noState;
        return this;

    }

    public ChatBotModel setSpecialtiesState(boolean specialtiesState) {
        this.specialtiesState = specialtiesState;
        return this;

    }

    public ChatBotModel setLecturesState(boolean lecturesState) {
        this.lecturesState = lecturesState;
        return this;
    }
}

