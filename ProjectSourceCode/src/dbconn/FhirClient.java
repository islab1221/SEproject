package dbconn;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FhirClient {

    private FhirContext ctx;
    private IGenericClient client;
    private final String serverBase = "http://hapi.fhir.org/baseR4";

    public FhirClient(){
        ctx = FhirContext.forR4();
        client = ctx.newRestfulGenericClient(serverBase);

    }



    public Patient getPatient(int FHIRid){
        Patient patient = client.read()
                                .resource(Patient.class)
                                .withId(Integer.toString(FHIRid))
                                .execute();
        return patient;
    }

    public Patient getPatientByIdent(String ID){
        Bundle bundle = client.search()
                                .forResource(Patient.class)
                                .where(Patient.IDENTIFIER.exactly().code(ID))
                                .prettyPrint()
                                .returnBundle(Bundle.class)
                                .execute();

        return (Patient) bundle.getEntry().get(0).getResource();
    }

    public Bundle getProcedure(int FHIRid){
        Bundle procedure = client.search()
                                    .forResource(Procedure.class)
                                    .where(Procedure.PATIENT.hasId(Integer.toString(FHIRid)))
                                    .prettyPrint()
                                    .returnBundle(Bundle.class)
                                    .execute();

        return procedure;

    }

    public Bundle getMedicationStatement(int FHIRid){
        Bundle medicationstatement = client.search()
                .forResource(MedicationStatement.class)
                .where(MedicationStatement.PATIENT.hasId(Integer.toString(FHIRid)))
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();

        return medicationstatement;
    }

    public Bundle getServiceRequest(int FHIRid){
        Bundle servicerequest = client.search()
                .forResource(ServiceRequest.class)
                .where(ServiceRequest.PATIENT.hasId(Integer.toString(FHIRid)))
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();

        return servicerequest;
    }
    public Bundle getServiceRequestByDate(String date){
        Bundle servicerequest = client.search()
                .forResource(ServiceRequest.class)
                .where(ServiceRequest.OCCURRENCE.exactly().day(date))
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();

        return servicerequest;
    }

    public Practitioner getPractitioner(int FHIRid){
        Practitioner practitioner = client.read()
                                            .resource(Practitioner.class)
                                            .withId(Integer.toString(FHIRid))
                                            .execute();

        return  practitioner;
    }

    public Bundle searchSpecifiedMedicationStatement(String Humanid){
        Bundle medicationStatement = client.search()
                                            .forResource(MedicationStatement.class)
                                            .where(MedicationStatement.IDENTIFIER.exactly().code(Humanid))
                                            .prettyPrint()
                                            .returnBundle(Bundle.class)
                                            .execute();


        return medicationStatement;

    }

    public Bundle getProcedureByDoctor(int FHIRid){
        Bundle bundle = client.search()
                                .forResource(Procedure.class)
                                .where(Procedure.PERFORMER.hasId(Integer.toString(FHIRid)))
                                .prettyPrint()
                                .returnBundle(Bundle.class)
                                .execute();

        return bundle;
    }

    public Bundle getServiceRequestByDoctor(int FHIRid){
        Bundle servicerequest = client.search()
                .forResource(ServiceRequest.class)
                .where(ServiceRequest.PERFORMER.hasId(Integer.toString(FHIRid)))
                .prettyPrint()
                .returnBundle(Bundle.class)
                .execute();

        return servicerequest;
    }

    public String createPatient(String name, String address, String id, int date[], String gender){
        Patient patient = new Patient();


        patient.addName(new HumanName().setText(name));
        patient.addAddress(new Address().setText(address));
        patient.addIdentifier().setSystem("身分證").setValue(id);
        Calendar calendar = Calendar.getInstance();
        calendar.set(date[0],date[1]-1,date[2]);
        patient.setBirthDate(calendar.getTime());
        if(gender.equals("男"))
            patient.setGender(Enumerations.AdministrativeGender.MALE);
        else if(gender.equals("女"))
            patient.setGender(Enumerations.AdministrativeGender.FEMALE);



        MethodOutcome outcome = client.create()
                .resource(patient)
                .prettyPrint()
                .encodedJson()
                .execute();

        return  outcome.getId().getValue();
    }


    public String createDoctor(String name, String address, String id, int date[], String gender, String department, String subject){
        Practitioner practitioner = new Practitioner();


        practitioner.addName(new HumanName().setText(name));
        practitioner.addAddress(new Address().setText(address));
        practitioner.addIdentifier().setSystem("身分證").setValue(id);
        Calendar calendar = Calendar.getInstance();
        calendar.set(date[0],date[1]-1,date[2]);
        practitioner.setBirthDate(calendar.getTime());
        if(gender.equals("男"))
            practitioner.setGender(Enumerations.AdministrativeGender.MALE);
        else if(gender.equals("女"))
            practitioner.setGender(Enumerations.AdministrativeGender.FEMALE);

        Practitioner.PractitionerQualificationComponent qualificationComponent = practitioner.addQualification();
        qualificationComponent.addIdentifier().setValue(department);
        qualificationComponent.setCode(new CodeableConcept().setText(subject));


        MethodOutcome outcome = client.create()
                .resource(practitioner)
                .prettyPrint()
                .encodedJson()
                .execute();

        return outcome.getId().getValue();
    }

    private String createProcedure(String location, String Id, String subject, int FHIRid){
        Procedure procedure = new Procedure();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        procedure.setCode(new CodeableConcept().setText(simpleDateFormat.format(calendar.getTime())));
        procedure.setLocation(new Reference(new Location().setName(location)));
        procedure.setCategory(new CodeableConcept().setText(subject));
        procedure.addPerformer(new Procedure.ProcedurePerformerComponent().setActor(new Reference(this.getPractitioner(FHIRid))).setFunction(new CodeableConcept().setText(this.getPractitioner(FHIRid).getName().get(0).getText())));

        procedure.setSubject(new Reference(this.getPatientByIdent(Id)));

        MethodOutcome outcome = client.create()
                .resource(procedure)
                .prettyPrint()
                .encodedJson()
                .execute();

        return outcome.getId().getValue();
    }

    public String createMedicationStatement(int FHIRid, String name, String location, String reason, String medication, String medicationId, String days, String ratio, String Id, String status){
        MedicationStatement medicationStatement = new MedicationStatement();
        Practitioner practitioner = this.getPractitioner(FHIRid);

        Calendar calendar = Calendar.getInstance();


        medicationStatement.setDateAsserted(calendar.getTime());
        medicationStatement.addIdentifier().setValue(name);
        medicationStatement.addIdentifier().setValue(Id);
        medicationStatement.setInformationSource(new Reference(new Practitioner().addName((HumanName) practitioner.getName().get(0))));
        medicationStatement.addDerivedFrom(new Reference(new Location().setName(location)));
        medicationStatement.addReasonCode(new CodeableConcept().setText(reason));
        medicationStatement.setMedication((Type) new CodeableConcept().setText(medication).setId(medicationId));
        medicationStatement.addDosage(new Dosage().setPatientInstruction(days).setMaxDosePerPeriod((Ratio) new Ratio().setId(ratio)));
        medicationStatement.setSubject(new Reference(this.getPatientByIdent(Id)));
        medicationStatement.addStatusReason(new CodeableConcept().setText(status));

        this.createProcedure(location, Id, practitioner.getQualificationFirstRep().getCode().getText(),FHIRid);

        MethodOutcome outcome = client.create()
                .resource(medicationStatement)
                .prettyPrint()
                .encodedJson()
                .execute();

        return outcome.getId().getValue();
    }

    public String createServiceRequest(int doctorFHIRid, int patientId, String date[],String department , String hospital){
        ServiceRequest serviceRequest = new ServiceRequest();

        Practitioner practitioner = this.getPractitioner(doctorFHIRid);

        serviceRequest.addIdentifier().setValue(this.getPatient(patientId).getIdentifierFirstRep().getValue());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(date[0]),Integer.parseInt(date[1])-1,Integer.parseInt(date[2]));
        serviceRequest.setCode(new CodeableConcept().setText(practitioner.getName().get(0).getText()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        serviceRequest.setOccurrence(new DateTimeType(simpleDateFormat.format(calendar.getTime())));
        serviceRequest.addPerformer(new Reference(practitioner));
        serviceRequest.addCategory(new CodeableConcept().setText(department));
        serviceRequest.addLocationReference(new Reference(new Location().setName(hospital).setDescription("235診室")));
        serviceRequest.setRequisition(new Identifier().setValue(this.getConsultationNumber(simpleDateFormat.format(calendar.getTime()))));
        serviceRequest.setSubject(new Reference(this.getPatient(patientId)));

        MethodOutcome outcome = client.create()
                .resource(serviceRequest)
                .prettyPrint()
                .encodedJson()
                .execute();

        return outcome.getId().getValue();

    }

    public void updatePatient(int FHIRid, String name, String gender, String address, String[] date){
        Patient newData = new Patient();
        Patient oldData = this.getPatient(FHIRid);
        newData.setId("Patient/" + FHIRid);
        newData.addIdentifier().setSystem("身分證").setValue(oldData.getIdentifierFirstRep().getValue());

        if (name.equals(""))
            newData.addName(oldData.getName().get(0));
        else
            newData.addName(new HumanName().setText(name));

        if (address.equals(""))
            newData.addAddress(oldData.getAddress().get(0));
        else
            newData.addAddress(new Address().setText(address));

        if (!date[0].equals("")) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1])-1, Integer.parseInt(date[2]));
            newData.setBirthDate(calendar.getTime());
        }
        else
            newData.setBirthDate(oldData.getBirthDate());

        if(!gender.equals("")) {
            if (gender.equals("男"))
                newData.setGender(Enumerations.AdministrativeGender.MALE);
            else if (gender.equals("女"))
                newData.setGender(Enumerations.AdministrativeGender.FEMALE);
        }
        else
            newData.setGender(oldData.getGender());

        MethodOutcome outcome = client.update()
                                        .resource(newData)
                                        .execute();
    }

    public void updateDoctor(int FHIRid, String name, String gender, String address, String[] date){
        Practitioner newData = new Practitioner();
        Practitioner oldData = this.getPractitioner(FHIRid);
        newData.setId("Patient/" + FHIRid);
        newData.addIdentifier().setSystem("身分證").setValue(oldData.getIdentifierFirstRep().getValue());
        newData.addQualification(oldData.getQualificationFirstRep());

        if (name.equals(""))
            newData.addName(oldData.getName().get(0));
        else
            newData.addName(new HumanName().setText(name));

        if (address.equals(""))
            newData.addAddress(oldData.getAddress().get(0));
        else
            newData.addAddress(new Address().setText(address));

        if (!date[0].equals("")) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1])-1, Integer.parseInt(date[2]));
            newData.setBirthDate(calendar.getTime());
        }
        else
            newData.setBirthDate(oldData.getBirthDate());

        if(!gender.equals("")) {
            if (gender.equals("男"))
                newData.setGender(Enumerations.AdministrativeGender.MALE);
            else if (gender.equals("女"))
                newData.setGender(Enumerations.AdministrativeGender.FEMALE);
        }
        else
            newData.setGender(oldData.getGender());

        MethodOutcome outcome = client.update()
                .resource(newData)
                .execute();
    }

    private String getConsultationNumber(String date){

        Bundle bundle = this.getServiceRequestByDate(date);

        if(bundle.getTotal()==0)//如果沒預約紀錄
            return "1";
        else{
            ServiceRequest lastServiceRequest = (ServiceRequest) bundle.getEntry().get(bundle.getTotal()-1).getResource();
            return Integer.toString(Integer.parseInt(lastServiceRequest.getRequisition().getValue()) + 1);
        }


    }
}
