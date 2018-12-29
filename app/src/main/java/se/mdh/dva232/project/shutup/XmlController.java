package se.mdh.dva232.project.shutup;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlController {
    private Context context;
    private String xmlFilename = "events.xml";
    private String xmlContent = null;
    private XmlSerializer xmlSerializer = Xml.newSerializer();
    private Boolean debug = true;       // true: with Log.d output; false: without Log.d output

    /**
     * Constructor for these class
     * @param c     Context     context of that application for access to the open file functions
     */
    XmlController(Context c) {
        if(debug) { Log.d("XMLC", "constructor"); }
        context = c;
    }

    /**
     * Clear the XML content string in the class
     */
    private void resetXmlContent() {
        if(debug) { Log.d("XMLC","resetXmlContent()"); }
        setXmlContent( null );
    }

    /**
     * Set the XML content string with a string.
     * @param content   String      XML content
     */
    private void setXmlContent(String content) {
        if(debug) { Log.d("XMLC","setXmlContent(...)"); }
        xmlContent = content;
    }

    /**
     * Returns the XML content string
     * @return      String      XML content
     */
    String getXmlContent() {
        if(debug) { Log.d("XMLC","getXmlContent()"); }
        return xmlContent;
    }

    void logCurrentXmlContent() {
        Log.d("XMLC", "XML content: " + getXmlContent() );
    }
    /**
     * Is there any XML content?
     * @return      Boolean     True: yes; False: no
     */
    private Boolean isXmlContent() {
        if(debug) { Log.d("XMLC","isXmlContent()"); }
        if( (getXmlContent() != null) && (!getXmlContent().isEmpty()) && (getXmlContent().length() > 0) ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates the skeleton (start and end) of the XML content (XML valid)
     */
    void createXmlContentSkeleton() {
        if(debug) { Log.d("XMLC", "createXmlContentSkeleton()"); }
        StringWriter writer = new StringWriter();
        try {
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag("", "events");
            xmlSerializer.endTag("","events");
            xmlSerializer.endDocument();
            setXmlContent( writer.toString() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds an event to the XML content string
     * @param startDate     Date        start date of the event
     * @param startTime     Date        start time of the event
     * @param endDate       Date        end date of the event
     * @param endTime       Date        end time of the event
     * @param name          String      name of the event
     */
    void addEventToXmlContent(Date startDate, Date startTime, Date endDate, Date endTime, String name) {
        if(debug) { Log.d("XMLC", "addEventToXmlContent()"); }
        try {
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormater = new SimpleDateFormat("hh:mm");

            // instance the DOM object
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            InputSource xml = new InputSource(new StringReader( getXmlContent() ));
            Document doc = docBuilder.parse( xml );

            Node nEvents = doc.getFirstChild();
            NodeList nEventsList = nEvents.getChildNodes();
            Log.d("XMLC","count events #1: " + nEventsList.getLength() );

            // create content (elements) of one event
            Element eStartDate = doc.createElement("start_date");
            eStartDate.appendChild(doc.createTextNode( dateFormater.format(startDate) ));
            Element eStartTime = doc.createElement("start_time");
            eStartTime.appendChild(doc.createTextNode( timeFormater.format(startTime) ));
            Element eEndDate = doc.createElement("end_date");
            eEndDate.appendChild(doc.createTextNode( dateFormater.format(endDate) ));
            Element eEndTime = doc.createElement("end_time");
            eEndTime.appendChild(doc.createTextNode( timeFormater.format(endTime) ));
            Element eName = doc.createElement("name");
            eName.appendChild(doc.createTextNode( name ));

            // create event node with content (elements)
            Element eEvent = doc.createElement("event");
            eEvent.appendChild(eStartDate);
            eEvent.appendChild(eStartTime);
            eEvent.appendChild(eEndDate);
            eEvent.appendChild(eEndTime);
            eEvent.appendChild(eName);
            nEvents.appendChild(eEvent);

            // write new event to the XML content in the class
            StringWriter writer = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            /*
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            */
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            setXmlContent( writer.toString() );

            NodeList nEventsList2 = nEvents.getChildNodes();
            Log.d("XMLC","count events #2: " + nEventsList2.getLength() );

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    /**
     * Saves the XML content in tha class to the XML file on the device
     */
    void saveXmlToFile() {
        if(debug) { Log.d("XMLC","saveXmlToFile()"); }
        FileOutputStream xmlFile;
        try {
            xmlFile = context.openFileOutput(xmlFilename, Context.MODE_PRIVATE);
            xmlFile.write( getXmlContent().getBytes() );
            xmlFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the XML file from the device and load it into the XML content in the class
     * @return   Boolean    True: success; False: Error
     */
    Boolean readXmlFileAndLoad() {
        if(debug) { Log.d("XMLC", "readXmlFileAndLoad()"); }
        try {
            FileInputStream xmlFile = context.openFileInput(xmlFilename);
            InputStreamReader inputStreamReader = new InputStreamReader(xmlFile);
            char[] inputBuffer = new char[xmlFile.available()];
            inputStreamReader.read(inputBuffer);
            setXmlContent( new String(inputBuffer) );
            inputStreamReader.close();
            xmlFile.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
