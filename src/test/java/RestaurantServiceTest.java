import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE


    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE
        //ARRANGE
        List<Restaurant> restaurantList=new ArrayList<>();
        restaurant=new Restaurant("punjabi dhaba","bangalore",LocalTime.of(9,30),LocalTime.of(22,00));
        restaurantList.add(restaurant);
        //ACT
        RestaurantService mockedService= Mockito.spy(service);
        Mockito.when(mockedService.getRestaurants()).thenReturn(restaurantList);
        String restaurantName=mockedService.findRestaurantByName("punjabi dhaba").getName();
        //ASSERT
        Assertions.assertEquals("punjabi dhaba",restaurantName);

    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE
        //ARRANGE
        List<Restaurant> restaurantList=new ArrayList<>();
        restaurant=new Restaurant("punjabi dhaba","bangalore",LocalTime.of(9,30),LocalTime.of(22,00));
        restaurantList.add(restaurant);

        //ACT
        RestaurantService mockedService= Mockito.spy(service);
        Mockito.when(mockedService.getRestaurants()).thenReturn(restaurantList);

        //ASSERT
        Assertions.assertThrows(restaurantNotFoundException.class, ()-> mockedService.findRestaurantByName("punjabi dhaba new"));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<CALCUALTE ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void given_the_list_of_menu_items_should_return_the_total_order_cost(){
        //ARRANGE
        restaurant=new Restaurant("punjabi dhaba","bangalore",LocalTime.of(9,30),LocalTime.of(22,00));
        restaurant.addToMenu("coffee",10);
        restaurant.addToMenu("tea",10);
        restaurant.addToMenu("paratha",20);
        List<Item> items=restaurant.getMenu();
        //ACT ASSERT
        Assertions.assertEquals(40,service.getOrderValue(items));
    }

    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>
}