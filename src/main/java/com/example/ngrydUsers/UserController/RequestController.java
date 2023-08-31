package com.example.ngrydUsers.UserController;

import com.example.ngrydUsers.Logs.LogWriter;
import com.example.ngrydUsers.UserModel.Users;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/")
public class RequestController {
    private final String userName = "root";
    private final String password = "";
    private final String url = "jdbc:mysql://127.0.0.1:3306/users_db";

    @GetMapping("/users")
    public List<Users> getAllUsers(){
        List<Users> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement()){
            ResultSet resultSet= statement.executeQuery("SELECT * FROM nygrydusers");
            while( resultSet.next() ){
                list.add(new Users(resultSet.getString("name"), Integer.parseInt(resultSet.getString("age")), Double.parseDouble(resultSet.getString("accountBalance")), resultSet.getString("location")) );
            }
            resultSet.close();
            return list;
        } catch( SQLException exception ){
            System.out.println(exception.getMessage());
            return new ArrayList<>();
        }
    }

    @GetMapping("/user/{user}")
    public Users getUser(@PathVariable String user){
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             Statement statement = connection.createStatement()) {
            String query = String.format("Select * from nygrydusers where name=\"%s\"", user.toLowerCase());
            ResultSet resultSet = statement.executeQuery(query);
            Users person = null;
            while (resultSet.next()) {
                String nam = resultSet.getString(1);
                int age = Integer.parseInt(resultSet.getString(2));
                String accountBalance = resultSet.getString(3);
                String location = resultSet.getString(4);
                person= new Users(nam, age, Double.parseDouble(accountBalance), location);
            }
            return person;
        } catch (SQLException e) {
            System.out.println("This error occurred : " + e.getMessage());
            return new Users(user);
        }
    }

    @GetMapping("/sorted_users")
    public List<Users> getSortedUsers(){
        List<Users> list = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement()){
            String query = "SELECT * FROM nygrydusers";
            ResultSet resultSet= statement.executeQuery(query);
            while( resultSet.next() ){
                list.add(new Users(resultSet.getString("name"), Integer.parseInt(resultSet.getString("age")),
                        Double.parseDouble(resultSet.getString("accountBalance")), resultSet.getString("location")) );
            }
            resultSet.close();
            return list.stream().sorted((u1, u2)-> u1.getName().compareTo(u2.getName())).toList();
        } catch( SQLException exception ){
            System.out.println(exception.getMessage());
            return new ArrayList<>();
        }
    }

    @PostMapping("/user")
    public String createUser(@RequestBody Users user) throws IOException {
        try(Connection connection= DriverManager.getConnection(url, userName, password);
            Statement statement= connection.createStatement()){
            String query = String.format("Insert into nygrydusers(name, age, accountBalance, location)Values(\"%s\",\"%s\",\"%s\",\"%s\")", user.getName(), user.getAge(), user.getAccountBalance(), user.getLocation());
            statement.execute(query);
            String log = "User with "+ user.getName() + " has been created\n";
            LogWriter.writeFile(log);
            return String.format("User with %s has been created", user.getName());
        }
        catch(SQLException e){
            String log = String.format("User with %s was not created because of\n Error: %s\n", user.getName(), e.getMessage());
            LogWriter.writeFile(log);
            return String.format("User with %s was not created", user.getName());
        } catch (IOException e) {
            return String.format("Write error Occured: "+ e.getMessage());
        }
    }
}
