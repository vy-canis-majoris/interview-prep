package cerner;
import java.util.Calendar;
import java.util.Date;

//import cerner.Medicine.Frequency;

public class Medication {
	public enum Frequency{
		FOUR_HOURS, ONCE_A_DAY, WITH_MEALS, BEFORE_BED, AS_NEEDED,HOUR 
	}
	public enum StopType{
		DAYS,DOSES,FOREVER;
	}
	private String medicineName;
	private Frequency frequency;
	private StopType stopType;
	private Date medicationStartDuration;
	private Date medicationEndDuration;
	private int duration;
	private Calendar tempEndDuration;
	
	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public StopType getStopType() {
		return stopType;
	}

	public void setStopType(StopType stopType) {
		this.stopType = stopType;
	}

	public Date getMedicationStartDate() {
		return medicationStartDuration;
	}

	public void setMedicationStartDate(Date medicationStartDate) {
		this.medicationStartDuration = medicationStartDate;
	}

	public Date getMedicationEndDate() {
		return medicationEndDuration;
	}

	public void setMedicationEndDate(Date medicationEndDate) {
		this.medicationEndDuration = medicationEndDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	
	
	public Medication(String medicineName,Frequency frequency,StopType stopType,Date startDate, int duration){
		this.medicineName=medicineName;
		//frequency is a variable of type Frequency which is used to store object.
		this.frequency=frequency;
		this.stopType = stopType;
		this.medicationStartDuration=startDate;
		this.duration=duration;
		this.medicationEndDuration=computeEndDate(frequency,stopType,duration,startDate);
	}

	private Date computeEndDate(Frequency frequency, StopType stopType, int duration, Date startDate) {
		Calendar tempDuration=Calendar.getInstance();
		tempDuration.setTime(startDate);
		if(stopType.equals(StopType.DAYS)){
			
			//Calendar.DAY_OF_MONTH returns DAY_OF_MONTH index in calendar class  
			System.out.println("DAYS");
			tempDuration.add(Calendar.DAY_OF_MONTH,duration);
		}
		
		//if medication needs to be stopped after certain no. of doses
		else if(stopType.equals(StopType.DOSES)){
			
			//diff cases based on frequencies
			switch(frequency){
			
			case FOUR_HOURS:
					
					for (int i=0;i<duration;i++){
						tempDuration.add(Calendar.HOUR_OF_DAY,4);
					}
					
					break;
					
			case ONCE_A_DAY : case WITH_MEALS: case BEFORE_BED :
					
					for (int i=0;i<duration;i++){
						//System.out.println("Once a day**");
						tempDuration.add(Calendar.DAY_OF_MONTH,1);
					}
					
					break;
					
				//when hour value changes dynamically then pass 'n' parameter to the method
			case HOUR:
					
					for (int i=0;i<duration;i++){
						tempDuration.add(Calendar.HOUR_OF_DAY,1);
					}
					
					break;
					
				//for this case, nurse gets remainder every day and gives the doses to patients asneeded for that specific day
			case AS_NEEDED:
						tempDuration.add(Calendar.DAY_OF_MONTH,1);
					break;
			default:
				System.out.println("default");
				break;
			}
		}
		else{
			
			//forever case, set year value to interger max value
			System.out.println("forever");
			tempDuration.set(Calendar.YEAR, Integer.MAX_VALUE);
		}
		
		//done with calculating endDate and assinging the end date present in temporary tempDate to endDate 
		tempEndDuration = tempDuration;
		return tempDuration.getTime();
	}
	
	public void getSchedule(){
		Calendar temp=Calendar.getInstance();
		temp.setTime(medicationStartDuration); 
		
		switch(frequency){

		case FOUR_HOURS:

		//remainder will be printed until temp is less than or equal to endDate
		while(temp.compareTo(tempEndDuration)<=0){

		System.out.println(temp.getTime());
		//System.out.println("four hours");

		temp.add(Calendar.HOUR_OF_DAY, 4);

		}

		break;

		case ONCE_A_DAY : case WITH_MEALS: case BEFORE_BED:

		while(temp.compareTo(tempEndDuration)<=0){

		System.out.println(temp.getTime());

		temp.add(Calendar.DAY_OF_MONTH, 1);

		}


		break;

		case HOUR:

			while(temp.compareTo(tempEndDuration)<=0){

			System.out.println(temp.getTime());

			temp.add(Calendar.HOUR_OF_DAY,1);

			}

		break;                                
		case AS_NEEDED:
			temp.add(Calendar.DAY_OF_MONTH,1);
			System.out.println(temp.getTime());
		break;
		default:
			System.out.println("default");
			break;
		}
	}

}
