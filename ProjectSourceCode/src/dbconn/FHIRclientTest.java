package dbconn;


import org.hl7.fhir.r4.model.MedicationStatement;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;

public class FHIRclientTest {

    private FhirClient client = new FhirClient();

    @Test
    public void getPatientTest(){
        assertEquals("F111111111" , client.getPatient(433583).getIdentifier().get(0).getValue());
    }

    @Test
    public void getPatientByIdent(){
        assertEquals("F111111111", client.getPatient(433583).getIdentifier().get(0).getValue());
    }

    @Test
    public void getProcedureTest(){
        assertEquals("Procedure", client.getProcedure(433583).getEntry().get(0).getResource().getResourceType().name());
    }

    @Test
    public void getMedicationStatementTest(){
        assertEquals("MedicationStatement", client.getMedicationStatement(433583).getEntry().get(0).getResource().getResourceType().name());
    }

    @Test
    public void getServiceRequestByDateTest(){
        assertEquals("ServiceRequest", client.getServiceRequestByDate("2020-01-29").getEntry().get(0).getResource().getResourceType().name());
    }

    @Test
    public void getPractitionerTest(){
        assertEquals("F123456789", client.getPractitioner(579942).getIdentifierFirstRep().getValue());
    }

    @Test
    public void searchSpecifiedMedicationStatementTest(){
        assertEquals("F111111111", ((MedicationStatement)client.searchSpecifiedMedicationStatement("F111111111").getEntry().get(0).getResource()).getIdentifier().get(1).getValue());
    }

    @Test
    public void getProcedureByDoctor(){
        assertEquals("Practitioner/579942", ((Procedure)client.getProcedureByDoctor(579942).getEntry().get(0).getResource()).getPerformerFirstRep().getActor().getReference());
    }

    @Test
    public void getServiceRequestByDoctor(){
        assertEquals("Practitioner/579942", ((ServiceRequest)client.getServiceRequestByDoctor(579942).getEntry().get(0).getResource()).getPerformerFirstRep().getReference());
    }

    @Test
    public void createPatientTest(){
        int date[]={2019,1,1};
        assertNotSame("",client.createPatient("測試用", "測試用", "F000000000", date, "男"));
    }

    @Test
    public void createDoctorTest(){
        int date[]={2019,1,1};
        assertNotSame("",client.createDoctor("測試用", "測試用", "F000000000", date, "男", "測試用", "測試用"));
    }

    @Test
    public void createMedicationStatementTest(){
        assertNotSame("", client.createMedicationStatement(80198, "測試用", "測試用", "測試用","測試用", "123", "3", "6", "5d1a4dfe-89f2-4a35-b720-b75ef28d6e59", "測試用"));
    }

    @Test
    public void createServiceRequestTest(){
        String date[]={"2020","2","8"};
        assertNotSame("", client.createServiceRequest(80198, 5, date, "測試用", "測試用"));
    }


}
