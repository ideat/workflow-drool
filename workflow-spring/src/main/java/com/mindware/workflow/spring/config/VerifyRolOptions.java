package com.mindware.workflow.spring.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.workflow.core.entity.rol.Option;
import com.mindware.workflow.core.entity.rol.Rol;
import com.mindware.workflow.core.service.data.rol.RepositoryRol;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//import org.apache.commons.io.FileUtils;

@Component
public class VerifyRolOptions {

    @Autowired
    RepositoryRol repository;

    @Autowired
    ResourceLoader resourceLoader;

//    public VerifyRolOptions() throws IOException {
//        updateOptionsRol();
//    }

    public void updateOptionsRol() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Option> optionList = new ArrayList<>();


//        File file =  new File(classLoader.getResource("/menu-json.json").getFile());;  //ResourceUtils.getFile("classpath:menu-json.json");
        Resource resource = resourceLoader.getResource("classpath:static/menu-json.json");
        File file = File.createTempFile("menu","json");
        InputStream inputStream = resource.getInputStream();
        FileUtils.copyInputStreamToFile(inputStream,file);

//        File file = resource.getFile();

        optionList = mapper.readValue(file,new TypeReference<List<Option>>() {});

        Rol rol = repository.getRolByName("ADMINISTRADOR").get();
        List<Option> optionsAdmin = mapper.readValue(rol.getOptions(),new TypeReference<List<Option>>() {});

        List<Option> result = new ArrayList<>();
        if(optionList.size()>=optionsAdmin.size()){
            result = optionsToAddAndRemove(optionList,optionsAdmin);
            addOption(result);
        }else{
            result = optionsToAddAndRemove(optionsAdmin,optionList);
            removeOption(result);
        }
    }

    private List<Option> optionsToAddAndRemove(List<Option> optionsA, List<Option> optionsB){
        List<Option> result = new ArrayList<>();
        for(Option o : optionsA){
            Option op = optionsB.stream()
                    .filter(b -> o.getName().equals(b.getName()))
                    .findAny()
                    .orElse(null);
            if(op==null){
                result.add(o);
            }

        }
        return result;
    }

    private void addOption(List<Option> options) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Rol> rolList = repository.getAll();
        for(Rol rol:rolList){
            List<Option> optionsRol = mapper.readValue(rol.getOptions(),new TypeReference<List<Option>>() {});
            optionsRol.addAll(options);
            String jsonOptions = mapper.writeValueAsString(optionsRol);
            rol.setOptions(jsonOptions);
            repository.update(rol);
        }
    }

    private void removeOption(List<Option> options) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Rol> rolList = repository.getAll();
        for(Rol rol:rolList){
            List<Option> optionsRol = mapper.readValue(rol.getOptions(),new TypeReference<List<Option>>() {});

            for(Option option:options){
                optionsRol.removeIf(o -> o.getName().equals(option.getName()));
            }
//            optionsRol.removeAll(options);
            String jsonOptions = mapper.writeValueAsString(optionsRol);
            rol.setOptions(jsonOptions);
            repository.update(rol);
        }
    }

}
