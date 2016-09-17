package appealmodels;

import javax.xml.bind.annotation.XmlEnumValue;

public class StatusofAppeal {

	public enum AppealStatus {
	    @XmlEnumValue(value="created")
	    CREATED,
	    @XmlEnumValue(value="submitted")
	    SUBMITTED, 
	    @XmlEnumValue(value="inprocess")
	    INPROCESS, 
	    @XmlEnumValue(value="approved")
	    APPROVED,
	    @XmlEnumValue(value="declined")
	    DECLINED,
	    @XmlEnumValue(value="abandoned")
	    ABANDONED,
	    @XmlEnumValue(value="followup")
	    FOLLOWUP,
	    @XmlEnumValue(value="closed")
	    CLOSED,
	}
	
}
