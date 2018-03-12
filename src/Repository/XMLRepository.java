package Repository;

import Domain.BaseEntity;
import Domain.Book;
import Domain.Client;
import Domain.Validators.Validator;
import Exceptions.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public class XMLRepository<T extends BaseEntity<Integer>> extends  InMemoryRepository<Integer,T>{
    private String filename;

    public XMLRepository(Validator<T> validator, String filename) {
        super(validator);
        this.filename = filename;

        try {
            loadData();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void loadData() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory =  DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse(filename);
        Element root = xmlDoc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int index = 0; index < childNodes.getLength(); index++){
            Node child = childNodes.item(index);
            if(child instanceof Element) {
                Element entity = (Element) child;
                T ent;
                if(entity.getTagName()=="book") {
                    String id = getTextByTagName(entity,"id");
                    String title = getTextByTagName(entity, "title");
                    String author = getTextByTagName(entity, "author");
                    String price = getTextByTagName(entity, "price");
                    String stock = getTextByTagName(entity, "stock");

                    Book book = new Book(title, author, Integer.parseInt(price), Integer.parseInt(stock));
                    book.setId(Integer.parseInt(id));
                    ent = (T)book;
                    try { super.save(ent); } catch (ValidatorException v){v.printStackTrace();}
                }
                if(entity.getTagName()=="client")
                {
                    String id = getTextByTagName(entity,"id");
                    String name = getTextByTagName(entity, "name");
                    String amountSpent = getTextByTagName(entity,"amountSpent");

                    Client client = new Client(name,Integer.parseInt(amountSpent));
                    client.setId(Integer.parseInt(id));
                    ent = (T)client;
                    try { super.save(ent); } catch (ValidatorException v){v.printStackTrace();}
                }
            }
        }
    }

    private String getTextByTagName(Element entity, String title) {
        return entity.getElementsByTagName(title).item(0).getTextContent();
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        Optional<T> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        try {
            saveToFile(entity);
        }
        catch (Exception e)
        {e.printStackTrace();}
        return Optional.empty();
    }

    private static void appendChildToElement(Document doc, Element parent, String tag, String value){
        Element element = doc.createElement(tag);
        element.setTextContent(value);
        parent.appendChild(element);
    }

    private void saveToFile(T entity) throws Exception{
        DocumentBuilderFactory dbFactory =  DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse(filename);
        Element root = xmlDoc.getDocumentElement();

        if(entity instanceof Client)
        {
            Element client = xmlDoc.createElement("client");
            appendChildToElement(xmlDoc, client, "id", String.valueOf(entity.getId()));
            appendChildToElement(xmlDoc, client, "name", ((Client) entity).getName());
            appendChildToElement(xmlDoc, client, "amountSpent", String.valueOf(((Client) entity).getAmountSpent()));
            root.appendChild(client);
        }

        if(entity instanceof Book)
        {
            Element book = xmlDoc.createElement("book");
            appendChildToElement(xmlDoc, book, "id", String.valueOf(entity.getId()));
            appendChildToElement(xmlDoc, book, "title", ((Book) entity).getTitle());
            appendChildToElement(xmlDoc, book, "author", ((Book) entity).getAuthor());
            appendChildToElement(xmlDoc, book, "price", String.valueOf(((Book) entity).getPrice()));
            appendChildToElement(xmlDoc, book, "stock", String.valueOf(((Book) entity).getStock()));

            root.appendChild(book);
        }

        Transformer tr= TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.METHOD, "xml");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        tr.transform(new DOMSource(root),new StreamResult(new FileOutputStream(filename)));
    }
}
