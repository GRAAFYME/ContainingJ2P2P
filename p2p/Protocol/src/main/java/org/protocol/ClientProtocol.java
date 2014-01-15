package org.protocol;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ClientProtocol {
	@XmlElement
	public String idAGV;

	@XmlElement
	public String idCrane;

	@XmlElement
	public List<Float> lengthRoutes;

	@XmlElement
	public List<MotionPathProtocol> motionPathList;

	public ClientProtocol() {
	}

	public String getIDAGV() {
		return idAGV;
	}

	public String getIDCrane() {
		return idCrane;
	}
}
