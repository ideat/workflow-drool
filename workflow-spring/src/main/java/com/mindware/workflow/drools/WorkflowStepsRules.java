package com.mindware.workflow.drools;

import com.mindware.workflow.core.entity.config.RequestStage;
import com.mindware.workflow.core.entity.config.States;
import com.mindware.workflow.core.entity.creditRequest.CreditRequest;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.entity.stageHistory.StageHistory;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;

import java.util.Collection;
import java.util.List;

public class WorkflowStepsRules {
    public static Boolean verifyAllStageFinished(List<StageHistory> stageHistories, List<RequestStage> requestStageList, List<States> statesList){

        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("VerifyStage");

        stageHistories.forEach(kieSession::insert);
        requestStageList.forEach(kieSession::insert);
        statesList.forEach(kieSession::insert);

        kieSession.fireAllRules();
        kieSession.dispose();

        Collection<?> verify = kieSession.getObjects(o -> o.getClass()==Boolean.class);

        return !verify.isEmpty();

    }


    public static List<StageHistory> nextStageHistory(List<RequestStage> nextRequestStages, StageHistory stageHistory
            , CreditRequest creditRequest){

        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("NextStages");


        return null;

    }


    public static Boolean validStage(String stage, CreditRequest creditRequest, List<PatrimonialStatement> patrimonialStatementList){

        return true;
    }


}
