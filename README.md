# Food_Delivery_Management_System
## Project objective
It is required to create an application for a catering company, having a role based access control system and a way to process information from files. The roles consist of customer, employee and administrator, with the employee having a notification system for when a customer places an order. The administrator can also generate reports and modify the products in the files.
## Implementation
### Observer Pattern
It is an architectural pattern that contains an Observable (resources that can change) and one or more Observers that are notified of changes made to the Observable. In this case, catering employees must be notified when a customer orders something, so they know to prepare the food for the order.
### Composite Pattern
It is an architectural pattern that allows a group of objects to be treated as a single instance. It is based on subclasses that inherit a base class that is either an abstract class or an interface.
### Used data structures
##### Sets
Sets are collections that do not accept equal objects (which equality is defined by the .equals() or compareTo() method). In this project, the uniqueness of the name of the products in stock and of an order had to be ensured, as well as the name of the users in order not to create confusion when logging in.
#### Hashing and Mapping
This project required the use of scatter tables and mapping of certain objects. A prime example would be even keeping orders in memory that, instead of having a list of products as an instance variable, were mapped to an object of type Order.
## GUI
### Log in 
This is the LogIn window, in which the user must write the name and the password. There are 4 possible windows that can be open after that: an admin window, an employee window, or a client window. In case the sign up button is pressed,  a new similar window for creating a client account will appear.   
#### ![image](https://user-images.githubusercontent.com/79631600/226594331-7d9f4780-003d-40f0-a9f8-ac3b15aadeca.png)
### Menu Window
The admin window will appear once logged in, which has all the requested requirements: add/modify/delete/search products and view reports. Once the Reports button is pressed, a new window for the reports will open and the admin will can read all the reports wanted.
#### ![image](https://user-images.githubusercontent.com/79631600/226594560-181767ac-1e8c-4fce-aa59-420996474d86.png)
### Order reports window
#### ![image](https://user-images.githubusercontent.com/79631600/226594777-b29ef496-84ad-4909-82ac-04c7bcf278df.png)
### Order window
#### ![image](https://user-images.githubusercontent.com/79631600/226594896-a2c62fc5-5b7f-4f77-9423-37d05931a8a1.png)
## Conclusions 
In conclusion, this application  provides the user with an intelligible way to manage the products from the menu or to generate some reports if he is an admin, to make an order if he is a client and to see the made orders in case the user is an employee. 
