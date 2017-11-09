package cerner;

import java.util.*;


public class Temperature {
	
	
	private double temperature;
	
	{	
		String.format("%.2f",temperature);
	}
	
	public enum MeasuringMethod{
		EAR, MOUTH, ARMPIT
	}
	private MeasuringMethod measuringMethod;
	
	public enum MeasuringUnit{
		CELCIUS, FAHRENHEIT, KELVIN
	}
	private MeasuringUnit measuringUnit;
	
	public void setTemperature(double temperature){
		this.temperature = temperature;
	}
	
	public double getTemperature(){
		return temperature;
	}
	
	public void setMeasuringMethod(MeasuringMethod measuringMethod){
		this.measuringMethod = measuringMethod;
	}
	
	public MeasuringMethod getMeasuringMethod(){
		return measuringMethod;
	}
	
	public void setMeasuringUnit(MeasuringUnit measuringUnit){
		this.measuringUnit = measuringUnit;
	}
	
	public MeasuringUnit getMeasuringUnit(){
		return measuringUnit;
	}
	
	public Temperature(){
		
	}
	
	public Temperature(double temperature, MeasuringMethod measuringMethod,MeasuringUnit measuringUnit){
		this.temperature = temperature;
		this.measuringMethod = measuringMethod;
		this.measuringUnit = measuringUnit;
	}
	
	
	public double fahrenheitToCelcius(double temperature){
		temperature = (temperature - 32) * 5/9;
		return temperature;
	}
	
	
	public double kelvinToCelcius(double temperature){
		temperature = temperature - 273;
		return temperature;
	}
	
	
	public boolean checkFever(Temperature initialTemperature){
		
		
		if(measuringUnit.equals(MeasuringUnit.FAHRENHEIT) && initialTemperature.measuringUnit.equals(MeasuringUnit.CELCIUS)){
			
			temperature = fahrenheitToCelcius(temperature);
		}else if(measuringUnit.equals(MeasuringUnit.FAHRENHEIT) && initialTemperature.measuringUnit.equals(MeasuringUnit.KELVIN)){
			temperature = kelvinToCelcius(temperature);
		}else if(measuringUnit.equals(MeasuringUnit.CELCIUS) && initialTemperature.measuringUnit.equals(MeasuringUnit.FAHRENHEIT)){
			temperature = celciusToFarenheit(temperature);
		}
		
		
		if(measuringMethod.equals(initialTemperature.getMeasuringMethod()) && temperature-initialTemperature.getTemperature()>1){
			return true;
		}
		
		
		else if(!measuringMethod.equals(initialTemperature.getMeasuringMethod()) && temperature-initialTemperature.getTemperature()>2){
			return true;
		}
		else{
			System.out.println(temperature +" "+initialTemperature.getTemperature());
			return false;
		}
	}
	
	//no need to write main method
	public static void main(String[] args){
		Temperature initialTemperature = new Temperature();
		initialTemperature.setTemperature(97.69889);
		initialTemperature.setMeasuringMethod(MeasuringMethod.MOUTH);
		initialTemperature.setMeasuringUnit(MeasuringUnit.FAHRENHEIT);
		
		//1am
		Temperature one = new Temperature();
		Temperature two = new Temperature();
		
		
		
		List<Temperature> list1 = new ArrayList<Temperature>();
		list1.add(one);
		list1.add(two);
		double sum = 0.0;
		for(Temperature t : list1){
			sum = sum + t.getTemperature();
		}
		double avg = sum/list1.size();
		System.out.println(initialTemperature.getTemperature());
		
		Temperature temperature1 = new Temperature();
		temperature1.setTemperature(100.4);
		temperature1.setMeasuringMethod(MeasuringMethod.MOUTH);
		temperature1.setMeasuringUnit(MeasuringUnit.FAHRENHEIT);
		
		boolean res = temperature1.checkFever(initialTemperature);
		System.out.println(res);
	}

}
