package org.upc.fitwise.plan.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.upc.fitwise.plan.application.internal.outboundservices.acl.ExternalIamService;
import org.upc.fitwise.plan.domain.model.aggregates.PlanSubscription;
import org.upc.fitwise.plan.domain.model.commands.CancelPlanSubscriptionCommand;
import org.upc.fitwise.plan.domain.model.commands.CreatePlanSubscriptionCommand;
import org.upc.fitwise.plan.domain.model.commands.RenewPlanSubscriptionCommand;
import org.upc.fitwise.plan.domain.services.PlanSubscriptionCommandService;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.FitwisePlanRepository;
import org.upc.fitwise.plan.infrastructure.persistence.jpa.repositories.PlanSubscriptionRepository;

import java.util.Optional;

@Service
public class PlanSubscriptionCommandServiceImpl implements PlanSubscriptionCommandService {

    private final PlanSubscriptionRepository planSubscriptionRepository;
    private final FitwisePlanRepository fitwisePlanRepository;
    private final ExternalIamService externalIamService;

    public PlanSubscriptionCommandServiceImpl(PlanSubscriptionRepository planSubscriptionRepository, FitwisePlanRepository fitwisePlanRepository, ExternalIamService externalIamService) {
        this.planSubscriptionRepository= planSubscriptionRepository;
        this.fitwisePlanRepository= fitwisePlanRepository;
        this.externalIamService= externalIamService;
    }

    @Override
    public Long handle(CreatePlanSubscriptionCommand command) {
        // Verificar que el usuario existe
        if (!externalIamService.existsUserById(command.userId())) {
            throw new IllegalArgumentException("El usuario con ID " + command.userId() + " no existe.");
        }

        if(!fitwisePlanRepository.existsById(command.fitwisePlanId())){
            throw new IllegalArgumentException("El plan con ID " + command.fitwisePlanId() + " no existe.");
        }

        // Crear instancia de PlanSubscription usando createWithFitwisePlanId
        PlanSubscription planSubscription = PlanSubscription.createWithFitwisePlanId(
                command.userId(),
                command.fitwisePlanId(),
                command.subscriptionStartDate(),
                command.endDate(),
                command.notes(),
                fitwisePlanRepository
        );


        // Establecer notas, si están presentes
        if (command.notes() != null && !command.notes().isBlank()) {
            planSubscription.setNotes(command.notes());
        }

        // Guardar la instancia y devolver el ID generado
        return planSubscriptionRepository.save(planSubscription).getId();
    }

    @Override
    public Optional<PlanSubscription> handle(RenewPlanSubscriptionCommand command) {
        // Buscar la suscripción por ID
        PlanSubscription planSubscription = planSubscriptionRepository.findById(command.planSubscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("La suscripción con ID " + command.planSubscriptionId() + " no existe."));

        // Verificar si la nueva fecha de fin es válida
        if (!command.newEndDate().isAfter(planSubscription.getEndDate())) {
            throw new IllegalArgumentException("La nueva fecha de finalización debe ser posterior a la fecha actual de finalización.");
        }

        // Renovar la suscripción
        planSubscription.renew(command.newEndDate());

        // Guardar los cambios
        planSubscriptionRepository.save(planSubscription);

        // Devolver la suscripción actualizada
        return Optional.of(planSubscription);
    }

    @Override
    public void handle(CancelPlanSubscriptionCommand command) {
        // Buscar la suscripción por ID
        PlanSubscription planSubscription = planSubscriptionRepository.findById(command.planSubscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("La suscripción con ID " + command.planSubscriptionId() + " no existe."));

        // Cancelar la suscripción
        planSubscription.cancel();

        // Guardar los cambios
        planSubscriptionRepository.save(planSubscription);
    }
}
