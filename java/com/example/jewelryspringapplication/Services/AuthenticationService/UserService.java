package com.example.jewelryspringapplication.Services.AuthenticationService;

import com.example.jewelryspringapplication.Exceptions.*;
import com.example.jewelryspringapplication.Models.Jewelries.BaseEntity;
import com.example.jewelryspringapplication.Models.Users.User;
import com.example.jewelryspringapplication.Repositories.UserRepository;
import com.example.jewelryspringapplication.Services.JewelryServices.BaseJewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BaseJewelryService baseJewelryService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, BaseJewelryService baseJewelryService) {
        this.userRepository = userRepository;
        this.baseJewelryService = baseJewelryService;
        this.encoder = new BCryptPasswordEncoder();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByRegistrationCode(String registrationCode) { return userRepository.findByRegistrationCode(registrationCode); };

    public List<User> findSubscribedUsers() { return this.userRepository.findAllByIsSubscribedTrue(); }

    public Page<BaseEntity> getCartItems(Principal principal, int pageNumber) {
        List<BaseEntity> currentUserCartItems = user(principal).getCartItems();
        int pageSize = 4;
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, currentUserCartItems.size());
        List<BaseEntity> itemsForPage = currentUserCartItems.subList(startIndex, endIndex);
        return new PageImpl<>(itemsForPage, PageRequest.of(pageNumber - 1, pageSize), currentUserCartItems.size());
    }

    public void registerUser(String email,String password) {
        this.userRepository.save(new User(email, encoder.encode(password)));
    }

    public void changePassword(String newPassword,User user) {
        user.setPassword(encoder.encode(newPassword));
        this.userRepository.save(user);
    }

    public void addItemToWishlist(String discriminator, Long id,Principal principal) {
        BaseEntity entity = this.baseJewelryService.findByIdAndDiscriminator(id, discriminator);
        User currentUser = user(principal);
        currentUser.getWishlist().add(entity);
        this.userRepository.save(currentUser);
    }

    public void subscribeUser(User currentUser) {
        currentUser.setIsSubscribed(true);
        this.userRepository.save(currentUser);
    }
    public void checkForErrorsDuringSubscription(String email,User currentUser) {
        if (!currentUser.getEmail().equals(email)) { throw new WrongLinkedEmailException("The email linked to your account isn't the same as the one you typed !"); }
        this.subscribeUser(currentUser);
    }
    public void checkForExistingEmail(String email) {
         if (this.userRepository.findByEmail(email).isEmpty()) { throw new EmailNotExistingException("Such email doesn't exist"); }
    }

    public void addItemToCart(String discriminator, Long id,Principal principal) {
        BaseEntity entity = this.baseJewelryService.findByIdAndDiscriminator(id, discriminator);
        User currentUser = user(principal);
        currentUser.getCartItems().add(entity);
        this.userRepository.save(currentUser);
    }
    public void save(User user) { this.userRepository.save(user); }

    public void checkForErrors(String email, String password, String repeatPassword) {
        Optional<User> user = findUserByEmail(email);

        if (user.isPresent()) { throw new PresentEmailException("such email already exists");}

        if (!password.equals(repeatPassword)) { throw new PasswordsNotMatchingException("passwords do not match !"); }
    }

    public void checkForErrorsDuringUpdate(String oldPassword, String newPassword, User user) {
        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordChangingException("Passwords do not match");
        } else if (oldPassword.equals(newPassword)) {
            throw new SamePasswordsException("Old and new password are same.");
        }
    }

    private User user(Principal principal) {
        return this.findUserByEmail(principal.getName()).orElseThrow();
    }

}
