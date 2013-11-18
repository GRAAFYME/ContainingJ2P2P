package org.server;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 18/11/13
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.util.*;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import javax.vecmath.Point3d;
/**
 * Hello world!
 *
 */
public class xmlParser
{

    public List<Container> parse(String path) throws FileNotFoundException
    {
        List<Container> containerList = new ArrayList<Container>();

        try {
            //Configure the xpath stuff
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(path);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            //Grabs all containers, <record></record>
            XPathExpression recordExpr = xpath.compile("/recordset/record");

            //:TODO: stop presuming dates are in format d/m/j and locations in x/y/z
            //Grab arrival date
            XPathExpression arrivalDExpr = xpath.compile("aankomst/datum/d");
            XPathExpression arrivalMExpr = xpath.compile("aankomst/datum/m");
            XPathExpression arrivalJExpr = xpath.compile("aankomst/datum/j");
            XPathExpression arrivalFromExpr = xpath.compile("aankomst/tijd/van");
            XPathExpression arrivalTillExpr = xpath.compile("aankomst/tijd/tot");
            //Grab leave date
            XPathExpression leaveDExpr = xpath.compile("vertrek/datum/d");
            XPathExpression leaveMExpr = xpath.compile("vertrek/datum/m");
            XPathExpression leaveJExpr = xpath.compile("vertrek/datum/j");
            XPathExpression leaveFromExpr = xpath.compile("vertrek/tijd/van");
            XPathExpression leaveTillExpr = xpath.compile("vertrek/tijd/tot");
            //Grab location
            XPathExpression locationXExpr = xpath.compile("aankomst/positie/*");
            XPathExpression locationYExpr = xpath.compile("aankomst/positie/*");
            XPathExpression locationZExpr = xpath.compile("aankomst/positie/*");
            //Grab name
            XPathExpression nameExpr = xpath.compile("eigenaar/naam");
            XPathExpression containerNrExpr = xpath.compile("eigenaar/containernr");
            //Grab Transport types
            XPathExpression arrivalTransportXpr = xpath.compile("aankomst/soort_vervoer");
            XPathExpression leaveTransportXpr = xpath.compile("vertrek/soort_vervoer");
            //Grab Transport companies
            XPathExpression arrivalCompaniesXpr = xpath.compile("aankomst/bedrijf");
            XPathExpression leaveCompaniesXpr = xpath.compile("vertrek/bedrijf");
            //Grab weights
            XPathExpression emptyWeightXpr = xpath.compile("gewicht/leeg");
            XPathExpression fullWeightXpr = xpath.compile("gewicht/inhoud");
            //Grab content
            XPathExpression contentXpr = xpath.compile("inhoud/naam");
            XPathExpression contentTypeXpr = xpath.compile("inhoud/soort");
            XPathExpression contentDangerlevelXpr = xpath.compile("inhoud/gevaar");
            //Grab ISO
            XPathExpression isoExpr = xpath.compile("ISO");


            //evaluate the expression, this will grab all <record>... </record> nodes
            //instead of having a complex maze of switch/loop/if statements we can just
            //grab everything with a loop and a few more xpath expressions
            NodeList containerNodes = (NodeList)recordExpr.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < containerNodes.getLength(); i++) {
                Node n = containerNodes.item(i);
                Container container = new Container();
                //Grab ID
                container.id = n.getAttributes().getNamedItem("id").getTextContent();
                //Grab Arrive Date
                double d1 = (Double)arrivalDExpr.evaluate(n, XPathConstants.NUMBER);
                double m1 = (Double)arrivalMExpr.evaluate(n, XPathConstants.NUMBER);
                double j1 = (Double)arrivalJExpr.evaluate(n, XPathConstants.NUMBER);
                container.arrivalDate = new Point3d(d1, m1, j1);
                //Grab ArriveTime
                container.arriveTimeFrom = (String)arrivalFromExpr.evaluate(n, XPathConstants.STRING);
                container.arriveTimeTill = (String)arrivalTillExpr.evaluate(n, XPathConstants.STRING);

                //Grab leave Date (0 1 2) => (d m j)
                double d2 = (Double)leaveDExpr.evaluate(n, XPathConstants.NUMBER);
                double m2 = (Double)leaveMExpr.evaluate(n, XPathConstants.NUMBER);
                double j2 = (Double)leaveJExpr.evaluate(n, XPathConstants.NUMBER);
                container.leaveDate = new Point3d(d2, m2, j2);
                //Grab leave time
                container.leaveTimeFrom = (String)leaveFromExpr.evaluate(n, XPathConstants.STRING);
                container.leaveTimeTill = (String)leaveTillExpr.evaluate(n, XPathConstants.STRING);

                //TODO: check if the xml file contains float values, if so, change int -> float
                //Grab location (0 1 2) => (x y z)
                double x = (Double)locationXExpr.evaluate(n, XPathConstants.NUMBER);
                double y = (Double)locationYExpr.evaluate(n, XPathConstants.NUMBER);
                double z = (Double)locationZExpr.evaluate(n, XPathConstants.NUMBER);
                container.location = new Point3d(x, y, z);

                //Grab name
                container.name = (String)nameExpr.evaluate(n, XPathConstants.STRING);

                //Grab Transport types
                container.arrivalTransport = (String)arrivalTransportXpr.evaluate(n, XPathConstants.STRING);
                container.leaveTransport = (String)leaveTransportXpr.evaluate(n, XPathConstants.STRING);
                //Grab Transport companies
                container.arrivalCompany = (String)arrivalCompaniesXpr.evaluate(n, XPathConstants.STRING);
                container.leaveCompany = (String)leaveCompaniesXpr.evaluate(n, XPathConstants.STRING);
                //Grab weights
                container.emptyWeight = (Double)emptyWeightXpr.evaluate(n, XPathConstants.NUMBER);
                container.fullWeight = (Double)fullWeightXpr.evaluate(n, XPathConstants.NUMBER);
                //Grab content
                container.contentName = (String)contentXpr.evaluate(n, XPathConstants.STRING);
                container.contentType = (String)contentTypeXpr.evaluate(n, XPathConstants.STRING);
                container.contentDanger = (String)contentDangerlevelXpr.evaluate(n, XPathConstants.STRING);
                //Grab ISO
                container.iso = (Double)isoExpr.evaluate(n, XPathConstants.NUMBER);

                containerList.add(container);
            }
        }
        catch (Exception e) {
            System.out.println("Exception in Server.main: " + e.getMessage());
        }

        return containerList;
    }
}

