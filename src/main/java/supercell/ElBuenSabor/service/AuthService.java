package supercell.ElBuenSabor.service;


import supercell.ElBuenSabor.Models.Person;

public interface AuthService<T> {
    String logIn(String userName, String password);

    T register(T user);


}
