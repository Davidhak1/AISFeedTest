package model;

public class ProgramLocal {
    private long id;
    private long programID;
    private long groupID;
    private String compatibleProgramsString;

    public ProgramLocal(long id, long programID, long groupID, String compatibleProgramsString) {
        this.id = id;
        this.programID = programID;
        this.groupID = groupID;
        this.compatibleProgramsString = compatibleProgramsString;
    }

    public long getProgramID() {
        return programID;
    }

    public void setProgramID(long programID) {
        this.programID = programID;
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    public String getCompatibleProgramsString() {
        return compatibleProgramsString;
    }

    public void setCompatibleProgramsString(String compatibleProgramsString) {
        this.compatibleProgramsString = compatibleProgramsString;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("programLocal{ id=%d, programID=%d, groupID=%d, compatibleProgramsString='%s'}",
                id, programID, groupID, compatibleProgramsString);
    }
}
