import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException{
        List<Restaurant> restaurants=getRestaurants();
        List<String> restaurantNames=restaurants.stream().map(e->e.getName()).collect(Collectors.toList());
        if(restaurantNames.contains(restaurantName)){
            return restaurants.get(restaurantNames.indexOf(restaurantName));
        }
        else{
            throw new restaurantNotFoundException(restaurantName);
        }

    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public int getOrderValue(List<Item> selectedItems){

        int ordervalue=0;
        for(Item item:selectedItems){
            ordervalue=ordervalue+item.getPrice();
        }

        return ordervalue;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}
