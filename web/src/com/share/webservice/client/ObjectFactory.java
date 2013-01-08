
package com.share.webservice.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.share.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FindUsers_QNAME = new QName("http://webservice.share.com/", "findUsers");
    private final static QName _FindUsersResponse_QNAME = new QName("http://webservice.share.com/", "findUsersResponse");
    private final static QName _Say_QNAME = new QName("http://webservice.share.com/", "say");
    private final static QName _SayUserName_QNAME = new QName("http://webservice.share.com/", "sayUserName");
    private final static QName _SayUserNameResponse_QNAME = new QName("http://webservice.share.com/", "sayUserNameResponse");
    private final static QName _SayResponse_QNAME = new QName("http://webservice.share.com/", "sayResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.share.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SayResponse }
     * 
     */
    public SayResponse createSayResponse() {
        return new SayResponse();
    }

    /**
     * Create an instance of {@link SayUserNameResponse }
     * 
     */
    public SayUserNameResponse createSayUserNameResponse() {
        return new SayUserNameResponse();
    }

    /**
     * Create an instance of {@link SayUserName }
     * 
     */
    public SayUserName createSayUserName() {
        return new SayUserName();
    }

    /**
     * Create an instance of {@link Say }
     * 
     */
    public Say createSay() {
        return new Say();
    }

    /**
     * Create an instance of {@link FindUsersResponse }
     * 
     */
    public FindUsersResponse createFindUsersResponse() {
        return new FindUsersResponse();
    }

    /**
     * Create an instance of {@link FindUsers }
     * 
     */
    public FindUsers createFindUsers() {
        return new FindUsers();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link ListObject }
     * 
     */
    public ListObject createListObject() {
        return new ListObject();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUsers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.share.com/", name = "findUsers")
    public JAXBElement<FindUsers> createFindUsers(FindUsers value) {
        return new JAXBElement<FindUsers>(_FindUsers_QNAME, FindUsers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.share.com/", name = "findUsersResponse")
    public JAXBElement<FindUsersResponse> createFindUsersResponse(FindUsersResponse value) {
        return new JAXBElement<FindUsersResponse>(_FindUsersResponse_QNAME, FindUsersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Say }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.share.com/", name = "say")
    public JAXBElement<Say> createSay(Say value) {
        return new JAXBElement<Say>(_Say_QNAME, Say.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayUserName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.share.com/", name = "sayUserName")
    public JAXBElement<SayUserName> createSayUserName(SayUserName value) {
        return new JAXBElement<SayUserName>(_SayUserName_QNAME, SayUserName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayUserNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.share.com/", name = "sayUserNameResponse")
    public JAXBElement<SayUserNameResponse> createSayUserNameResponse(SayUserNameResponse value) {
        return new JAXBElement<SayUserNameResponse>(_SayUserNameResponse_QNAME, SayUserNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.share.com/", name = "sayResponse")
    public JAXBElement<SayResponse> createSayResponse(SayResponse value) {
        return new JAXBElement<SayResponse>(_SayResponse_QNAME, SayResponse.class, null, value);
    }

}
