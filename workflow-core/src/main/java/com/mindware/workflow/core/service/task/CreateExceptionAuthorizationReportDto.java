package com.mindware.workflow.core.service.task;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.exceptions.StatusReview;
import com.mindware.workflow.core.service.data.exceptions.dto.AuthorizationExceptionReportDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateExceptionAuthorizationReportDto {


    public static List<AuthorizationExceptionReportDto> generate(List<AuthorizationExceptionReportDto> authorizationExceptionReportDtoList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<AuthorizationExceptionReportDto> authorizationExceptionReportDtos = new ArrayList<>();
        for(AuthorizationExceptionReportDto a : authorizationExceptionReportDtoList){
            List<StatusReview> statusReviews = objectMapper.readValue(a.getStatusReview(), new TypeReference<List<StatusReview>>(){});
            StatusReview statusReview = statusReviews.get(0);

            String justification = a.getJustification();
            String comment = statusReview.getJustificationApprovalLevel() != null?statusReview.getJustificationApprovalLevel():"Sin comentarios";
            justification = justification + "\n\n" + "Aprobado por: " + statusReview.getLoginUser() + "\n" + "Comentarios: \n" + comment;
            a.setJustification(justification);

            authorizationExceptionReportDtos.add(a);

        }

        return authorizationExceptionReportDtos;
    }
}
