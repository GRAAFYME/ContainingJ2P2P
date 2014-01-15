package org.protocol;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ProtocolParser {
	public String serialize(Object protocol) {
		try {
			OutputStream stream = new ByteArrayOutputStream();

			JAXBContext jaxbContext = JAXBContext.newInstance(protocol
					.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(protocol, stream);

			// Not a proper way they say
			return stream.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public ClientProtocol deserializeClient(String packet) {
		InputStream stream = new ByteArrayInputStream(packet.getBytes());
		ClientProtocol p = null;

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ClientProtocol.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			p = (ClientProtocol) jaxbUnmarshaller.unmarshal(stream);
			return p;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public ServerProtocol deserialize(String packet) {
		InputStream stream = new ByteArrayInputStream(packet.getBytes());
		ServerProtocol p = null;

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ServerProtocol.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			p = (ServerProtocol) jaxbUnmarshaller.unmarshal(stream);
			return p;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}
}
