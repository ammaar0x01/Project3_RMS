package com.restaurant.rms.controllers;

import com.restaurant.rms.models.*;
import com.restaurant.rms.models.DTO.CustomerGroupDTO;
import com.restaurant.rms.models.DTO.ReservationDTO;
import com.restaurant.rms.repository.CustomerGroupRepo;
import com.restaurant.rms.repository.ReservationRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private CustomerGroupRepo customerGroupRepo;
    // --------------------------------

    // READ //
    @GetMapping("")
    public String allReservations(Model model) {
        List<Reservation> reservations = reservationRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("reservations", reservations);
        return "reservation/reservations";
    }

    @GetMapping("/list")
    public String reservationsList(Model model) {
        List<Reservation> reservations = reservationRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("reservations", reservations);
        return "reservation/reservations-list";
    }
    // --------------------------------

    // ADD //
    @GetMapping("/add")
    public String addReservation(Model model) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setCustomerGroup(new CustomerGroupDTO());
        model.addAttribute("reservationDTO", reservationDTO);
        return "reservation/reservations-add";
    }
    @PostMapping("/add")
    public String createReservation(
            @Valid @ModelAttribute ReservationDTO rDTO,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("errors", "Validation errors occurred.");
            System.out.println(result.getAllErrors());
            return "reservation/reservations-add";
        }

        //Optional<CustomerGroup> optionalCustomerGroup = customerGroupRepo.findByPhoneNumber(     rDTO.getCustomerGroupId());

//        CustomerGroup customerGroup;
//        if (optionalCustomerGroup.isPresent()){
//            customerGroup = optionalCustomerGroup.get();
//
//        } else {
//            customerGroup = new CustomerGroup();
//
//        }

        //CustomerGroup customerGroup = customerGroupRepo.findById(rDTO.getCustomerGroupId()).orElse(null);

        CustomerGroupDTO customerGroupDTO = rDTO.getCustomerGroup();
        if (customerGroupDTO == null || customerGroupDTO.getFirstName() == null || customerGroupDTO.getPhoneNumber() == null) {
            model.addAttribute("errors", "Customer information missing");
            return "reservation/reservations-add";
        }

        CustomerGroup customerGroup = new CustomerGroup();
        customerGroup.setFirstName(customerGroupDTO.getFirstName());
        customerGroup.setLastName(customerGroupDTO.getLastName());
        customerGroup.setPhoneNumber(customerGroupDTO.getPhoneNumber());
        customerGroup.setNumberOfPeople(customerGroupDTO.getNumberOfPeople());

        customerGroupRepo.save(customerGroup);


        UUID rId = UUID.randomUUID();
        ReservationId reservationId = new ReservationId(rId, customerGroup.getCustomerGroupId());

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setCustomerGroup(customerGroup);

        reservation.setReservationDateTime(rDTO.getReservationDateTime());
        reservation.setReservationTimeMax(rDTO.getReservationTimeMax());
        reservation.setReservationTimeStart(rDTO.getReservationTimeStart());
        reservation.setReservationTimeEnd(rDTO.getReservationTimeEnd());
        reservation.setTableId(rDTO.getTableId());

        if (reservation.getId() == null||
        reservation.getId().getUuid() == null){
            model.addAttribute("errors", "Reservation id incomplete");
            return "reservation/reservations-add";
        }

        this.reservationRepo.save(reservation);
        System.out.println("\n***Reservation Added \n(" + reservation.getId() + ")");
        return "redirect:/reservations";
    }
    // --------------------------------

    // EDIT //
    @GetMapping("/edit")
    public String editReservation(
            Model model,
            @RequestParam UUID uuid,
            @RequestParam int customerGroupId
    ) {

        ReservationId id = new ReservationId(uuid, customerGroupId);
        // System.out.println("\n***Reservation Edit \n(" + uuid + customerGroupId + ")");
        try {
            Reservation reservation = reservationRepo.findById(id).orElseThrow();

            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setId(id);
            reservationDTO.setReservationDateTime(reservation.getReservationDateTime());
            reservationDTO.setReservationTimeMax(reservation.getReservationTimeMax());
            reservationDTO.setReservationTimeStart(reservation.getReservationTimeStart());
            reservationDTO.setReservationTimeEnd(reservation.getReservationTimeEnd());
            reservationDTO.setTableId(reservation.getTableId());

            CustomerGroup group = reservation.getCustomerGroup();
            if (group != null) {
                CustomerGroupDTO groupDTO = new CustomerGroupDTO();
                groupDTO.setCustomerGroupId(group.getCustomerGroupId());
                groupDTO.setFirstName(group.getFirstName());
                groupDTO.setLastName(group.getLastName());
                groupDTO.setPhoneNumber(group.getPhoneNumber());
                groupDTO.setNumberOfPeople(group.getNumberOfPeople());
                reservationDTO.setCustomerGroup(groupDTO);
            }

            model.addAttribute("reservationDTO", reservationDTO);
            //model.addAttribute("reservation", reservationDTO);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/reservations";

        }
        return "reservation/reservations-edit";
    }
    @PostMapping("/edit")
    public String updateReservation(
            Model model,
            @Valid @ModelAttribute ("reservationDTO")ReservationDTO resDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "reservation/reservations-edit";
        }
        try {
           ReservationId id = resDTO.getId();
           Reservation reservation = reservationRepo.findById(id).orElseThrow();
            //model.addAttribute("resDTO", reservation);


            reservation.setReservationDateTime(resDTO.getReservationDateTime());
            reservation.setReservationTimeMax(resDTO.getReservationTimeMax());
            reservation.setReservationTimeStart(resDTO.getReservationTimeStart());
            reservation.setReservationTimeEnd(resDTO.getReservationTimeEnd());
            reservation.setTableId(resDTO.getTableId());

            CustomerGroupDTO group = resDTO.getCustomerGroup();
            CustomerGroup updatedGroup = reservation.getCustomerGroup();

            updatedGroup.setFirstName(group.getFirstName());
            updatedGroup.setLastName(group.getLastName());
            updatedGroup.setPhoneNumber(group.getPhoneNumber());
            updatedGroup.setNumberOfPeople(group.getNumberOfPeople());

            customerGroupRepo.save(updatedGroup);
            reservationRepo.save(reservation);
            System.out.println("\n Updated Reservation \n(" + reservation.getId() + ")");

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "redirect:/reservations";
    }
    // --------------------------------

    // DELETE //
    @GetMapping("/delete")
    public String deleteReservation(
            @RequestParam ("uuid") UUID uuid,
            @RequestParam ("customerGroupId") int customerGroupId
    ){

        try {
           ReservationId id = new ReservationId(uuid, customerGroupId);
           Reservation reservation = reservationRepo.findById(id).orElse(null);

           if (reservation != null) {
               reservationRepo.delete(reservation);
               System.out.println("\n Deleted Reservation \n(" + reservation.getId() + ")");
           }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "redirect:/reservations";
    }
}
