package se.mdh.dva232.project.shutup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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


class Collision {
    private Boolean collision;
    private Integer collisionCode;
    private String collisionMessage;
    private String collisionEventId;

    /**
     * Constructor
     */
    Collision() {
        collision = false;
        collisionCode = null;
        collisionMessage = null;
        collisionEventId = null;
    }

    Boolean getCollision() { return collision; }
    void setCollision(Boolean value) { collision = value; }
    Integer getCollisionCode() { return collisionCode; }
    void setCollisionCode(Integer value) { collisionCode = value; }
    String getCollisionMessage() { return collisionMessage; }
    void setCollisionMessage(String value) { collisionMessage = value; }
    String getCollisionEventId() { return collisionEventId; }
    void setCollisionEventId(String value) { collisionEventId = value; }
}

/**
 * That class includes all methods for handling with the XML content string and the XML file in the storage of the device.
 * @author Sylvio Ujvari
 */
class XmlController {
    private Context context;
    private String xmlFilename = "events.xml";
    private String xmlContent = null;
    private Boolean debug = false;       // true: with Log.d output; false: without Log.d output
    private Collision currentCollision = null;

    /**
     * Constructor for these class
     * @param c     Context     context of that application for access to the open file functions
     */
    XmlController(Context c) {
        if(debug) { Log.d("XMLC", "constructor"); }
        context = c;
    }

    //
    // public methods
    //

