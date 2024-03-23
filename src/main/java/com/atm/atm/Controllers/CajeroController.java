package com.atm.atm.Controllers;

import com.atm.atm.Entities.Cajero;
import com.atm.atm.repos.CajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cajero")
public class CajeroController {
    @Autowired
    private CajeroRepository cajeroRepository;

    @GetMapping()
    public Iterable<Cajero> getAllCajeros(){
        return cajeroRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cajero getCajeroById(@PathVariable Long id){
        return cajeroRepository.findById(id).orElseThrow(()-> new RuntimeException("Cajero no encontrado"));
    }

    @PutMapping("/{id}/retiro")
    public Object[] cashWithdrawal(@RequestParam double amount, @PathVariable Long id){
        try {
            System.out.println("ID: " + id);

            System.out.println("Amount received: " + amount);
            if(amount <= 0){
                throw new RuntimeException("El monto debe ser mayor a 0");
            }
            Cajero cajero = cajeroRepository.findById(id).orElseThrow(() -> new RuntimeException("Cajero no encontrado"));
            System.out.println(cajero.getAmount());
            if(cajero.getAmount() < amount){
                throw new RuntimeException("No hay suficiente dinero en el cajero");
            }
            cajero.setAmount(cajero.getAmount() - amount);
            Object [] res;
            res = new Object[]{cajeroRepository.save(cajero), "El saldo actualizado es: " + cajero.getAmount()};
            return res;
        } catch (Exception e) {
            throw new RuntimeException("El monto es mayor al que se tiene en la cuenta");
        }

    }

    @PutMapping("/{id}/deposito")
    public Object[] deposit(@RequestParam double amount, @PathVariable Long id){
        try {
            if(amount <= 0){
                throw new RuntimeException("El monto debe ser mayor a 0");
            }
            Cajero cajero = cajeroRepository.findById(id).orElseThrow(() -> new RuntimeException("Cajero no encontrado"));
            cajero.setAmount(cajero.getAmount() + amount);
            Object [] res;
            res = new Object[]{cajeroRepository.save(cajero), "El saldo actualizado es: " + cajero.getAmount()};
            return res;
        } catch (Exception e) {
            throw new RuntimeException("El monto no es válido");
        }
    }

    @PutMapping("/deposito")
    public Object[] depositBody(@RequestBody Cajero cajero){
        try {
            if(cajero.getAmount() <= 0){
                throw new RuntimeException("El monto debe ser mayor a 0");
            }
            Cajero cajero1 = cajeroRepository.findById(cajero.getId()).orElseThrow(() -> new RuntimeException("Cajero no encontrado"));
            System.out.println(cajero1.getAmount());
            cajero1.setAmount(cajero1.getAmount() + cajero.getAmount());
            Object [] res;
            res = new Object[]{cajeroRepository.save(cajero1), "El saldo actualizado es: " + cajero1.getAmount()};
            return res;
        } catch (Exception e) {
            throw new RuntimeException("El monto no es válido");
        }
    }

    @PostMapping()
    public Cajero createCajero(@RequestBody Cajero cajero){
        return cajeroRepository.save(cajero);
    }

}
