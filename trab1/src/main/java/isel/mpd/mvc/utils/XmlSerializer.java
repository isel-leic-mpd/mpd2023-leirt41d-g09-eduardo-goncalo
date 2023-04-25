package isel.mpd.mvc.utils;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.shapes.Circle;
import isel.mpd.mvc.model.shapes.IShape;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static isel.mpd.mvc.utils.ReflectionUtils.getFields;

public class XmlSerializer {
    private static Object getValueFromString(String strVal, Class<?> type) {
        Object objVal = null;
        if (type == int.class) {
            objVal = Integer.parseInt(strVal);
        }
        else if (type == Point.class) {
            strVal = strVal.substring(strVal.indexOf("=") + 1);
            int i = 0;
            while (Character.isDigit(strVal.charAt(i))) ++i;
            int x = Integer.parseInt(strVal.substring(0, i));
            strVal = strVal.substring(strVal.indexOf("=") + 1);
            i = 0;
            while (Character.isDigit(strVal.charAt(i))) ++i;
            int y = Integer.parseInt(strVal.substring(0, i));
            objVal = new Point(x, y);
        }
        else if (type == Color.class) {
            strVal = strVal.substring(strVal.indexOf("=") + 1);
            int i = 0;
            while (Character.isDigit(strVal.charAt(i))) ++i;
            int r = Integer.parseInt(strVal.substring(0, i));
            strVal = strVal.substring(strVal.indexOf("=") + 1);
            i = 0;
            while (Character.isDigit(strVal.charAt(i))) ++i;
            int g = Integer.parseInt(strVal.substring(0, i));
            strVal = strVal.substring(strVal.indexOf("=") + 1);
            i = 0;
            while (Character.isDigit(strVal.charAt(i))) ++i;
            int b = Integer.parseInt(strVal.substring(0, i));
            objVal = new Color(r, g, b);
        }
        return objVal;
    }

    private static void loadFields(Object o, List<Element> xmlFields)
            throws NoSuchFieldException, IllegalAccessException {
        Class<?> objClass = o.getClass();
        for(Element xmlField : xmlFields) {
            Field f = objClass.getDeclaredField(xmlField.getName());
            f.setAccessible(true);
            String isNullVal = xmlField.attributeValue("isNull");
            Object value = null;

            if (!"true".equals(isNullVal)) {
                String strVal = xmlField.getText();
                value = getValueFromString(strVal, f.getType());
            }

            f.set(o, value);
        }
    }

    private static void saveFields(Object o, Element parent, List<Field> fields)
            throws IllegalAccessException {
        for(Field f : fields) {
            Element child = parent.addElement(f.getName());
            child.addAttribute("type", f.getType().getName());
            child.addAttribute("declaredAt", f.getDeclaringClass().getName());
            f.setAccessible(true);
            Object value = f.get(o);
            if (f.getType().isPrimitive()) {
                child.addText(value.toString());
            }
            else {
                if (value == null) {
                    child.addAttribute("isNull", "true");
                }
                else {
                    child.addText(value.toString());
                }
            }
        }
    }

    private static void toXml(Iterable<IShape> shapes, String xmlFile) {
        try(FileWriter fw = new FileWriter(xmlFile)) {
            var root = DocumentHelper.createElement("object");
            var doc = DocumentHelper.createDocument(root);

            String xmlText = doc.asXML();

            fw.append(xmlText.substring(0, xmlText.indexOf("\n") + 1));
            fw.append("<root>");

            for (var s : shapes) {
                if (s == null) {
                    root.addAttribute("isNull", "true");
                } else {
                    Class<?> objClass = s.getClass();
                    root.addAttribute("type", objClass.getName());
                    List<Field> fields = getFields(objClass);
                    saveFields(s, root, fields);
                }
                xmlText = "\n" + "\t" + root.asXML();
                fw.append(xmlText);
                root.clearContent();
            }
            fw.append("\n" + "</root>");
        }
        catch(RuntimeException e) {
            throw e;
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object fromXml(String xmlText) {
        try {
            Document doc = DocumentHelper.parseText(xmlText);
            Element root = doc.getRootElement();
            String typeName = root.attributeValue("type");

            Class<?> objClass = Class.forName(typeName);
            Constructor<?> ctor = objClass.getConstructor();
            Object obj = ctor.newInstance();
            loadFields(obj, root.elements());
            return obj;
        }
        catch(DocumentException | ClassNotFoundException |
              NoSuchMethodException | SecurityException |
              InstantiationException | IllegalAccessException |
              InvocationTargetException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveCanvas(App app) throws FileNotFoundException {
        JFileChooser chooser = new JFileChooser("c:");
        int x = chooser.showDialog(null, "Save");

        if(x == JFileChooser.APPROVE_OPTION) {
            try { //create file
                String filePath = chooser.getSelectedFile().getAbsolutePath();
                File file = new File(filePath);
                file.createNewFile();
                toXml(app.getShapes(), filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void loadCanvas(App app) {
        JFileChooser chooser = new JFileChooser("c:");
        int x = chooser.showDialog(null, "Load");

        if(x == JFileChooser.APPROVE_OPTION) {
            try { //create file
                String filePath = chooser.getSelectedFile().getAbsolutePath();
                File file = new File(filePath);
                try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String input;
                    while((input = br.readLine()) != null) {
                        if (input.startsWith("\t" + "<object"))
                            app.addShape((IShape) fromXml(input));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
