package org.upc.fitwise.plan.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upc.fitwise.plan.domain.model.aggregates.PlanSubscription;
import org.upc.fitwise.plan.domain.model.queries.GetPlanSubscriptionByIdQuery;
import org.upc.fitwise.plan.domain.model.queries.GetPlanSubscriptionsByUserIdQuery;
import org.upc.fitwise.plan.domain.services.PlanSubscriptionCommandService;
import org.upc.fitwise.plan.domain.services.PlanSubscriptionQueryService;
import org.upc.fitwise.plan.interfaces.rest.resources.CancelPlanSubscriptionResource;
import org.upc.fitwise.plan.interfaces.rest.resources.CreatePlanSubscriptionResource;
import org.upc.fitwise.plan.interfaces.rest.resources.PlanSubscriptionResource;
import org.upc.fitwise.plan.interfaces.rest.resources.RenewPlanSubscriptionResource;
import org.upc.fitwise.plan.interfaces.rest.transform.CancelPlanSubscriptionCommandFromResourceAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.CreatePlanSubscriptionCommandFromResourceAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.PlanSubscriptionResourceFromEntityAssembler;
import org.upc.fitwise.plan.interfaces.rest.transform.RenewPlanSubscriptionCommandFromResourceAssembler;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/plan-subscriptions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Plan subscriptions ", description = "Plan subscriptions Management Endpoints")
public class PlanSubscriptionsController {

    private final PlanSubscriptionQueryService planSubscriptionQueryService;
    private final PlanSubscriptionCommandService planSubscriptionCommandService;

    public PlanSubscriptionsController(PlanSubscriptionQueryService planSubscriptionQueryService, PlanSubscriptionCommandService planSubscriptionCommandService) {
        this.planSubscriptionQueryService = planSubscriptionQueryService;
        this.planSubscriptionCommandService = planSubscriptionCommandService;
    }

    @GetMapping("/{planSubscriptionId}")
    public ResponseEntity<PlanSubscriptionResource> getPlanSubscriptionsById(@PathVariable Long planSubscriptionId) {
        var getPlanSubscriptionByIdQuery = new GetPlanSubscriptionByIdQuery(planSubscriptionId);
        var planSubscription = planSubscriptionQueryService.handle(getPlanSubscriptionByIdQuery);
        if (planSubscription.isEmpty()) return ResponseEntity.notFound().build();
        var planSubscriptionResource = PlanSubscriptionResourceFromEntityAssembler.toResourceFromEntity(planSubscription.get());
        return ResponseEntity.ok(planSubscriptionResource);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PlanSubscriptionResource>> getPlanSubscriptionsByUserId(@PathVariable Long userId) {
        var getPlanSubscriptionsByUserIdQuery = new GetPlanSubscriptionsByUserIdQuery(userId);
        var planSubscriptions = planSubscriptionQueryService.handle(getPlanSubscriptionsByUserIdQuery);
        if (planSubscriptions.isEmpty()) return ResponseEntity.notFound().build();
        var planSubscriptionResource = planSubscriptions.stream().map(PlanSubscriptionResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(planSubscriptionResource);
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<PlanSubscriptionResource>> getActivePlanSubscriptionsByUserId(@PathVariable Long userId) {
        var getPlanSubscriptionsByUserIdQuery = new GetPlanSubscriptionsByUserIdQuery(userId);
        var planSubscriptions = planSubscriptionQueryService.handle(getPlanSubscriptionsByUserIdQuery);
        if (planSubscriptions.isEmpty()) return ResponseEntity.notFound().build();
        var activePlanSubscriptions = planSubscriptions.stream().filter(PlanSubscription::isActive).toList();
        var planSubscriptionResource = activePlanSubscriptions.stream().map(PlanSubscriptionResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(planSubscriptionResource);
    }

    @PostMapping
    public ResponseEntity<PlanSubscriptionResource> createPlanSubscription(@RequestBody CreatePlanSubscriptionResource createPlanSubscriptionResource) {
        var createPlanSubscriptionCommand = CreatePlanSubscriptionCommandFromResourceAssembler.toCommandFromResource(createPlanSubscriptionResource);
        var planSubscriptionId = planSubscriptionCommandService.handle(createPlanSubscriptionCommand);
        if (planSubscriptionId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getPlanSubscriptionByIdQuery = new GetPlanSubscriptionByIdQuery(planSubscriptionId);
        var planSubscription = planSubscriptionQueryService.handle(getPlanSubscriptionByIdQuery);
        if (planSubscription.isEmpty()) return ResponseEntity.notFound().build();
        var planSubscriptionResource = PlanSubscriptionResourceFromEntityAssembler.toResourceFromEntity(planSubscription.get());
        return new ResponseEntity<>(planSubscriptionResource, HttpStatus.CREATED);
    }

    @PutMapping("/{planSubscriptionId}/renew")
    public ResponseEntity<PlanSubscriptionResource> renewPlanSubscription(@PathVariable Long planSubscriptionId, @RequestBody RenewPlanSubscriptionResource renewPlanSubscriptionResource) {
        var renewPlanSubscriptionCommand = RenewPlanSubscriptionCommandFromResourceAssembler.toCommandFromResource(planSubscriptionId, renewPlanSubscriptionResource);
        var renewedPlanSubscription = planSubscriptionCommandService.handle(renewPlanSubscriptionCommand);
        if(renewedPlanSubscription.isEmpty()) return ResponseEntity.badRequest().build();
        var planSubscriptionResource = PlanSubscriptionResourceFromEntityAssembler.toResourceFromEntity(renewedPlanSubscription.get());
        return ResponseEntity.ok(planSubscriptionResource);
    }

}
