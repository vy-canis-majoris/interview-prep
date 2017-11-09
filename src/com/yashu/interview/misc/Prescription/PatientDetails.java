package cerner;

import java.util.ArrayList;
import java.util.Calendar;

import cerner.Medication.Frequency;
import cerner.Medication.StopType;

public class PatientDetails {
	private String patientName;
	private ArrayList<Medication> medicinesList=new ArrayList<Medication>();
	
	public void addMedicine(Medication medicine){
		medicinesList.add(medicine);
	}

	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public ArrayList<Medication> getMedicnesList() {
		return medicinesList;
	}
	public void setMedicnesList(ArrayList<Medication> medicnesList) {
		this.medicinesList = medicnesList;
	}
	
	public static void main(String[] args) {
		
		PatientDetails robert=new PatientDetails();
		Calendar calendar=Calendar.getInstance();
		robert.setPatientName("Robert");
		robert.addMedicine(new Medication("Paracetmol", Frequency.ONCE_A_DAY,StopType.DAYS,calendar.getTime(),40));
	//	robert.addMedicine(new Medication("Zyrtec",Frequency.WITH_MEALS,StopType.DOSES,calendar.getTime(),3));
		
		//print medicines
		System.out.println("Patient Name: "+robert.getPatientName());
		ArrayList<Medication> list=robert.getMedicnesList();
		for(Medication medicine:list){
			System.out.printf("MedicineName:%s%nFrequency:%s%nStartDuration:%s%nEndDuration:%s%n", medicine.getMedicineName(),medicine.getFrequency(),medicine.getMedicationStartDate(),medicine.getMedicationEndDate());
			System.out.println("Schedule :");
			medicine.getSchedule();
			System.out.println("\n");
		}
		
		

	}


}
