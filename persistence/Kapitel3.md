# Reading Group javaee7

## Pro JPA in Java EE 8 Kapitel 3

### Session Beans
* Stateful  de.akdb.oesio.persistence.webshop.ShoppingCart
* Stateless de.akdb.oesio.persistence.webshop.ProductService
* Singleton de.akdb.oesio.persistence.webshop.OrderCounter

### Resourcen Annotationen
* @PersistenceContext   de.akdb.oesio.persistence.webshop.ProductService
* @PersistenceUnit      de.akdb.oesio.persistence.webshop.ShoppingCart
* @Resource             de.akdb.oesio.persistence.webshop.ShoppingCart

### Scopes
* Request
* Session
* Application
* Conversation: Über eine Menge von JSF Requests
* Transaction:  Lebenszeit der JTA Tranaction
* Depentent

Producer Methode 

### Transaction Management
* Container-Managed Transactions
* Bean-Managed Transactions

### Begriffe
* Java Transaction API (JTA)
* Java Connector Architecture (JCA)
* @TransactionManagement