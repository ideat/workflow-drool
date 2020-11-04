package com.mindware.workflow.spring.rest.cashFlow;

import com.mindware.workflow.core.entity.PaymentPlan;
import com.mindware.workflow.core.entity.cashFlow.CashFlow;
import com.mindware.workflow.core.entity.cashFlow.FlowItem;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.cashFlow.RepositoryCashFlow;
import com.mindware.workflow.core.service.data.creditRequest.RepositoryCreditRequest;
import com.mindware.workflow.core.service.data.patrimonialStatement.RepositoryPatrimonialStatement;
import com.mindware.workflow.core.service.data.paymentPlan.RepositoryPaymentPlan;
import com.mindware.workflow.core.service.task.CreateCashFlow;
import com.mindware.workflow.core.usecase.UseCase;
import com.mindware.workflow.spring.config.ServiceUseCaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CashFlowController {
    private Integer numberRequest;
    private UUID id;

    @Autowired
    ServiceUseCaseFactory useCaseFactory;

    @Autowired
    RepositoryPaymentPlan repositoryPaymentPlan;

    @Autowired
    RepositoryPatrimonialStatement repositoryPatrimonialStatement;

    @Autowired
    RepositoryCreditRequest repositoryCreditRequest;

    @Autowired
    RepositoryCashFlow repositoryCashFlow;

    @PostMapping(value = "/v1/cashFlow/add", name = "Crea flujo de caja")
    ResponseEntity<CashFlow> create(@RequestBody CashFlow cashFlow, HttpServletRequest request){
        UseCase<CashFlow> useCase = useCaseFactory.create(CreateCashFlow.class.getSimpleName(),cashFlow);
        useCase.execute();
        if(useCase.getResult().isPresent()){
            return new ResponseEntity<>(useCase.getResult().get(),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(cashFlow,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/cashFlow/getById/{idcashflow}", name = "Obtiene Flujo caja por ID")
    ResponseEntity<Optional<CashFlow>> getById(@PathVariable("idCashFlow") UUID idcashflow){
        Optional<CashFlow> cashFlow = repositoryCashFlow.getById(idcashflow);

        return new ResponseEntity<>(cashFlow,HttpStatus.OK);

    }

    @GetMapping(value = "/v1/cashFlow/getByNumberRequest/{numberRequest}", name = "Obtiene Flujo caja por ID")
    ResponseEntity<Optional<CashFlow>> getByNumberRequest(@PathVariable("numberRequest") Integer number){
        Optional<CashFlow> cashFlow = repositoryCashFlow.getByNumberRequest(number);

        return new ResponseEntity<>(cashFlow,HttpStatus.OK);
    }
    

    @GetMapping(value = "/v1/cashFlow/generateCashFlow", name = "Crear flujo caja")
    ResponseEntity<List<FlowItem>> generateCashFlow(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("numberrequest")) numberRequest = Integer.parseInt(value);
            if(key.equals("id")) id = UUID.fromString(value);
        });

        List<PaymentPlan> paymentPlanList = repositoryPaymentPlan.getPaymentPlanByNumberRequest(numberRequest);
        List<PatrimonialStatement> patrimonialStatementList = repositoryPatrimonialStatement.getByIdCreditRequestApplicant(id);
        CreditRequest creditRequest = repositoryCreditRequest.getCreditRequestByNumberRequest(numberRequest).get();
        CreateCashFlow createCashFlow = new CreateCashFlow();
        List<FlowItem> cashflow = createCashFlow.createCashFlowMonth(paymentPlanList,patrimonialStatementList, creditRequest);

        return new ResponseEntity<>(cashflow, HttpStatus.OK);

    }



}
