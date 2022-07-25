package com.teo.cateringteo.Service;

import com.teo.cateringteo.BussinessLogic.Observable;
import com.teo.cateringteo.BussinessLogic.*;
import com.teo.cateringteo.CateringApplication;
import com.teo.cateringteo.DataAccess.Serializer;
import javafx.util.Pair;

import javax.security.auth.login.LoginException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/***
 * Class where all the methods from the interface IDeliveryServiceProcessing
 * are implemented and described (all the functionalities for the admin, client and employee)
 */
public class DeliveryService extends Observable implements Serializable, IDeliveryServiceProcessing{
    private HashSet<MenuItem> menu;
    private Map<Order, List<MenuItem>> orders;

    public DeliveryService() {
        menu = new HashSet<>();
        orders = new HashMap<>();
    }

    /**
     * Uses serializator to attempt decoding lines from a given file
     */
    public void loadInFile() {
        Serializer<DeliveryService> serializer = new Serializer<DeliveryService>();
        try {
            serializer.serialize(this, "DeliveryService.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Uses deserializator in order to attempt writing
     * lines from a given file into another (or print them somewhere)
     */
    public void loadFromFile(){
        Serializer<DeliveryService> serializer = new Serializer<DeliveryService>();
        try {
            DeliveryService loaded = serializer.deserialize("DeliveryService.bin");
            this.menu = loaded.menu;
            this.orders = loaded.orders;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importMenu(String filePath) throws IOException {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        lines.remove(0);

        HashSet<MenuItem> newMenu = new HashSet<>();
        for(String line:lines){
            String[] values = line.split(",");
            BaseProduct imported = new BaseProduct();
            imported.setTitle(values[0]);
            imported.setRating(Float.parseFloat(values[1]));
            imported.setCalories(Integer.parseInt(values[2]));
            imported.setProtein(Integer.parseInt(values[3]));
            imported.setFat(Integer.parseInt(values[4]));
            imported.setSodium(Integer.parseInt(values[5]));
            imported.setPrice(Integer.parseInt(values[6]));
            newMenu.add(imported);
        }
        this.menu = newMenu;
        this.loadInFile();
    }

    @Override
    public void addMenuItem(MenuItem menuItem) {
        assert menuItem.getTitle().equals("") : "To add an item, you have to insert a name";
        menu.add(menuItem);
        this.loadInFile();
    }

    @Override
    public void removeMenuItem(MenuItem menuItem) {
        assert menuItem.getTitle().equals(""): "To delete an item, you have to select it";
        menu.remove(menuItem);
        assert true: "The item was deleted";
        this.loadInFile();
    }

    private void makeBill(Order order, List<MenuItem> orderedItems){
        try {
            FileWriter fileWriter = new FileWriter("Bill" + System.currentTimeMillis() / 1000);
            fileWriter.write("Bill:\n" + order.clientName + " bought:\n");
            for(MenuItem menuItem : orderedItems){
                fileWriter.write(menuItem.getTitle() + "\n");
            }
            fileWriter.write("at: " + order.getDate() + " " + order.getTime().getHour() + ":" + order.getTime().getMinute());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addOrder(Order order, List<MenuItem> orderedItems) {
        assert order.getId() <= 0: "Order must exist";
        orders.put(order, new ArrayList<>(orderedItems));
        assert orderedItems.size() == 0: "An order must have at least one product";
        this.loadInFile();
        assert order.getClientName().equals(""): "An order must have a valid client";
        assert order.equals(null): "An order must have at least one product";
        makeBill(order, orderedItems);
        notifyObservers();
    }

    @Override
    public List<MenuItem> filterMenu(Predicate<MenuItem> ... predicates) {
        List<Predicate<MenuItem>> predicatesList = Arrays.asList(predicates);
        return filterMenu(predicatesList);
    }

    @Override
    public List<MenuItem> filterMenu(List<Predicate<MenuItem>> predicates) {
        Stream<MenuItem> menuStream = menu.stream();
        for(Predicate<MenuItem> predicate: predicates){
            menuStream = menuStream.filter(predicate);
        }
        return menuStream.collect(Collectors.toList());
    }

    /**
     * Attempts to log in.
     * @param username the username of the account that the method will look for.
     * @param password the password that should match the entered username.
     * @pre username != null
     * @pre password != null
     * @return the User matching the given credentials
     * @throws LoginException if there is no user matching the credentials.
    */
    @Override
    public User logIn(String username, String password) throws LoginException {
        try {
            List<User> users = new Serializer<List<User>>().deserialize("Accounts.bin");
            Optional<User> optionalUser = users
                    .stream()
                    .filter((u) -> u.getUsername().equals(username) && u.getPassword().equals(password))
                    .findAny();
            if(optionalUser.isEmpty()) throw new LoginException("User not found");
            return  optionalUser.get();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new LoginException("Exception occurred");
        }
    }

    /**
     *  Creates a new entry in the Accounts file for the specified user.
     * @param user the user that will be added in the Accounts.bin file.
     * @apiNote user.getId() is indifferent.
     * @post @result.getUsername().equals(user.getUsername());
     * @post @result.getPassword().equals(user.getPassword());
     * @post @result.getType().equals(user.getType());
     * @post user @nochange
     * @return the user who was added to the file.
     */
    @Override
    public User signUp(User user) throws LoginException {
        try {
            List<User> users = new Serializer<List<User>>().deserialize("Accounts.bin");
            Optional<User> maxIdUser = users.stream().max(Comparator.comparingInt(User::getId));
            int maxId = maxIdUser.isEmpty() ? 1 : maxIdUser.get().getId();
            User addedUser = new User(maxId + 1, user.getUsername(), user.getPassword(), user.getType());
            users.add(addedUser);
            new Serializer<List<User>>().serialize(users,"Accounts.bin");
            return addedUser;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new LoginException("Exception occurred");
        }
    }

    /**
     * Shows the orders made in the given time interval
     * @param startHour the beginning hour
     * @param stopHour  the ending hour
     * @return the orders made in that interval, otherwise null
     */
    @Override
    public List<Map.Entry<Order, List<MenuItem>>> timeIntervalReport(int startHour, int stopHour) {
        return CateringApplication.deliveryService.getOrders().entrySet()
                .stream()
                .filter(mapEntry ->
                        startHour <= mapEntry.getKey().getTime().getHour() &&
                        mapEntry.getKey().getTime().getHour() <= stopHour)
                .collect(Collectors.toList());
    }

    /**
     * Shows the products ordered by n given times
     * @param timesOrdered this is the filter used
     * @return the products ordered at least the given number of times
     */
    @Override
    public List<MenuItem> orderedManyTimes(int timesOrdered) {
        Map<MenuItem, Integer> itemsMap = new ConcurrentHashMap();
        CateringApplication.deliveryService.getOrders().forEach((key, value) -> {
            for (MenuItem mi : value) {
                if (!itemsMap.containsKey(mi)) {
                    itemsMap.put(mi, 1);
                } else {
                    itemsMap.put(mi, itemsMap.get(mi) + 1);
                }
            }
        });
        return itemsMap.entrySet()
                .stream()
                .filter(me -> me.getValue() >= timesOrdered)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pair<String, Integer>> usersWhoFrequentlyOrderALot(int howManyTimes, int howMuch) {
        Map<String, Integer> userMap = new ConcurrentHashMap();
        CateringApplication.deliveryService.getOrders().forEach((key, value) -> {
            int total = 0;
            for (MenuItem mi : value) {
                total += mi.getPrice();
            }
            if(total >= howMuch){
                if(userMap.containsKey(key.getClientName())){
                    userMap.put(key.getClientName(), userMap.get(key.getClientName()) + 1);
                } else {
                    userMap.put(key.getClientName(), 1);
                }
            }
        });
        return userMap.entrySet()
                .stream()
                .filter(me -> me.getValue() >= howManyTimes)
                .map(me -> new Pair<>(me.getKey(), me.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pair<MenuItem, Integer>> perDayStatistic(LocalDate day) {
        Map<MenuItem, Integer> itemsMap = new ConcurrentHashMap();
        CateringApplication.deliveryService.getOrders().forEach((key, value) -> {
            if(!key.getDate().equals(day)){
                return;
            }
            for (MenuItem mi : value) {
                if (!itemsMap.containsKey(mi)) {
                    itemsMap.put(mi, 1);
                } else {
                    itemsMap.put(mi, itemsMap.get(mi) + 1);
                }
            }
        });
        return itemsMap.entrySet()
                .stream()
                .map(e-> new Pair<>(e.getKey(),e.getValue()))
                .collect(Collectors.toList());
    }

    /**setters getters
     *
     */
    public HashSet<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(HashSet<MenuItem> menu) {
        this.menu = menu;
    }

    public Map<Order, List<MenuItem>> getOrders() {
        return orders;
    }

    public void setOrders(Map<Order, List<MenuItem>> orders) {
        this.orders = orders;
    }

    /**
     * toString overwritten method
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("DeliveryService:\nmenu:\n");
        for(MenuItem menuItem: menu){
            stringBuilder.append(menuItem.toString());
            stringBuilder.append("\n");
        }
        stringBuilder.append("Orders:\ncoming soon");
        return stringBuilder.toString();
    }
}
