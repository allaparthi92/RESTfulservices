package Grade.CURD_Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraderBookService {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(GraderBookService.class);
	static ConcurrentHashMap<Integer, ArrayList<GradeBook>> hm= new ConcurrentHashMap<Integer,ArrayList<GradeBook>>();
	
	static Converter converter = new Converter();
		
	public static String AddStudentdetails(String inputXML) throws JAXBException{
		
	
		GradeBook gb = (GradeBook) converter.convertFromXmlToObject(inputXML,GradeBook.class);
		if(hm.containsKey(gb.getStudentid())){
			ArrayList<GradeBook> al = hm.get(gb.getStudentid());
			for(GradeBook gb1: al){
				if(gb1.getAssignemntname().equalsIgnoreCase(gb.getAssignemntname())){
					String result = "";
					return result;
					
				}
			}
			al.add(gb);
			hm.put(gb.getStudentid(),al);
		}
		else{
			ArrayList al = new ArrayList<GradeBook>();
			al.add(gb);
			hm.put(gb.getStudentid(),al);
		}
		String result = "The student with "+ gb.getStudentid()+ "for "+ gb.getAssignemntname()+ " is added";
		System.out.println(hm);
		return result;
	}
	
	public static String SearchById(Integer id){
		
		StringBuilder  result = new StringBuilder();
		if(hm.containsKey(id)){
			ArrayList<GradeBook> al = hm.get(id);
			for(GradeBook gb: al){
				result.append(converter.convertFromObjectToXml(gb, GradeBook.class));
			}
		}
		return result.toString();
		
	}

	public static String SearchByIdName(Integer studentId,
			String assignemnetName) {
		String result = "";
		if(hm.containsKey(studentId)){
			ArrayList<GradeBook> al = hm.get(studentId);
			for(GradeBook gb: al){
				if(gb.getAssignemntname().equalsIgnoreCase(assignemnetName)){
					result = converter.convertFromObjectToXml(gb, GradeBook.class);
					break;
				}
			}
		}
		
		return result;
	}

	public static String SearchByName(String assignemnetName) {
		StringBuilder  result = new StringBuilder();
		for(Entry<Integer, ArrayList<GradeBook>> entry:hm.entrySet()){
			ArrayList<GradeBook> al = entry.getValue();
			for(GradeBook gb: al){
				if(gb.getAssignemntname().equalsIgnoreCase(assignemnetName)){
					result.append(converter.convertFromObjectToXml(gb, GradeBook.class));
				}
			}
		}
		return result.toString();
	}

	public static String deleteByID(Integer id) {
		String result = "";
		if(hm.containsKey(id)){
			hm.remove(id);
			result = "The student with "+ id + " is deleted";
		}
		return result;
	}
	
	public static String deleteByIDName(Integer id, String assignemnetName) {
		String result = "";
		if(hm.containsKey(id)){
			ArrayList<GradeBook> al = hm.get(id);
			for(GradeBook gb: al){
				if(gb.getAssignemntname().equalsIgnoreCase(assignemnetName)){
					al.remove(gb);
					hm.put(id,al);
					result = "The Student with "+id +" for " + assignemnetName + " is deleted";
					break;
				}
			}
		}
		return result;
	}

	public static String deleteByName(String assignemnetName) {
		// TODO Auto-generated method stub
		Boolean flag = false;
		for(Entry<Integer, ArrayList<GradeBook>> entry:hm.entrySet()){
		ArrayList<GradeBook> al = entry.getValue();
		for(GradeBook gb: al){
			if(gb.getAssignemntname().equalsIgnoreCase(assignemnetName)){
				flag = true;
				al.remove(gb);
				hm.remove(gb.getStudentid());
				hm.put(gb.getStudentid(), al);
				break;
			}
		}
	  }
		if(!flag)
			return "";
		String result = "The records with "+ assignemnetName + " are deleted";
		return result;
	}

	public static String UpdateById(String cntent) throws JAXBException {
		String result ="";
		GradeBook gb = (GradeBook) converter.convertFromXmlToObject(cntent,GradeBook.class);
		if(hm.containsKey(gb.getStudentid())){
			ArrayList<GradeBook> al = hm.get(gb.getStudentid());
			for(GradeBook gb1: al){
				if(gb1.getAssignemntname().equalsIgnoreCase(gb.getAssignemntname())){
					al.remove(gb1);
					al.add(gb);
					hm.put(gb.getStudentid(),al);
					result = "The Student with "+gb.getStudentid() +" for " + gb.getAssignemntname() + " is updated";
					break;
		}
			}
		}
		else
			result = null;
		
		return result;
	}

}

