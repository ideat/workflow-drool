package com.mindware.workflow.core.service.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.config.RequestStage;
import com.mindware.workflow.core.entity.config.WorkflowProduct;
import com.mindware.workflow.core.entity.stageHistory.StageHistory;
import com.mindware.workflow.core.service.data.stageHistory.dto.StageHistoryCreditRequestDto;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CreateStageHistoryCreditRequestDto {

    public static List<StageHistoryCreditRequestDto> generateDetailStageHistoryCreditRequest(List<StageHistoryCreditRequestDto> list, List<WorkflowProduct> workflowProductList) throws IOException {
        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for(StageHistoryCreditRequestDto stageHCR:list){
            WorkflowProduct workflowProduct = workflowProductList.stream()
                    .filter(w ->w.getCodeObjectCredit().equals(stageHCR.getCodeObjectCredit())
                            && w.getCodeTypeCredit().equals(stageHCR.getCodeTypeCredit()))
                    .collect(Collectors.toList()).get(0);
            List<RequestStage> requestStageList = mapper.readValue(workflowProduct.getRequestStage(),new TypeReference<List<RequestStage>>(){});
            RequestStage requestStage = requestStageList.stream().filter(st -> st.getStage().equals(stageHCR.getStage()))
                    .collect(Collectors.toList()).get(0);

            if(stageHCR.getInitDateTime()==null){
                Long hours = ChronoUnit.HOURS.between(stageHCR.getStartDateTime(), Instant.now());
                stageHCR.setTimeToBeAssigned(hours.intValue());
                stageHCR.setTimeElapsed(0);
            }else{
                Long hours = ChronoUnit.HOURS.between(stageHCR.getStartDateTime(), stageHCR.getInitDateTime());
                stageHCR.setTimeToBeAssigned(hours.intValue());
                if(stageHCR.getFinishedDateTime()==null){
                    hours = ChronoUnit.HOURS.between(stageHCR.getInitDateTime(),Instant.now());
                    stageHCR.setTimeElapsed(hours.intValue());
                }else{
                    hours = ChronoUnit.HOURS.between(stageHCR.getInitDateTime(),stageHCR.getFinishedDateTime());
                    stageHCR.setTimeElapsed(hours.intValue());
                }
            }
            stageHCR.setTotalHoursStage(requestStage.getHours());

            stageHistoryCreditRequestDtoList.add(stageHCR);
        }


        return stageHistoryCreditRequestDtoList;
    }
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static List<StageHistoryCreditRequestDto> generateResumStageHistoryCreditRequestDto(List<StageHistoryCreditRequestDto> list, List<String> states){


        List<StageHistoryCreditRequestDto> stageHistoryCreditRequestDtoList = list;
        List<StageHistoryCreditRequestDto> returnList = new ArrayList<>();

        List<StageHistoryCreditRequestDto> aux=   new ArrayList<>();

        for(StageHistoryCreditRequestDto st:stageHistoryCreditRequestDtoList){
            for(String s:states){
                if(st.getState().equals(s)){
                    aux.add(st);
                }
            }
        }

        for(StageHistoryCreditRequestDto st: aux){
            List<StageHistoryCreditRequestDto> filtered = list.stream()
                    .filter(s -> s.getNumberRequest().equals(st.getNumberRequest()) && st.getStage().equals(s.getStage()))
                    .collect(Collectors.toList());

            Integer totalTimeElapsedStage = filtered.stream()
                    .filter(f->f.getNumberRequest().equals(st.getNumberRequest()) && st.getStage().equals(f.getStage()))
                    .mapToInt(StageHistoryCreditRequestDto::getTimeElapsed).sum();
            Integer timeToBeAssigned = filtered.stream()
                    .filter(f->f.getNumberRequest().equals(st.getNumberRequest()) && st.getStage().equals(f.getStage()))
                    .mapToInt(StageHistoryCreditRequestDto::getTimeToBeAssigned).sum();

            StageHistoryCreditRequestDto stageHistoryCreditRequestDto = st;
            stageHistoryCreditRequestDto.setTimeToBeAssigned(timeToBeAssigned);
            stageHistoryCreditRequestDto.setTimeElapsed(totalTimeElapsedStage);
            stageHistoryCreditRequestDto.setHoursLeft(stageHistoryCreditRequestDto.getTotalHoursStage()- (totalTimeElapsedStage+timeToBeAssigned));
            returnList.add(stageHistoryCreditRequestDto);

        }

        return returnList;

    }

    public static List<StageHistoryCreditRequestDto> generateGlobalResumeStageHistoryCreditRequestDto(List<StageHistoryCreditRequestDto> list){

        List<StageHistoryCreditRequestDto> uniqueStageHistoryCreditRequestDtoList = list.stream()
                .filter(distinctByKey(StageHistoryCreditRequestDto::getNumberRequest))
                .collect(Collectors.toList());

        List<StageHistoryCreditRequestDto> resultList = new ArrayList<>();
        for(StageHistoryCreditRequestDto st:uniqueStageHistoryCreditRequestDtoList){
            List<StageHistoryCreditRequestDto> filtered = list.stream().filter(f ->
                    f.getNumberRequest().equals(st.getNumberRequest())).collect(Collectors.toList());
            Integer totalTimeElapsed =  filtered.stream().filter(f->f.getNumberRequest().equals(st.getNumberRequest())).mapToInt(StageHistoryCreditRequestDto::getTimeElapsed).sum();
            Integer timeToBeAssigned = filtered.stream().filter(f->f.getNumberRequest().equals(st.getNumberRequest())).mapToInt(StageHistoryCreditRequestDto::getTimeToBeAssigned).sum();

            StageHistoryCreditRequestDto stageHistoryCreditRequestDto = st;
            stageHistoryCreditRequestDto.setTimeToBeAssigned(timeToBeAssigned);
            stageHistoryCreditRequestDto.setTimeElapsed(totalTimeElapsed);
            stageHistoryCreditRequestDto.setHoursLeft(stageHistoryCreditRequestDto.getTotalHours()- (totalTimeElapsed+timeToBeAssigned));

            resultList.add(stageHistoryCreditRequestDto);
        }

        return resultList;
    }

    
}
