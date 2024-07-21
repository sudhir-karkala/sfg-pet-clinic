package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService,
                      VetService vetService,
                      PetTypeService petTypeService,
                      SpecialityService specialityService,
                      VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        PetType dogType = new PetType();
        dogType.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dogType);

        PetType catType = new PetType();
        catType.setName("Cat");
        PetType savedCatPetType = petTypeService.save(catType);

        System.out.println("Loaded pet types...");
        Owner owner1 = new Owner();
        owner1.setFirstName("Frank");
        owner1.setLastName("George");
        owner1.setAddress("123 Brickell");
        owner1.setCity("Miami");
        owner1.setTelephone("1234563456");

        Pet franksPet = new Pet();
        franksPet.setBirthDate(LocalDate.now());
        franksPet.setPetType(savedDogPetType);
        franksPet.setName("Just Dog");
        franksPet.setOwner(owner1);
        owner1.getPets().add(franksPet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Michael");
        owner2.setLastName("Robert");
        owner2.setAddress("123 Brickell");
        owner2.setCity("Miami");
        owner2.setTelephone("1234563456");

        Pet michaelPet = new Pet();
        michaelPet.setName("Just Cat");
        michaelPet.setPetType(savedCatPetType);
        michaelPet.setBirthDate(LocalDate.now());
        michaelPet.setOwner(owner2);
        owner2.getPets().add(michaelPet);

        ownerService.save(owner2);

        System.out.println("Loaded owners...");

        Visit catVisit = new Visit();
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Visiting michael's cat");
        catVisit.setPet(michaelPet);

        visitService.save(catVisit);

        System.out.println("Loaded visits...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Joseph");
        vet1.setLastName("Thomas");

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = specialityService.save(surgery);
        vet1.getSpecialities().add(savedSurgery);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Charles");
        vet2.setLastName("Mark");

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = specialityService.save(radiology);
        vet2.getSpecialities().add(savedRadiology);
        vetService.save(vet2);

        System.out.println("Loaded vets...");
    }
}
