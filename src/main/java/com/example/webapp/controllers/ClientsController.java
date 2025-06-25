package com.example.webapp.controllers;

import com.example.webapp.models.ClientDto;
import com.example.webapp.models.Clients;
import com.example.webapp.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/clients")

public class ClientsController {
    @Autowired
    private ClientRepository clientRepo;

    @GetMapping({"","/"})
    public String getClients(Model model){
        var clients = clientRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("clients", clients);
        return "clients/index";
    }

    @GetMapping("/create")
    public String createClient(Model model){
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientDto", clientDto);

        return "clients/create";
    }

    @PostMapping("/create")
    public String createClient(
            @Valid @ModelAttribute ClientDto clientDto,
            BindingResult result
    )  {
        if (clientRepo.findByEmail(clientDto.getEmail()) != null){
            result.addError(
                    new FieldError("clientDto", "email", clientDto.getEmail()
                    , false, null, null,"Email address already used")
            );
        }
        if (result.hasErrors()) {
            return "clients/create";
        }

        Clients clients = new Clients();
        clients.setFirstName(clientDto.getFirstName());
        clients.setLastName(clientDto.getLastName());
        clients.setEmail(clientDto.getEmail());
        clients.setPhone(clientDto.getPhone());
        clients.setAddress(clientDto.getAddress());
        clients.setStatus(clientDto.getStatus());
        clients.setCreatedAt(new Date());


        clientRepo.save(clients);
        return "redirect:/clients";
    }


    @GetMapping("/edit")
    public String editClient(Model model, @RequestParam int id){
            Clients clients = clientRepo.findById(id).orElse(null);
            if(clients == null){
                return "redirect:/clients";
            }
            ClientDto clientDto = new ClientDto();
            clientDto.setFirstName(clients.getFirstName());
            clientDto.setLastName(clients.getLastName());
            clientDto.setEmail(clients.getEmail());
            clientDto.setPhone(clients.getPhone());
            clientDto.setAddress(clients.getAddress());
            clientDto.setStatus(clients.getStatus());

            model.addAttribute("clients", clients);
            model.addAttribute("clientDto", clientDto);

            return "clients/edit";
    }

    @PostMapping("/edit")
    public String editClient(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ClientDto clientDto,
            BindingResult result
    ){
        Clients clients = clientRepo.findById(id).orElse(null);
        if (clients == null){
            return "redirect:/clients";
        }

        model.addAttribute("clients", clients);

        if (result.hasErrors()){
            return  "clients/edit";
        }

        clients.setFirstName(clientDto.getFirstName());
        clients.setLastName(clientDto.getLastName());
        clients.setEmail(clientDto.getEmail());
        clients.setPhone(clientDto.getPhone());
        clients.setAddress(clientDto.getAddress());
        clients.setStatus(clientDto.getStatus());


        try {
            clientRepo.save(clients);
        }
        catch (Exception ex){
            result.addError(
                    new FieldError("clientDto", "email", clientDto.getEmail()
                    ,false,null,null, "Email Address is already  used")
            );
            return "clients/edit";
        }

        return "redirect:/clients";
    }

    @GetMapping("/delete")
    public String deleteClient(@RequestParam int id){
        Clients clients = clientRepo.findById(id).orElse(null);

        if(clients != null){
            clientRepo.delete(clients);
        }

        return  "redirect:/clients";

    }

}
