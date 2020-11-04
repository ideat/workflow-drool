package com.mindware.workflow.core.service.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.Applicant;
import com.mindware.workflow.core.entity.patrimonialStatement.PatrimonialStatement;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.costProduct.CostProduct;
import com.mindware.workflow.core.service.data.patrimonialStatement.dto.costProduct.PatrimonialStatementCostProducts;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CreatePatrimonialStatementCostProduct {
    List<CostProduct> costProductList;

    public PatrimonialStatementCostProducts generateCostProduct(String product, Applicant applicant, PatrimonialStatement patrimonialStatement) throws IOException {
        PatrimonialStatementCostProducts pcp = new PatrimonialStatementCostProducts();
        pcp.setActivity(patrimonialStatement.getFieldText1());
        pcp.setFullName(applicant.getFullName());
        ObjectMapper mapper = new ObjectMapper();
        costProductList = mapper.readValue(patrimonialStatement.getFieldText6(),new TypeReference<List<CostProduct>>(){});
        List<CostProduct> costProductListFilter = costProductList.stream().filter(c -> c.getProduct().equals(product)).collect(Collectors.toList());
        pcp.setCostProductList(costProductListFilter);

        return pcp;
    }
}
