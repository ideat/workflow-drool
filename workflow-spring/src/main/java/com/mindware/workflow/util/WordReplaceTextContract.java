package com.mindware.workflow.util;

import com.mindware.workflow.core.entity.contract.Contract;
import com.mindware.workflow.core.entity.contract.ContractVariable;
import com.mindware.workflow.core.service.data.contract.dto.ContractData;
import com.mindware.workflow.core.service.data.legal.RepositoryContractVariable;
import com.xandryex.WordReplacer;
import com.xandryex.utils.TextReplacer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordReplaceTextContract {
    private TextReplacer replacer;
    private WordReplacer  wordReplacer;
    private static File templateFile;
    private static File replacedFiles;

    private String compoundVariable;
    private   String variable;

    private Map<String,String> mapContractData;
    private Map<String,String> mapWithDataContractVariable;

    @Autowired
    private RepositoryContractVariable repositoryContractVariable;

    @Autowired
    private GenerateContractData generateContractData;

    @SneakyThrows
    public void generateContract(Contract contract) throws IOException {

//        generateContractData = new GenerateContractData();


        ContractData contractData = generateContractData.getContractData(contract.getNumberRequest());
        contractData.setDenominationDebtor(contract.getDenominationDebtor());
        contractData.setDenominationGuarantor(contract.getDenominationGuarantor());
        contractData.setDenominationCreditor(contract.getDenominationCreditor());

        replacer = new TextReplacer();
        templateFile = new File(contract.getPathTemplate());
        wordReplacer = new WordReplacer(templateFile);

        mapContractData = getDataForMapContractData(contractData);
        Map<String,String> mapContractVariableSimple = getDataForMapContractVariable("SIMPLE");
        mapWithDataContractVariable = replaceDataForMapContractVariable(mapContractVariableSimple);
        Map<String,String> mapContractVariableCompound = getDataForMapContractVariable("COMPUESTA");
        mapWithDataContractVariable.putAll(replaceDataForMapContractVariableCompound(mapContractVariableCompound));

        mapWithDataContractVariable.forEach((k,v)->{
            if(v.equals("$us.")) v="USD";
//            if(v.contains("$")) v.replaceAll("$","S");
            wordReplacer.replaceWordsInText(k,v);
            wordReplacer.replaceWordsInTables(k,v);
        });
        wordReplacer.saveAndGetModdedFile(contract.getPathContract());

    }

    private Map<String,String> getDataForMapContractData(ContractData contractData) throws NoSuchFieldException, IllegalAccessException {
        Class aClass = ContractData.class;
        Field[] fields = aClass.getDeclaredFields();
        Map<String,String> map = new HashMap<>();
        for(Field f: fields ){
            Field field = contractData.getClass().getDeclaredField(f.getName());
            map.put(f.getName(),field.get(contractData)!=null?field.get(contractData).toString():"");
        }
        return map;
    }

    private Map<String,String> getDataForMapContractVariable(String typeVariable){
        Map<String,String> mapContractVariable= new HashMap<>();
        List<ContractVariable> contractVariableList = repositoryContractVariable.getByTypeVariable(typeVariable);

        for(ContractVariable cv:contractVariableList){
            mapContractVariable.put(cv.getName(),cv.getVariable());
        }
        return mapContractVariable;

    }

    private Map<String,String> replaceDataForMapContractVariable(Map<String,String> mapContractVariable){
        Map<String,String> resultMap = mapContractVariable;
        mapContractVariable.forEach((k,v)->{ //(k,v) -> @nombreCompleto,fullName
            String value= mapContractData.get(v);// get value from mapContractData (Jose Luis)
            if(value!=null && !value.equals(""))
                resultMap.replace(k,v,value); // @nomberCompleto,fullName,Jose Luis

        });
        return resultMap;
    }

    private Map<String,String> replaceDataForMapContractVariableCompound(Map<String,String> mapContractVariableCompound){
        Map<String,String> resultMap = mapContractVariableCompound;
        mapContractVariableCompound.forEach((k,v)->{ //(@Garantes,@nombreCompleto @carnet
            variable = v;
            compoundVariable="";
            mapWithDataContractVariable.forEach((key,value)->{ //@nombreCompleto, Jose Luis
                if(variable.contains(key)){
                    compoundVariable = variable.replace(key,value);
                    variable = compoundVariable;
                }

            });
            resultMap.replace(k,v,compoundVariable);
        });
        return resultMap;
    }

}
