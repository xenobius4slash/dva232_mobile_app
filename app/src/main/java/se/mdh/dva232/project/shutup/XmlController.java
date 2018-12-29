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

import java.io.DataInput;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * That class includes all methods for handling with the XML content string and the XML file in the storage of the device.
 * @author Sylvio Ujvari
 */
class XmlController {
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
    void resetXmlContent() {
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
    private String getXmlContent() {
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
     * @param id            String  Format: "yyyy-MM-dd_hhmm"   id of the event
     * @param startDate     String  Format: "yyyy-MM-dd"        start date of the event
     * @param startTime     String  Format: "hh:mm"             start time of the event
     * @param endDate       String  Format: "yyyy-MM-dd"        end date of the event
     * @param endTime       String  Format: "hh:mm"             end time of the event
     * @param name          String                              name of the event
     */
    void addEventToXmlContent(String id, String startDate, String startTime, String endDate, String endTime, String name) {
        if(debug) { Log.d("XMLC", "addEventToXmlContent()"); }
        Document doc = getDocumentOfXmlContent();
        Node nRoot = null;
        if (doc != null) {
            nRoot = doc.getFirstChild();      // get root node ("events")

            // create content (elements) of one event
            Element eStartDate = doc.createElement("start_date");
            eStartDate.appendChild(doc.createTextNode(startDate));
            Element eStartTime = doc.createElement("start_time");
            eStartTime.appendChild(doc.createTextNode(startTime));
            Element eEndDate = doc.createElement("end_date");
            eEndDate.appendChild(doc.createTextNode(endDate));
            Element eEndTime = doc.createElement("end_time");
            eEndTime.appendChild(doc.createTextNode(endTime));
            Element eName = doc.createElement("name");
            eName.appendChild(doc.createTextNode(name));

            // create the parent of the elements (event node) with content (elements)
            Element eEvent = doc.createElement("event");
            eEvent.setAttribute("id", id);
            eEvent.appendChild(eStartDate);
            eEvent.appendChild(eStartTime);
            eEvent.appendChild(eEndDate);
            eEvent.appendChild(eEndTime);
            eEvent.appendChild(eName);
            nRoot.appendChild(eEvent);

            writeChangesToXmlContent(doc);
        }
    }

    void removeEventFromXmlContent(String id) {
        if(debug) { Log.d("XMLC", "removeEventFromXmlContent()"); }
        Document doc = getDocumentOfXmlContent();
        Node nToRemove = null;
        if (doc != null) {
            nToRemove = doc.getElementById(id);
            nToRemove.getParentNode().removeChild( nToRemove );
            writeChangesToXmlContent(doc);
        } else {
            Log.d("XMLC","Node not found");
        }
    }

    /**
     * Removes all past events from the XML content string
     */
    void removeAllPastEventsFromXmlContent() {
        if(debug) { Log.d("XMLC", "removeAllPastEvents()"); }
        Document doc = getDocumentOfXmlContent();
        Node nRoot = null;
        if (doc != null) {
            nRoot = doc.getFirstChild();     // get root node ("events")
            NodeList nEventsList = nRoot.getChildNodes();
            Log.d("XMLC","count node event: " + nEventsList.getLength());
            // traverse the childs of the "events" node (root)
            for (int i=0; i<nEventsList.getLength(); i++) {
                Element eEvent = (Element) nEventsList.item(i);
                String id = eEvent.getAttribute("id");                  // id of the event node
                NodeList nEventList = nEventsList.item(i).getChildNodes();    // get childs of the event node
                String datetimeEnd = nEventList.item(2).getTextContent() + " " + nEventList.item(3).getTextContent() + ":00";
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    Date endDateTime = dateFormater.parse(datetimeEnd);
                    if (new Date().after(endDateTime)) {    // is "endDateTime" after now?
                        // event is past => remove event
                        removeEventFromXmlContent(id);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get the document object of the XML content string
     * @return  Document    XML object
     */
    private Document getDocumentOfXmlContent() {
        if(debug) { Log.d("XMLC", "getDocumentOfXmlContent()"); }
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            InputSource xml = new InputSource(new StringReader( getXmlContent() ));
            Document doc = docBuilder.parse( xml );
            return doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Write the changes of the document to the xml content string
     * @param doc   Document    XML document object
     */
    private void writeChangesToXmlContent(Document doc) {
        if(debug) { Log.d("XMLC", "writeChangesToXmlContentAndSaveToFile(...)"); }
        StringWriter writer = new StringWriter();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            /*
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            */
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            setXmlContent( writer.toString() );
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the XML content in the class to the XML file on the device
     */
    void saveXmlContentToFile() {
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
