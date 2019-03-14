package stepDefinitions;

import cucumber.api.java.en.When;
import resources.SFTPConnection;
import resources.base;

public class DownloadStepDef extends base {

    @When("^load all ais files$")
    public void loadAllAisFiles() {
        SFTPConnection con = new SFTPConnection();
        con.downloadTheFilesFromAISFeed(getFeedRunId());
    }
}
