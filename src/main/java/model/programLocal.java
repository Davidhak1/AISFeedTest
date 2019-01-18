package model;

public class programLocal {
    private long programID;
    private long groupID;
    private String compatibleProgramsString;

    public programLocal(long programID, long groupID, String compatibleProgramsString) {
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

    @Override
    public String toString() {
        return String.format("programLocal{programID=%d, groupID=%d, compatibleProgramsString='%s'}",
                programID, groupID, compatibleProgramsString);
    }
}
