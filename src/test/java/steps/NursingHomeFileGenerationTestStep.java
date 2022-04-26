package steps;

import GenericInfo.GenericFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;

public class NursingHomeFileGenerationTestStep {
    GenericFactory gf = new GenericFactory();
    @When("user hit on server with the IP address {string} on port {int}")
    public void  userHitOnServerWithTheIpAddressOnPort(String ipAddress, Integer port) {
       boolean isMessageSentToServer = gf.isMessageSentToServer(ipAddress,port);
       Assert.assertTrue("Message cannot send to the Server", isMessageSentToServer);
    }

    @Then("user should check the fileName with fileUrl {string}")
    public void userShouldCheckTheFileNameWithFileUrl(String fileUrl) {
        String fileName = gf.getFileName(fileUrl);
        String extension = FilenameUtils.getExtension(fileName);
        boolean isFileExist = gf.checkFileIsExistOrNot(fileName, extension);
        Assert.assertTrue("FileName Is Not Exist ",isFileExist);
    }

    @And("user should receive the patient medication details {string}")
    public void userShouldReceiveThePatientMedicationDetails(String expectedResult) {
        String fileContent = gf.getFilePatientContent();
        Assert.assertEquals(expectedResult, fileContent);
    }
}
