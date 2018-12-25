package se.mdh.dva232.project.shutup;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import org.xmlpull.v1.XmlSerializer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

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

    public void resetXmlContent() {
        if(debug) { Log.d("XMLC","resetXmlContent()"); }
        setXmlContent( null );
    }

    private void setXmlContent(String content) {
        if(debug) { Log.d("XMLC","setXmlContent(...)"); }
        xmlContent = content;
    }

    public String getXmlContent() {
        if(debug) { Log.d("XMLC","getXmlContent()"); }
        return xmlContent;
    }

    public Boolean isXmlContent() {
        if(debug) { Log.d("XMLC","isXmlContent()"); }
        if( (getXmlContent() != null) && (!getXmlContent().isEmpty()) && (getXmlContent().length() > 0) ) {
            return true;
        } else {
            return false;
        }
    }

    public void createXml() {
        if(debug) { Log.d("XMLC", "createXml()"); }
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

    public void saveXmlToFile() {
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

    public void readXmlFile() {
        if(debug) { Log.d("XMLC", "readXmlFile()"); }
        try {
            FileInputStream xmlFile = context.openFileInput(xmlFilename);
            InputStreamReader inputStreamReader = new InputStreamReader(xmlFile);
            char[] inputBuffer = new char[xmlFile.available()];
            inputStreamReader.read(inputBuffer);
            setXmlContent( new String(inputBuffer) );
            inputStreamReader.close();
            xmlFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