    public void getAllEventsFromTheXmlContent() {
        if(debug) { Log.d("XMLC", "getAllEventsFromTheXmlContent()"); }
        try {
            XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlParser = xmlFactory.newPullParser();
            xmlParser.setInput( new StringReader( getXmlContent() ));

            while(xmlParser.next() != XmlPullParser.END_DOCUMENT) {
                if ( xmlParser.getName().equals("event") ) {
                    if ( xmlParser.getEventType() == XmlPullParser.START_TAG ) {

                    }
                    if ( xmlParser.getEventType() == XmlPullParser.END_TAG ) {

                    }
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    Event getFirstUpcommingEvent() {
        if(debug) { Log.d("XMLC", "loadFirstUpcommingEvent()"); }
        String eventId=null, startDateString=null, startTimeString=null, endDateString=null, endTimeString=null;
        try {
            XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlParser = xmlFactory.newPullParser();
            xmlParser.setInput( new StringReader( getXmlContent() ));

            Boolean found = false;
            while(xmlParser.next() != XmlPullParser.END_DOCUMENT && !found) {
                if ( xmlParser.getEventType() == XmlPullParser.START_TAG && xmlParser.getName().equals("event")) {

                    eventId = xmlParser.getAttributeValue("","id");
                } else if ( xmlParser.getEventType() == XmlPullParser.START_TAG && xmlParser.getName().equals("start_date")) {
                    xmlParser.next();
                    startDateString = xmlParser.getText();
                } else if ( xmlParser.getEventType() == XmlPullParser.START_TAG && xmlParser.getName().equals("start_time")) {
                    xmlParser.next();
                    startTimeString = xmlParser.getText();
                } else if ( xmlParser.getEventType() == XmlPullParser.START_TAG && xmlParser.getName().equals("end_date")) {
                    xmlParser.next();
                    endDateString = xmlParser.getText();
                } else if ( xmlParser.getEventType() == XmlPullParser.START_TAG && xmlParser.getName().equals("end_time")) {
                    xmlParser.next();
                    endTimeString = xmlParser.getText();
                } else if ( xmlParser.getEventType() == XmlPullParser.END_TAG && xmlParser.getName().equals("event")) {
                    found = true;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Event(eventId, startDateString, startTimeString, endDateString, endTimeString);
    }

    /**
     * Check for the first collision by given new event
     * @param newStartDate     String   Format: "yyyy-MM-dd"        start date of the event
     * @param newStartTime     String   Format: "hh:mm:ss"          start time of the event
     * @param newEndDate       String   Format: "yyyy-MM-dd"        end date of the event
     * @param newEndTime       String   Format: "hh:mm:ss"          end time of the event
     * @return  Boolean     true: collision found; false: no collision found
     */
    Boolean isCollisionByNewEvent(String newStartDate, String newStartTime, String newEndDate, String newEndTime) {
        if(debug) { Log.d("XMLC", "isCollisionByNewEvent("+newStartDate+", "+newStartTime+", "+newEndDate+", "+newEndTime+")"); }
        Boolean collision = false;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newStartDateTime = null;
        Date newEndDateTime = null;
        String startDateString = null;
        Date startDateTime = null;
        String endDateString = null;
        Date endDateTime = null;

        // create compare values from parameters
        try {
            newStartDateTime = sdf.parse(newStartDate + " " + newStartTime);
            newEndDateTime = sdf.parse(newEndDate + " " + newEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlParser = xmlFactory.newPullParser();
            xmlParser.setInput( new StringReader( getXmlContent() ));

            createCurrentCollisionAndSave();    // init of the Collision Struct
            String eventId = null;

            while(xmlParser.next() != XmlPullParser.END_DOCUMENT && !collision) {
                if ( xmlParser.getEventType() == XmlPullParser.START_TAG && xmlParser.getName().equals("event")) {
                    eventId = xmlParser.getAttributeValue("","id");
                } else if ( xmlParser.getEventType() == XmlPullParser.START_TAG && xmlParser.getName().equals("start_date")) {
                    xmlParser.next();
                    startDateString = xmlParser.getText();
                } else if ( xmlParser.getEventType() == XmlPullParser.START_TAG && xmlParser.getName().equals("start_time")) {
                    xmlParser.next();
                    startDateTime = sdf.parse(startDateString + " " + xmlParser.getText());
                } else if ( xmlParser.getEventType() == XmlPullParser.START_TAG && xmlParser.getName().equals("end_date")) {
                    xmlParser.next();
                    endDateString = xmlParser.getText();
                } else if ( xmlParser.getEventType() == XmlPullParser.START_TAG && xmlParser.getName().equals("end_time")) {
                    xmlParser.next();
                    endDateTime = sdf.parse(endDateString + " " + xmlParser.getText());
                } else if ( xmlParser.getEventType() == XmlPullParser.END_TAG && xmlParser.getName().equals("event")) {
                    checkForCollision(startDateTime, endDateTime, newStartDateTime, newEndDateTime);

                    Log.d("XMLC", "Collision Sruct: getCollision(): " + getCurrentCollision().getCollision());
                    if( getCurrentCollision().getCollision() ) {
                        Log.d("XMLC", "collision detected -> eventId: "+ getCurrentCollision().getCollisionEventId()+ " // code: "+getCurrentCollision().getCollisionCode());
                        getCurrentCollision().setCollisionEventId(eventId);
                    }
                    collision = getCurrentCollision().getCollision();
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return collision;
    }


    /**
     * Adds an event in order to the XML content string
     * @param id            String  Format: "yyyy-MM-dd_hhmm"   id of the event
     * @param startDate     String  Format: "yyyy-MM-dd"        start date of the event
     * @param startTime     String  Format: "hh:mm:ss"          start time of the event
     * @param endDate       String  Format: "yyyy-MM-dd"        end date of the event
     * @param endTime       String  Format: "hh:mm:ss"          end time of the event
     * @param name          String                              name of the event
     * @return   Integer     Error-Code
     */
    Integer addEventToXmlContent(String id, String startDate, String startTime, String endDate, String endTime, String name) {
        if(debug) { Log.d("XMLC", "addEventToXmlContent()"); }
        Integer errorCode = 0;
        Document doc = getDocumentOfXmlContent();
        if (doc != null) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // create compare values from parameters
            Date newStartDateTime = null;
            Date newEndDateTime = null;
            try {
                newStartDateTime = sdf.parse(startDate + " " + startTime);
                newEndDateTime = sdf.parse(endDate + " " + endTime);
            } catch (ParseException e) {
                e.printStackTrace();
                errorCode = 1;
            }

            if ( newStartDateTime == null || newEndDateTime == null || !newStartDateTime.before(newEndDateTime) ) {
                errorCode = 10;
            } else {
                Node nRoot = doc.getFirstChild();      // get root node ("events")

                // find the right position for inserting the new event in the XML content string
                NodeList nRootList = nRoot.getChildNodes();
                Node nodeInsertBefore = null;
                Boolean insertAtEnd = false;
                Boolean found = false;
                if (nRootList.getLength() == 0) {
                    insertAtEnd = true;
                } else {
                    for (int i = 0; !found && (i < nRootList.getLength()); i++) {
                        NodeList event = nRootList.item(i).getChildNodes();
                        Date startDateTime = null;
                        Date endDateTime = null;
                        try {
                            startDateTime = sdf.parse(event.item(0).getTextContent() + " " + event.item(1).getTextContent());
                            endDateTime = sdf.parse(event.item(2).getTextContent() + " " + event.item(3).getTextContent());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            errorCode = 2;
                        }

                        /*
                        Log.d("XMLC"," startDateTime: " + startDateTime.toString());
                        Log.d("XMLC"," endDateTime: " + endDateTime.toString());
                        Log.d("XMLC"," newStartDateTime: " + newStartDateTime.toString());
                        Log.d("XMLC"," newEndDateTime: " + newEndDateTime.toString());
                        */

                        if (startDateTime == null || endDateTime == null) {
                            errorCode = 11;
                        } else {
                            /*
                             * current event for check: -----------------------|----------------|-------------------->
                             * new event:               ----{------------------]------------------------------------->
                             * result: insert new event before current event
                             */
                            if (newStartDateTime.before(startDateTime) && (newEndDateTime.equals(startDateTime) || newEndDateTime.before(startDateTime))) {
                                nodeInsertBefore = nRootList.item(i);
                                found = true;
                            }
                            /*
                             * current event for check: -----------------------|----------------|-------------------->
                             * new event:               ----------------------------------------[---------------}----->
                             * result: go to the next event -> if the current event is the last event then insert at the end
                             */
                            else if ((newStartDateTime.equals(endDateTime) || newStartDateTime.after(endDateTime)) && newEndDateTime.after(endDateTime)) {
                                if (i == (nRootList.getLength() - 1)) {
                                    insertAtEnd = true;
                                }
                            } else {
                                errorCode = 20;
                            }
                        }
                    }
                }

                if (errorCode == 0) {
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

                    if (insertAtEnd) {
                        nRoot.appendChild(eEvent);
                    } else {
                        nRoot.insertBefore(eEvent, nodeInsertBefore);
                    }

                    writeChangesToXmlContent(doc);
                }

            }
        }
        return errorCode;
    }

    /**
     * Removes one event by given id from the XML content string
     * @param id    String  Format: "yyyy-MM-dd_hhmm"
     */
    void removeEventFromXmlContent(String id) {
        if(debug) { Log.d("XMLC", "removeEventFromXmlContent("+id+")"); }
        Document doc = getDocumentOfXmlContent();
        if (doc != null) {
            Node nToRemove = doc.getElementById(id);
            nToRemove.getParentNode().removeChild( nToRemove );
            writeChangesToXmlContent(doc);
        }
    }

    /**
     * Removes all past events from the XML content string
     * @return      Boolean     true: deletions; false: no changes
     */
    Boolean removeAllPastEventsFromXmlContent() {
        if(debug) { Log.d("XMLC", "removeAllPastEventsFromXmlContent()"); }
        Boolean deletion = false;
        Document doc = getDocumentOfXmlContent();
        if (doc != null) {
            Node nRoot = doc.getFirstChild();     // get root node ("events")
            NodeList nRootList = nRoot.getChildNodes();
            // traverse the childs of the "events" node (root)
            for (int i=0; i<nRootList.getLength(); i++) {
                Element eEvent = (Element) nRootList.item(i);
                String id = eEvent.getAttribute("id");                  // id of the event node
                NodeList nEventList = nRootList.item(i).getChildNodes();    // get childs of the event node
                String datetimeEnd = nEventList.item(2).getTextContent() + " " + nEventList.item(3).getTextContent();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date endDateTime = dateFormater.parse(datetimeEnd);
                    Date now = Calendar.getInstance().getTime();
                    if (now.after(endDateTime)) {    // is "endDateTime" after now?
                        // event is past => remove event
                        Log.d("XMLC","removeAllPastEventsFromXmlContent() -> IF("+now.toString()+" is after "+endDateTime.toString()+") is TRUE -> remove event with id " + id);
                        nRoot.removeChild( nRootList.item(i) );     // removeEventFromXmlContent(id);  // TODO: replace
                        deletion = true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (deletion) {
                writeChangesToXmlContent(doc);
            }
        }
        logCurrentXmlContent();
        return deletion;
    }


    //
    //  Collision
    //

    /**
     * set the current collision in the class
     * @param c     Collision-Object
     */
    private void setCurrentCollision(Collision c) {
        currentCollision = c;
    }

    /**
     * get the current collision in the class
     * @return      Collision       Struct
     */
    private Collision getCurrentCollision() {
        return currentCollision;
    }

    /**
     * delete the content of the current collision
     */
    private void deleteCurrentCollision() {
        currentCollision = null;
    }

    /**
     * create the current collision and save it in the class
     */
    private void createCurrentCollisionAndSave() {
        Collision c = new Collision();
        setCurrentCollision(c);
    }


    /**
     * Conditions for collision checking
     * @param startDateTime             Date                    start datetime of the current checking event
     * @param endDateTime               Date                    end datetime of the current checking event
     * @param newStartDateTime          Date                    start datetime of the new event
     * @param newEndDateTime            Date                    end datetime of the new event
     */
    private void checkForCollision(Date startDateTime, Date endDateTime, Date newStartDateTime, Date newEndDateTime) {
        if (!debug) { Log.d("XMLC", "checkForCollision(...)" ); }
        /*
         * current event for check: -----------------------|----------------|-------------------->
         * new event:               -----------{--------------------}---------------------------->
         * collision code: 10
         */
        if (startDateTime.before(newEndDateTime) && (newEndDateTime.before(endDateTime) || newEndDateTime.equals(endDateTime))) {
            getCurrentCollision().setCollision(true);
            getCurrentCollision().setCollisionCode(10);
            getCurrentCollision().setCollisionMessage("The end of the new event is in collision");
            // from now: modify the event which is in collision to the new event by set new start for the event -> startDateTime = newEndDateTime
        }
        /*
         * current event for check: -----------------------|----------------|-------------------->
         * new event:               --------------------------------{---------------}------------>
         * collision code: 20
         */
        else if ((startDateTime.before(newStartDateTime) || startDateTime.equals(newStartDateTime)) && newStartDateTime.before(endDateTime)) {
            getCurrentCollision().setCollision(true);
            getCurrentCollision().setCollisionCode(20);
            getCurrentCollision().setCollisionMessage("The start of the new event is in collision");
            // from now: that should not be happen by silent from now
        }
        /*
         * current event for check: -----------------------|----------------|-------------------->
         * new event:               ---------------{-------------------------------}------------->
         * collision code: 30
         */
        else if ((newStartDateTime.before(startDateTime) || newStartDateTime.equals(startDateTime)) && (endDateTime.equals(newEndDateTime) || endDateTime.before(newEndDateTime))) {
            getCurrentCollision().setCollision(true);
            getCurrentCollision().setCollisionCode(30);
            getCurrentCollision().setCollisionMessage("The new event overlapped another event");
            // from now: delete the event which is in collision to the new event, but check again for collision. maybe there is another saved event
        }
        /*
         * current event for check: -----------------------|----------------|-------------------->
         * new event:               --------------------------{----------}----------------------->
         * collision code: 40
         */
        else if (startDateTime.before(newStartDateTime) && newEndDateTime.before(endDateTime)) {
            getCurrentCollision().setCollision(true);
            getCurrentCollision().setCollisionCode(40);
            getCurrentCollision().setCollisionMessage("An existing event overlapped the new event");
            // from now: that should not be happen by silent from now
        }
    }

    /**
     * Called only by a silent mode from now
     * Modifies saved events which are in collision with the new event
     * Conditions for call: Fragment0 -> EC.activateSilentModeFromNow(..) -> XC.isCollisionByNewEvent(..) is TRUE
     * The "currentCollision" Struct contains the first event-id of the event which is in collision
     * Should only called if the collision error code is 10 or 30
     * @param newStartDate      String      Format: "yyyy-MM-dd"
     * @param newStartTime      String      Format: "HH:mm:ss"
     * @param newEndDate        String      Format: "yyyy-MM-dd
     * @param newEndTime        String      Format: "HH:mm:ss"
     */
    void modifySavedEventsRegardingCollisionInXmlContent(String newStartDate, String newStartTime, String newEndDate, String newEndTime) {
        if (!debug) { Log.d("XMLC", "modifySavedEventsRegardingCollisionInXmlContent("+newStartDate+", "+newStartTime+", "+newEndDate+", "+newEndTime+")"); }
        if ( getCurrentCollision().getCollision() ) {
            Log.d("XMLC_COLLISION","collision: TRUE // collisionEventId: "+getCurrentCollision().getCollisionEventId()+" // collisionCode: "+getCurrentCollision().getCollisionCode()+" // collisionMessage: " + getCurrentCollision().getCollisionMessage() );
            if (getCurrentCollision().getCollisionCode() == 10) {
                /*
                 * current event for check: -----------------------|----------------|-------------------->
                 * new event:               -----------{--------------------}---------------------------->
                 * collision code: 10
                 */
                logCurrentXmlContent();
                Document doc = getDocumentOfXmlContent();
                if(doc != null) {
                    Node node = doc.getElementById(getCurrentCollision().getCollisionEventId());
                    // TODO: change id to new id
                    NodeList nodeList = node.getChildNodes();
                    nodeList.item(0).setTextContent(newEndDate);    // set start date of saved event to end date of new event
                    nodeList.item(1).setTextContent(newEndTime);    // set start time of saved event to end time of new event
                    writeChangesToXmlContent(doc);      // write to XML content
                    logCurrentXmlContent();
                    if (!isCollisionByNewEvent(newStartDate, newStartTime, newEndDate, newEndTime)) {
                        Log.d("XMLC", "[COLLISION] --> modify success");
                    } else {
                        Log.d("XMLC", "[COLLISION] --> modify not success");
                    }
                }
            } else if (getCurrentCollision().getCollisionCode() == 30) {
                /*
                 * current event for check: -----------------------|----------------|-------------------->
                 * new event:               ---------------{-------------------------------}------------->
                 * collision code: 30
                 */
                                /*
                <event id="2019-01-12_194833">
                    <start_date>2019-01-12</start_date>
                    <start_time>19:48:33</start_time>
                    <end_date>2019-01-12</end_date>
                    <end_time>19:50:33</end_time>
                    <name>Current</name>
                </event>
                 */
                logCurrentXmlContent();
                removeEventFromXmlContent( getCurrentCollision().getCollisionEventId() );
                logCurrentXmlContent();
                while ( isCollisionByNewEvent(newStartDate, newStartTime, newEndDate, newEndTime) ) {
                    modifySavedEventsRegardingCollisionInXmlContent(newStartDate, newStartTime, newEndDate, newEndTime);
                }

            } else {
                Log.d("XMLC", "modifySavedEventsRegardingCollisionInXmlContent(...) -> collision code not equal 10 or 30");
            }
        }
    }


    //
    //  XML content
    //

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
        XmlSerializer xmlSerializer = Xml.newSerializer();
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
     * Get the document object of the XML content string
     * @return  Document    XML object
     */
    private Document getDocumentOfXmlContent() {
        if(debug) { Log.d("XMLC", "getDocumentOfXmlContent()"); }
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            InputSource xml = new InputSource(new StringReader( getXmlContent() ));
            return docBuilder.parse( xml );
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
     * Writes the current XML content string to the Log
     */
    void logCurrentXmlContent() {
        Log.d("XMLC", "XML content: " + getXmlContent() );
    }


    //
    //  XML file
    //

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
